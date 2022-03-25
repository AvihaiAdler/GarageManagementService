package garage.util;

import garage.vehicles.boundaries.VehicleTypeBoundary;
import garage.vehicles.misc.EnergySourceTypes;
import garage.vehicles.misc.VehicleTypes;

public interface Util {

  VehicleTypes getTypeAsEnum(VehicleTypeBoundary vehicleType);
  
  EnergySourceTypes getEnergySourceAsEnum(VehicleTypeBoundary vehicleType);
  
  boolean checkValidLicenseNumber(String licenseNumber);
  
  int getNumberOfWheels(VehicleTypes type);
}
