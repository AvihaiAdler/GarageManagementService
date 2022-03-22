package garage.util;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import garage.data.VehicleEntity;
import garage.vehicles.DetailedVehicleBoundary;
import garage.vehicles.VehicleBoundary;
import garage.vehicles.VehicleType;
import garage.vehicles.util.VehicleTypes;
import garage.vehicles.util.Wheel;

@Component
public class VehicleBoundaryEntityConverterImpl implements VehicleBoundaryEntityConverter { 
  private Random rand = new Random();
  private int minPressure;
  
  @Value("${min.pressure:0}")
  public void setMinPressure(int minPressure) {
    this.minPressure = minPressure;
  }
  
  @Override
  public DetailedVehicleBoundary toBoundary(VehicleEntity entity) {
    return new DetailedVehicleBoundary(new VehicleType(entity.getVehicleType(), entity.getEnergySource()), 
            entity.getWheels(), 
            entity.getModelName(), 
            entity.getLicenseNumber(), 
            entity.getEnergyPercentage(), 
            entity.getMaxTirePressure());
  }
  
  @Override
  public VehicleEntity toEntity(VehicleBoundary boundary) {
    var energyPercantage = boundary.energyPercentage();
    if(energyPercantage == null) energyPercantage = 0;
    
    String energySource = null;
    if(getTypeAsEnum(boundary.vehicleType()) != VehicleTypes.Truck) {
      energySource = boundary.vehicleType().getEnergySource().toLowerCase();
    }
    
    return new VehicleEntity(boundary.vehicleType().getType().toLowerCase(), 
            energySource, 
            getWheels(boundary), 
            boundary.modelName().toLowerCase(), 
            boundary.licenseNumber(), 
            energyPercantage, 
            boundary.maxTirePressure());
  }
  
  private Map<String, Wheel> getWheels(VehicleBoundary boundary) {
    var numOfWheels = Helper.TYPES.get(getTypeAsEnum(boundary.vehicleType()));
    
    // initiate a Map of wheels. Map structure represent the wheel & it's current pressure percentage,
    // i.e. (wheel_0, 0), (wheel_3, 50) and so on
    return IntStream.range(0, numOfWheels)
            .boxed()
            .collect(Collectors.toMap(k -> "wheel_" + k, 
                    v -> new Wheel(rand.nextInt(minPressure, boundary.maxTirePressure())), 
                    (k1, k2) -> k2, 
                    TreeMap::new));
  }
  
  private VehicleTypes getTypeAsEnum(VehicleType vehicleType) {
    return switch(vehicleType.getType().toLowerCase()) {
      case "truck" -> VehicleTypes.Truck;
      case "car" -> VehicleTypes.Car;
      case "motorcycle" -> VehicleTypes.Motorcycle;
      default -> throw new IllegalArgumentException("invalid vehicle type " + vehicleType.getType().toLowerCase());
    };
  }
}
