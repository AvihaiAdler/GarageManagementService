package garage.vehicles;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import garage.dal.VehiclesDao;
import garage.exceptions.BadRequestException;
import garage.exceptions.ConflictException;
import garage.exceptions.NotFoundException;
import garage.logic.EnergySourceService;
import garage.logic.VehicleService;
import garage.logic.VehicleTypeService;
import garage.util.Util;
import garage.util.VehicleBoundaryEntityConverter;
import garage.vehicles.boundaries.DetailedVehicleBoundary;
import garage.vehicles.boundaries.FuelBoundary;
import garage.vehicles.boundaries.PressureBoundary;
import garage.vehicles.boundaries.VehicleBoundary;
import garage.vehicles.misc.VehicleTypes;
import garage.vehicles.misc.Wheel;

@Service
public class VehicleServiceImpl implements VehicleService {
  private VehicleBoundaryEntityConverter boundaryEntityConverter;
  private VehicleTypeService vehicleTypeService;
  private EnergySourceService energySourceService;
  private VehiclesDao vehiclesDao;
  private Util util;
  private int maxPercentage;
  private int minPercentage;
  private int maxPressure;
  private int minPressure;
  
  public VehicleServiceImpl(VehicleBoundaryEntityConverter boundaryEntityConverter, 
          VehicleTypeService vehicleTypeService,
          EnergySourceService energySourceService,
          VehiclesDao vehiclesDao,
          Util util) {
    this.boundaryEntityConverter = boundaryEntityConverter;
    this.vehicleTypeService = vehicleTypeService;
    this.energySourceService = energySourceService;
    this.vehiclesDao = vehiclesDao;   
    this.util = util;
  }
  
  @Value("${max.percent:100}")
  public void setMaxPercentage(int maxPercentage) {
    this.maxPercentage = maxPercentage;
  }
  
  @Value("${min.percent:0}")
  public void setMinPercentage(int minPercentage) {
    this.minPercentage = minPercentage;
  }
  
  @Value("${max.pressure:100}")
  public void setMaxPressure(int maxPressure) {
    this.maxPressure = maxPressure;
  }
  
  @Value("${min.pressure:0}")
  public void setMinPressure(int minPressure) {
    this.minPressure = minPressure;
  }
  
  @Override
  @Transactional
  public DetailedVehicleBoundary addVehicle(VehicleBoundary vehicleBoundary) {
    try {
      // validity checks:
      // VehicleType check
      var vehicleType = util.getTypeAsEnum(vehicleBoundary.vehicleType());
      
      // Model check
      if(vehicleBoundary.modelName() == null) {
        throw new BadRequestException("vehicle model name must not be null");
      }
      
      // Energy percentage check
      if(vehicleBoundary.energyPercentage() != null && vehicleBoundary.energyPercentage() < minPercentage) {
        throw new BadRequestException("invalid energy % " + vehicleBoundary.energyPercentage());
      }
      
      // Max tire pressure check
      if(vehicleBoundary.maxTirePressure() == null) {
        throw new BadRequestException("maximum tire pressure must not be null"); 
      }
      
      // Max tire pressure check
      if(vehicleBoundary.maxTirePressure() < minPressure || vehicleBoundary.maxTirePressure() > maxPressure) {
        throw new BadRequestException("invalid maximum tire pressure " + vehicleBoundary.maxTirePressure());
      }
         
      // License number check
      if(util.checkValidLicenseNumber(vehicleBoundary.licenseNumber()) == false) {
        throw new BadRequestException("invalid license number " + vehicleBoundary.licenseNumber());
      }
      
      // Vehicle already exists
      vehiclesDao.findByLicenseNumber(vehicleBoundary.licenseNumber()).ifPresent(value -> {
        throw new ConflictException("vehicle number " + vehicleBoundary.licenseNumber() + " already exists");
      });
      
      // create a vehicle entity
      var vehicleEntity = boundaryEntityConverter.toEntity(vehicleBoundary);
      
      // link the vehicle against its type
      vehicleTypeService.linkVehicleToService(vehicleEntity, vehicleType);
           
      // link the vehicle against its energy source
      if(vehicleType != VehicleTypes.Truck) {
        energySourceService.linkVehicleToSource(vehicleEntity, util.getEnergySourceAsEnum(vehicleBoundary.vehicleType()));
      }
      
      // save the vehicle
      return boundaryEntityConverter.toBoundary(vehiclesDao.save(vehicleEntity));
    } catch (NullPointerException | IllegalArgumentException np) {
      throw new BadRequestException(np);
    }
  }

  @Override
  @Transactional(readOnly = true)
  public DetailedVehicleBoundary getVehicle(String licenseNumber) {
    // validity check
    if(util.checkValidLicenseNumber(licenseNumber) == false) {
      throw new BadRequestException("invalid license number " + licenseNumber);
    }
    
    var entity = vehiclesDao
            .findByLicenseNumber(licenseNumber)
            .orElseThrow(() -> new NotFoundException("Vehicle with " + licenseNumber + " doesn't exists"));
    
    return boundaryEntityConverter.toBoundary(entity);
  }

  @Override
  @Transactional(readOnly = true)
  public List<DetailedVehicleBoundary> getAllVehicles(String filterType, String filterValue, int size, int page, String sortBy, String order) {   
    // extract the actual sortParam
    var sortParam = getSortParam(sortBy);
    
    // get direction Direction.ASC/DESC
    var direction = getDirection(order);
    
    // return the result/s based on the params
    var results = switch (filterType) {
      case "" -> vehiclesDao.findAll(PageRequest.of(page, size, direction, sortParam));             
      case "byEnergyType" -> vehiclesDao.findAllByEnergySource_energyType(filterValue.toLowerCase(), PageRequest.of(page, size, direction, sortParam));
      case "byModelName" -> vehiclesDao.findAllByModelName(filterValue.toLowerCase(), PageRequest.of(page, size, direction, sortParam));
      case "byVehicleType" -> vehiclesDao.findAllByVehicleType_vehicleType(filterValue.toLowerCase(), PageRequest.of(page, size, direction, sortParam));
      default -> throw new BadRequestException("inavlid filter type " + filterType);
    };
    return results.stream()
            .map(boundaryEntityConverter::toBoundary)
            .collect(Collectors.toList());
  }

  @Override
  @Transactional
  public void inflateTires(String licenseNumber, PressureBoundary pressure) {
    // validity checks
    // license number check
    if(util.checkValidLicenseNumber(licenseNumber) == false) {
      throw new BadRequestException("invalid license number " + licenseNumber);
    }
    
    // find the vehicle
    var vehicleEntity = vehiclesDao
            .findByLicenseNumber(licenseNumber)
            .orElseThrow(() -> new NotFoundException("vehicle number " + licenseNumber + " doesn't exists"));
    
    // pressure checks
    if(pressure == null || pressure.pressure() == null) {
      throw new BadRequestException("pressure must not be null");
    }
    
    if(pressure.pressure() < minPressure || pressure.pressure() > vehicleEntity.getMaxTirePressure()) {
      throw new BadRequestException("invalid pressure value " + pressure.pressure());
    }
    
    // inflate the tires (to the maxTirePressure of said vehicle)
    var wheels = boundaryEntityConverter.jsonToMap(vehicleEntity.getWheels())
            .entrySet()
            .stream()
            .collect(Collectors.toMap(Map.Entry::getKey, v -> new Wheel(pressure.pressure())));  
    vehicleEntity.setWheels(boundaryEntityConverter.mapToJson(wheels));
    
    vehiclesDao.save(vehicleEntity);
  }

  @Override
  @Transactional
  public void refuel(String licenseNumber, FuelBoundary fuel) {
    // validity checks
    // license number check
    if(util.checkValidLicenseNumber(licenseNumber) == false) {
      throw new BadRequestException("invalid license number " + licenseNumber);
    }
    
    // fuel check
    if(fuel == null || fuel.fuelPercentage() == null) {
      throw new BadRequestException("fuel must not be null");
    } 
    
    if(fuel.fuelPercentage() < minPercentage || fuel.fuelPercentage() > maxPercentage) {
      throw new BadRequestException("invalid fuel percentage value " + fuel.fuelPercentage());
    }
    
    var vehicleEntity = vehiclesDao
            .findByLicenseNumber(licenseNumber)
            .orElseThrow(() -> new NotFoundException("vehicle number " + licenseNumber + " doesn't exists"));
    
    // refuel/recharge the vehicle to max
    vehicleEntity.setEnergyPercentage(fuel.fuelPercentage());
    
    vehiclesDao.save(vehicleEntity);
  }

  @Override
  @Transactional
  public void delete(String licenseNumber) {
    // validity check
    if(util.checkValidLicenseNumber(licenseNumber) == false) {
      throw new BadRequestException("invalid license number " + licenseNumber);
    }
    
    var vehicleEntity = vehiclesDao
            .findByLicenseNumber(licenseNumber)
            .orElseThrow(() -> new NotFoundException("vehicle number " + licenseNumber + " doesn't exists"));
    
    vehiclesDao.delete(vehicleEntity);
  }

  @Override
  @Transactional
  public void deleteAllVehicles() {
    vehiclesDao.deleteAll();
    vehicleTypeService.deleteAll();
    energySourceService.deleteAll();
  }
  
  /*
   * Converts API sort params to their corresponding VehicleEntity data members  
   */
  private String getSortParam(String sortBy) {
    return switch(sortBy) {
      case "id", "licenseNumber" -> sortBy;
      case "model" -> "modelName";
      case "energy" -> "energyPercentage";
      case "maxPressure" -> "maxTirePressure";
      default -> throw new BadRequestException("invalid sort param " + sortBy);
    };
  }
  
  /*
   * Converts API order params to their corresponding Direction
   */
  private Direction getDirection(String order) {
    return switch(order) {
      case "ASC" -> Direction.ASC;
      case "DESC" -> Direction.DESC;
      default -> throw new BadRequestException("invalid sort order " + order);
    };
  }

}
