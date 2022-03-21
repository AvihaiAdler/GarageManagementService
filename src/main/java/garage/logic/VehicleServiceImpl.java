package garage.logic;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import garage.dal.VehiclesDao;
import garage.exceptions.BadRequestException;
import garage.util.Helper;
import garage.util.VehicleBoundaryEntityConverter;
import garage.vehicles.DetailedVehicleBoundary;
import garage.vehicles.VehicleBoundary;

@Service
public class VehicleServiceImpl implements VehicleService {
  
  private VehicleBoundaryEntityConverter boundaryEntityConverter;
  private VehiclesDao vehiclesDao;
  
  @Autowired
  public void setBoundaryEntityConverter(VehicleBoundaryEntityConverter boundaryEntityConverter) {
    this.boundaryEntityConverter = boundaryEntityConverter;
  }
  
  @Autowired
  public void setVehiclesDao(VehiclesDao vehiclesDao) {
    this.vehiclesDao = vehiclesDao;
  }
  
  @Override
  public void addVehicle(VehicleBoundary vehicleBoundary) {
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
    
    vehiclesDao.save(boundaryEntityConverter.toEntity(vehicleBoundary));
  }

  @Override
  public DetailedVehicleBoundary getVehicle(String licenseNumber) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public List<DetailedVehicleBoundary> getAllVehicles(int size, int page, String sortBy, String order) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public void inflateTires(String licenseNumber) {
    // TODO Auto-generated method stub

  }

  @Override
  public void refuel(String licenseNumber) {
    // TODO Auto-generated method stub

  }

}
