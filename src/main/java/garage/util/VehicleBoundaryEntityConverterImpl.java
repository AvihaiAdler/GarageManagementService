package garage.util;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.IntStream;
import org.springframework.stereotype.Component;
import garage.data.VehicleEntity;
import garage.vehicles.DetailedVehicleBoundary;
import garage.vehicles.VehicleBoundary;
import garage.vehicles.util.VehicleType;

@Component
public class VehicleBoundaryEntityConverterImpl implements VehicleBoundaryEntityConverter { 
  
  @Override
  public DetailedVehicleBoundary toBoundary(VehicleEntity entity) {
    var vehicleType = entity.getEnergySource().toString() + " " + entity.getVehicleType();
    return new DetailedVehicleBoundary(vehicleType, 
            entity.getWheels(), 
            entity.getModelName(), 
            entity.getLicenseNumber(), 
            entity.getAvailableEnergyPercentage(), 
            entity.getMaxTirePressure());
  }
  
  @Override
  public VehicleEntity toEntity(VehicleBoundary boundary) {
    var energyPercantage = boundary.availableEnergyPercentage();
    if(energyPercantage == null) energyPercantage = 0;
    
    var splitted = boundary.vehicleType().split("\\s+");
    var vehicleType = getType(splitted[1]);
    
    return new VehicleEntity(splitted[1], 
            splitted[0], 
            getWheels(vehicleType), 
            boundary.modelName(), 
            boundary.licenseNumber(), 
            energyPercantage, 
            boundary.maxTirePressure());
  }
  
  private VehicleType getType(String type) {
    return switch(type) {
      case "motorcycle" -> VehicleType.Motorcycle;
      case "car" -> VehicleType.Car;
      case "truck" -> VehicleType.Truck;
      default -> throw new IllegalArgumentException("invalid vehicle type " + type);
    };
  }
  
  private Map<String, Integer> getWheels(VehicleType type) {
    var numOfWheels = Helper.TYPES.get(type);
    var wheels = new HashMap<String, Integer>();
    IntStream.range(0, numOfWheels).boxed().forEach(i -> wheels.put("tire" + i, 0));
    return wheels;
  }
}
