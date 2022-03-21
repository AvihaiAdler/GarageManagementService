package garage.util;
import java.util.List;
import org.springframework.stereotype.Component;
import garage.data.VehicleEntity;
import garage.vehicles.DetailedVehicleBoundary;
import garage.vehicles.VehicleBoundary;
import garage.vehicles.util.EnergySource;
import garage.vehicles.util.TirePosition;
import garage.vehicles.util.VehicleType;
import garage.vehicles.util.Wheel;

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
    var vehicleType = getType(splitted[0]);
    
    return new VehicleEntity(vehicleType, 
            getEnergySource(splitted[1]), 
            getWheels(vehicleType), 
            boundary.modelName(), 
            boundary.licenseNumber(), 
            energyPercantage, 
            boundary.maxTirePressure());
  }
  
  public VehicleType getType(String type) {
    return switch(type) {
      case "motorcycle" -> VehicleType.Motorcycle;
      case "car" -> VehicleType.Car;
      case "truck" -> VehicleType.Truck;
      default -> throw new IllegalArgumentException("invalid vehicle type " + type);
    };
  }
  
  public EnergySource getEnergySource(String energySource) {
    return switch(energySource) {
      case "electric" -> EnergySource.Electric;
      case "fuel" -> EnergySource.Regular;
      default -> throw new IllegalArgumentException("invalid energy source " + energySource);
    };
  }
  
  private List<Wheel> getWheels(VehicleType type) {
    return switch(type) {
      case Motorcycle -> List.of(new Wheel(0, TirePosition.TireOne), 
              new Wheel(0, TirePosition.TireTwo));
      case Car -> List.of(new Wheel(0, TirePosition.TireOne), 
              new Wheel(0, TirePosition.TireTwo), 
              new Wheel(0, TirePosition.TireThree), 
              new Wheel(0, TirePosition.TireFour));
      case Truck ->List.of(new Wheel(0, TirePosition.TireOne), 
              new Wheel(0, TirePosition.TireTwo), 
              new Wheel(0, TirePosition.TireThree), 
              new Wheel(0, TirePosition.TireFour),
              new Wheel(0, TirePosition.TireFive), 
              new Wheel(0, TirePosition.TireSix), 
              new Wheel(0, TirePosition.TireSeven), 
              new Wheel(0, TirePosition.TireEight),
              new Wheel(0, TirePosition.TireNine), 
              new Wheel(0, TirePosition.TireTen), 
              new Wheel(0, TirePosition.TireEleven), 
              new Wheel(0, TirePosition.TireTwelve),
              new Wheel(0, TirePosition.TireThirteen), 
              new Wheel(0, TirePosition.TireFourteen), 
              new Wheel(0, TirePosition.TireFifteen), 
              new Wheel(0, TirePosition.TireSixteen));
    };
  }
}
