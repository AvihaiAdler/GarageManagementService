package garage.util;

import java.util.regex.Pattern;
import org.springframework.stereotype.Component;
import garage.vehicles.boundaries.VehicleTypeBoundary;
import garage.vehicles.misc.EnergySourceTypes;
import garage.vehicles.misc.VehicleTypes;

@Component
public class UtilImpl implements Util {

  @Override
  public VehicleTypes getTypeAsEnum(VehicleTypeBoundary vehicleType) {
    if(vehicleType == null || vehicleType.getType() == null) {
      throw new NullPointerException("vehicleType must not be null");
    }
    
    return switch(vehicleType.getType().toLowerCase()) {
      case "truck" -> VehicleTypes.Truck;
      case "car" -> VehicleTypes.Car;
      case "motorcycle" -> VehicleTypes.Motorcycle;
      default -> throw new IllegalArgumentException("invalid vehicle type " + vehicleType.getType().toLowerCase());
    };
  }

  @Override
  public EnergySourceTypes getEnergySourceAsEnum(VehicleTypeBoundary vehicleType) {
    if(vehicleType == null || vehicleType.getEnergySource() == null) {
      throw new NullPointerException("vehicleType must not be null");
    }
    
    return switch(vehicleType.getEnergySource().toLowerCase()) {
      case "electric" -> EnergySourceTypes.Electric;
      case "regular" -> EnergySourceTypes.Regular;
      default -> throw new IllegalArgumentException("invalid vehicle type " + vehicleType.getType().toLowerCase());
    };
  }

  @Override
  public boolean checkValidLicenseNumber(String licenseNumber) {
    if(licenseNumber == null) return false;
    
    final String pattern = "[0-9]{3}-[0-9]{2}-[0-9]{3}|[0-9]{2}-[0-9]{3}-[0-9]{2}";
    return Pattern.matches(pattern, licenseNumber);
  }

  @Override
  public int getNumberOfWheels(VehicleTypes type) {
    return switch(type) {
      case Motorcycle -> 2;
      case Car -> 4;
      case Truck -> 16;
    };
  }
}
