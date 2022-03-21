package garage.util;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;
import garage.vehicles.util.VehicleType;

public class Helper {
  public static final Map<VehicleType, Integer> TYPES = new HashMap<>();
  
  static {
    TYPES.put(VehicleType.Motorcycle, 2);
    TYPES.put(VehicleType.Car, 4);
    TYPES.put(VehicleType.Truck, 16);
  }
  
  public static boolean checkValidVehicleType(String vehicleType) {
    if(vehicleType == null) return false;
    
    var splitted = vehicleType.split("\\s+");
    
    if(splitted.length < 2) return false;
    
    var valid = Arrays.asList(VehicleType.values())
            .stream()
            .map(VehicleType::toString)
            .anyMatch(val -> val.equalsIgnoreCase(splitted[0]));
    
    valid &= Arrays.asList(VehicleType.values())
            .stream()
            .map(VehicleType::toString)
            .anyMatch(val -> val.equalsIgnoreCase(splitted[1]));
    
    return valid;
  }
  
  public static boolean checkValidLicenseNumber(String licenseNumber) {
    if(licenseNumber == null) return false;
    
    final String pattern = "[0-9]{3}-[0-9]{2}-[0-9]{3}|[0-9]{2}-[0-9]{3}-[0-9]{2}";
    return Pattern.matches(pattern, licenseNumber);
  }
}
