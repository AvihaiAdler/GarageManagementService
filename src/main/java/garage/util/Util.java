package garage.util;

import garage.vehicles.boundaries.VehicleTypeBoundary;
import garage.vehicles.misc.EnergySourceTypes;
import garage.vehicles.misc.VehicleTypes;

public interface Util {

  public VehicleTypes getTypeAsEnum(VehicleTypeBoundary vehicleType);
  
  public EnergySourceTypes getEnergySourceAsEnum(VehicleTypeBoundary vehicleType);
  
  public boolean checkValidLicenseNumber(String licenseNumber);
  
  public int getNumberOfWheels(VehicleTypes type);
}
