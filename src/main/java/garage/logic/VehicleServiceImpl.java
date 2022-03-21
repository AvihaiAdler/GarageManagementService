package garage.logic;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import garage.dal.VehiclesDao;
import garage.exceptions.BadRequestException;
import garage.exceptions.ForbiddenRequestException;
import garage.exceptions.NotFoundException;
import garage.util.Helper;
import garage.util.VehicleBoundaryEntityConverter;
import garage.vehicles.DetailedVehicleBoundary;
import garage.vehicles.VehicleBoundary;

@Service
public class VehicleServiceImpl implements VehicleService {
  private VehicleBoundaryEntityConverter boundaryEntityConverter;
  private VehiclesDao vehiclesDao;
  private int maxPercentage;
  
  @Autowired
  public void setBoundaryEntityConverter(VehicleBoundaryEntityConverter boundaryEntityConverter) {
    this.boundaryEntityConverter = boundaryEntityConverter;
  }
  
  @Autowired
  public void setVehiclesDao(VehiclesDao vehiclesDao) {
    this.vehiclesDao = vehiclesDao;
  }
  
  @Value("${max.percent}")
  public void setMaxPercentage(int maxPercentage) {
    this.maxPercentage = maxPercentage;
  }
  
  @Override
  public DetailedVehicleBoundary addVehicle(VehicleBoundary vehicleBoundary) {
    if(Helper.checkValidVehicleType(vehicleBoundary.vehicleType()) == false) {
      throw new BadRequestException("invalid vehicle type " + vehicleBoundary.vehicleType());
    }
    
    if(vehicleBoundary.modelName() == null) {
      throw new BadRequestException("vehicle model name must not be null");
    }
    
    if(Helper.checkValidLicenseNumber(vehicleBoundary.licenseNumber()) == false) {
      throw new BadRequestException("invalid license number " + vehicleBoundary.licenseNumber());
    }
    
    if(vehicleBoundary.availableEnergyPercentage() != null && vehicleBoundary.availableEnergyPercentage() < 0) {
      throw new BadRequestException("invalid energy % " + vehicleBoundary.availableEnergyPercentage());
    }
    
    if(vehicleBoundary.maxTirePressure() == null) {
      throw new BadRequestException("maximum tire pressure must not be null"); 
    }
    
    if(vehicleBoundary.maxTirePressure() <= 0) {
      throw new BadRequestException("invalid maximum tire pressure " + vehicleBoundary.maxTirePressure());
    }
    
    vehiclesDao.findByLicenseNumber(vehicleBoundary.licenseNumber()).ifPresent(value -> {
      throw new ForbiddenRequestException("vehicle number " + vehicleBoundary.licenseNumber() + " already exists");
    });
    
    var entity =  vehiclesDao.save(boundaryEntityConverter.toEntity(vehicleBoundary));
    return boundaryEntityConverter.toBoundary(entity);
  }

  @Override
  public DetailedVehicleBoundary getVehicle(String licenseNumber) {
    if(Helper.checkValidLicenseNumber(licenseNumber) == false) {
      throw new BadRequestException("invalid license number " + licenseNumber);
    }
    
    var entity = vehiclesDao.findByLicenseNumber(licenseNumber)
            .orElseThrow(() -> new NotFoundException("Vehicle with " + licenseNumber + " doesn't exists"));
    
    return boundaryEntityConverter.toBoundary(entity);
  }

  @Override
  public List<DetailedVehicleBoundary> getAllVehicles(String filterType, String filterValue, int size, int page, String sortBy, String order) {
    var sortOrder = switch(order) {
      case "ASC" -> Direction.ASC;
      case "DESC" -> Direction.DESC;
      default -> throw new BadRequestException("invalid sort order " + order);
    };
    
    if(Helper.checkValidSortParam(sortBy) == false) {
      throw new BadRequestException("invalid sort parameter " + sortBy);
    }
    
    return switch (filterType) {
      case "" -> vehiclesDao
              .findAll(PageRequest.of(page, size, sortOrder, sortBy))
              .stream()
              .map(boundaryEntityConverter::toBoundary)
              .collect(Collectors.toList());
      case "byEnergyType" -> vehiclesDao
              .findAllByEnergySource(filterValue, PageRequest.of(page, size, sortOrder, sortBy))
              .stream()
              .map(boundaryEntityConverter::toBoundary)
              .collect(Collectors.toList());
      case "byModelName" -> vehiclesDao
              .findAllByModelName(filterValue, PageRequest.of(page, size, sortOrder, sortBy))
              .stream()
              .map(boundaryEntityConverter::toBoundary)
              .collect(Collectors.toList());
      case "byVehicleType" -> vehiclesDao
              .findAllByVehicleType(filterValue, PageRequest.of(page, size, sortOrder, sortBy))
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
    
    vehicleEntity.getWheels().forEach(wheel -> wheel.setTireInlationPercentage(maxPercentage));
    vehiclesDao.save(vehicleEntity);
  }

  @Override
  public void refuel(String licenseNumber) {
    if(Helper.checkValidLicenseNumber(licenseNumber) == false) {
      throw new BadRequestException("invalid license number " + licenseNumber);
    }
    
    var vehicleEntity = vehiclesDao
            .findByLicenseNumber(licenseNumber)
            .orElseThrow(() -> new NotFoundException("vehicle number " + licenseNumber + " doesn't exists"));
    
    vehicleEntity.setAvailableEnergyPercentage(maxPercentage);
    vehiclesDao.save(vehicleEntity);
  }
}
