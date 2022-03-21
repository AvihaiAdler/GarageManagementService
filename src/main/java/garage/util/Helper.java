package garage.util;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import garage.vehicles.util.EnergySource;
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
    
    var valid = Arrays.asList(EnergySource.values())
            .stream()
            .map(EnergySource::toString)
            .anyMatch(val -> val.equalsIgnoreCase(splitted[0]));
    
    valid &= Arrays.asList(VehicleType.values())
            .stream()
            .map(VehicleType::toString)
            .anyMatch(val -> val.equalsIgnoreCase(splitted[1]));
    
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
  
  public static boolean checkValidSortParam(String sortParam) {
    final var sortParams = List.of("id", "model_name", "license_number", "energy_percentage", "tire_pressure");
    
    return sortParams
            .stream()
            .anyMatch(param -> param.equals(sortParam));
  }
}
