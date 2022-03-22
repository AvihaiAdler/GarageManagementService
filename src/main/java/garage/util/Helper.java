package garage.util;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;
import garage.vehicles.VehicleType;
import garage.vehicles.misc.EnergySource;
import garage.vehicles.misc.VehicleTypes;

public class Helper {
  // holds the number of wheels for each vehicle
  public static final Map<VehicleTypes, Integer> TYPES = new HashMap<>();
  
  static {
    TYPES.put(VehicleTypes.Motorcycle, 2);
    TYPES.put(VehicleTypes.Car, 4);
    TYPES.put(VehicleTypes.Truck, 16);
  }
  
  /**
   * Returns {@code true} for a valid VehicleType, {@code false} otherwise 
   * @param vehicleType : {@code String}
   * @return {@code true} if:
   * <ul>
   *  <li> {@code VehicleType} isn't null
   *  <li> and {@code VehicleType.getType()} isn't {@code null}
   *  <li> and {@code VehicleType.getType()} && {@code VehicleType.getEnergySource()} meets the required values
   * </ul>
   * {@code false} otherwise 
   */
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
  
  /**
   * Returns {@code true} for a valid energySource, {@code false} otherwise 
   * @param energySource : {@code String}
   * @return {@code true} if:
   * <ul>
   *  <li> {@code energySource} isn't null
   *  <li> and {@code energySource} is a valid energy source
   * </ul>
   * {@code false} otherwise 
   */
  public static boolean checkValidEnergySource(String energySource) {
    if(energySource == null) return false;
    
    return Arrays.asList(EnergySource.values())
            .stream()
            .map(EnergySource::toString)
            .anyMatch(energyType -> energyType.equalsIgnoreCase(energySource));
  }
  
  /**
   * Validates a license number of a 7 digits or 8 digits pattern
   * @param licenseNumber : {@code String}
   * @return {@code true} if the number is a valid license number of pattern 7/8 digits used in IL.
   * <br>
   * {@code false} otherwise
   */
  public static boolean checkValidLicenseNumber(String licenseNumber) {
    if(licenseNumber == null) return false;
    
    final String pattern = "[0-9]{3}-[0-9]{2}-[0-9]{3}|[0-9]{2}-[0-9]{3}-[0-9]{2}";
    return Pattern.matches(pattern, licenseNumber);
  }
}
