package garage.logic;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import garage.dal.VehiclesDao;
import garage.exceptions.BadRequestException;
import garage.exceptions.ConflictException;
import garage.exceptions.NotFoundException;
import garage.util.Helper;
import garage.util.VehicleBoundaryEntityConverter;
import garage.vehicles.DetailedVehicleBoundary;
import garage.vehicles.VehicleBoundary;
import garage.vehicles.misc.VehicleTypes;
import garage.vehicles.misc.Wheel;

@Service
public class VehicleServiceImpl implements VehicleService {
  private VehicleBoundaryEntityConverter boundaryEntityConverter;
  private VehiclesDao vehiclesDao;
  private int maxPercentage;
  private int minPercentage;
  private int maxPressure;
  private int minPressure;
  
  public VehicleServiceImpl(VehicleBoundaryEntityConverter boundaryEntityConverter, VehiclesDao vehiclesDao) {
    this.boundaryEntityConverter = boundaryEntityConverter;
    this.vehiclesDao = vehiclesDao;    
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
  public DetailedVehicleBoundary addVehicle(VehicleBoundary vehicleBoundary) {
    // validity checks:
    // VehicleType
    if(Helper.checkValidVehicleType(vehicleBoundary.vehicleType()) == false) {
      throw new BadRequestException("invalid vehicle type " + vehicleBoundary.vehicleType());
    }
    
    // if truck - override it's energySource to regular
    if(vehicleBoundary.vehicleType().getType().equalsIgnoreCase(VehicleTypes.Truck.toString())) {
      vehicleBoundary.vehicleType().setEnergySource(null);
    }
    
    // Model
    if(vehicleBoundary.modelName() == null) {
      throw new BadRequestException("vehicle model name must not be null");
    }
    
    // License number
    if(Helper.checkValidLicenseNumber(vehicleBoundary.licenseNumber()) == false) {
      throw new BadRequestException("invalid license number " + vehicleBoundary.licenseNumber());
    }
    
    // Energy percentage
    if(vehicleBoundary.energyPercentage() != null && vehicleBoundary.energyPercentage() < minPercentage) {
      throw new BadRequestException("invalid energy % " + vehicleBoundary.energyPercentage());
    }
    
    // Max tire pressure
    if(vehicleBoundary.maxTirePressure() == null) {
      throw new BadRequestException("maximum tire pressure must not be null"); 
    }
    
    // Max tire pressure
    if(vehicleBoundary.maxTirePressure() < minPressure || vehicleBoundary.maxTirePressure() > maxPressure) {
      throw new BadRequestException("invalid maximum tire pressure " + vehicleBoundary.maxTirePressure());
    }
    
    // Vehicle already exists
    vehiclesDao.findByLicenseNumber(vehicleBoundary.licenseNumber()).ifPresent(value -> {
      throw new ConflictException("vehicle number " + vehicleBoundary.licenseNumber() + " already exists");
    });
    
    var entity =  vehiclesDao.save(boundaryEntityConverter.toEntity(vehicleBoundary));
    return boundaryEntityConverter.toBoundary(entity);
  }

  @Override
  public DetailedVehicleBoundary getVehicle(String licenseNumber) {
    // validity check
    if(Helper.checkValidLicenseNumber(licenseNumber) == false) {
      throw new BadRequestException("invalid license number " + licenseNumber);
    }
    
    var entity = vehiclesDao.findByLicenseNumber(licenseNumber)
            .orElseThrow(() -> new NotFoundException("Vehicle with " + licenseNumber + " doesn't exists"));
    
    return boundaryEntityConverter.toBoundary(entity);
  }

  @Override
  public List<DetailedVehicleBoundary> getAllVehicles(String filterType, String filterValue, int size, int page, String sortBy, String order) {   
    // extract the actual sortParam
    var sortParam = getSortParam(sortBy);
    var direction = getDirection(order);
    
    return switch (filterType) {
      case "" -> vehiclesDao
              .findAll(PageRequest.of(page, size, direction, sortParam))
              .stream()
              .map(boundaryEntityConverter::toBoundary)
              .collect(Collectors.toList());
      case "byEnergyType" -> vehiclesDao
              .findAllByEnergySource(filterValue.toLowerCase(), PageRequest.of(page, size, direction, sortParam))
              .stream()
              .map(boundaryEntityConverter::toBoundary)
              .collect(Collectors.toList());
      case "byModelName" -> vehiclesDao
              .findAllByModelName(filterValue.toLowerCase(), PageRequest.of(page, size, direction, sortParam))
              .stream()
              .map(boundaryEntityConverter::toBoundary)
              .collect(Collectors.toList());
      case "byVehicleType" -> vehiclesDao
              .findAllByVehicleType(filterValue.toLowerCase(), PageRequest.of(page, size, direction, sortParam))
              .stream()
              .map(boundaryEntityConverter::toBoundary)
              .collect(Collectors.toList());
      default -> throw new BadRequestException("inavlid filter type " + filterType);
    };
  }

  @Override
  public void inflateTires(String licenseNumber) {
    if(Helper.checkValidLicenseNumber(licenseNumber) == false) {
      throw new BadRequestException("invalid license number " + licenseNumber);
    }
    
    var vehicleEntity = vehiclesDao
            .findByLicenseNumber(licenseNumber)
            .orElseThrow(() -> new NotFoundException("vehicle number " + licenseNumber + " doesn't exists"));
    
    var wheels = vehicleEntity.getWheels()
            .entrySet()
            .stream()
            .collect(Collectors.toMap(Map.Entry::getKey, v -> new Wheel(vehicleEntity.getMaxTirePressure())));
    
    vehicleEntity.setWheels(wheels);
    vehiclesDao.save(vehicleEntity);
  }

  @Override
  public void refuel(String licenseNumber) {
    // validity check
    if(Helper.checkValidLicenseNumber(licenseNumber) == false) {
      throw new BadRequestException("invalid license number " + licenseNumber);
    }
    
    var vehicleEntity = vehiclesDao
            .findByLicenseNumber(licenseNumber)
            .orElseThrow(() -> new NotFoundException("vehicle number " + licenseNumber + " doesn't exists"));
    
    vehicleEntity.setEnergyPercentage(maxPercentage);
    vehiclesDao.save(vehicleEntity);
  }

  @Override
  public void delete(String licenseNumber) {
    // validity check
    if(Helper.checkValidLicenseNumber(licenseNumber) == false) {
      throw new BadRequestException("invalid license number " + licenseNumber);
    }
    
    var vehicleEntity = vehiclesDao
            .findByLicenseNumber(licenseNumber)
            .orElseThrow(() -> new NotFoundException("vehicle number " + licenseNumber + " doesn't exists"));
    
    vehiclesDao.delete(vehicleEntity);
  }

  @Override
  public void deleteAllVehicles() {
    vehiclesDao.deleteAll();
  }
  
  private String getSortParam(String sortBy) {
    return switch(sortBy) {
      case "id" -> sortBy;
      case "model" -> "modelName";
      case "licenseNumber" -> sortBy;
      case "energy" -> "energyPercentage";
      case "maxPressure" -> "maxTirePressure";
      default -> throw new BadRequestException("invalid sort param " + sortBy);
    };
  }
  
  private Direction getDirection(String order) {
    return switch(order) {
      case "ASC" -> Direction.ASC;
      case "DESC" -> Direction.DESC;
      default -> throw new BadRequestException("invalid sort order " + order);
    };
  }

}
