package garage.util;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;
import garage.vehicles.VehicleType;
import garage.vehicles.util.EnergySource;
import garage.vehicles.util.VehicleTypes;

public class Helper {
  public static final Map<VehicleTypes, Integer> TYPES = new HashMap<>();
  
  static {
    TYPES.put(VehicleTypes.Motorcycle, 2);
    TYPES.put(VehicleTypes.Car, 4);
    TYPES.put(VehicleTypes.Truck, 16);
  }
  
  public static boolean checkValidVehicleType(VehicleType vehicleType) {
    if(vehicleType == null) return false;
    
    if(vehicleType.getType() == null) return false;
    
    boolean valid = true;
    //if vehicle != truck check it's energySource
    if(!vehicleType.getType().equalsIgnoreCase(VehicleTypes.Truck.toString())) {
      valid &= checkValidEnergySource(vehicleType.getEnergySource());
    }
        
    // regardless check for a valid VehicleType.type
    valid &= Arrays.asList(VehicleTypes.values())
            .stream()
            .map(VehicleTypes::toString)
            .anyMatch(val -> val.equalsIgnoreCase(vehicleType.getType()));
    
    return valid;
  }
  
  public static boolean checkValidEnergySource(String energySource) {
    if(energySource == null) return false;
    
    return Arrays.asList(EnergySource.values())
            .stream()
            .map(EnergySource::toString)
            .anyMatch(energyType -> energyType.equalsIgnoreCase(energySource));
  }
  
  public static boolean checkValidLicenseNumber(String licenseNumber) {
    if(licenseNumber == null) return false;
    
    final String pattern = "[0-9]{3}-[0-9]{2}-[0-9]{3}|[0-9]{2}-[0-9]{3}-[0-9]{2}";
    return Pattern.matches(pattern, licenseNumber);
  }
}
