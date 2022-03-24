package garage.util;

import java.util.Map;
import java.util.Random;
import java.util.TreeMap;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import garage.data.VehicleEntity;
import garage.vehicles.boundaries.DetailedVehicleBoundary;
import garage.vehicles.boundaries.VehicleBoundary;
import garage.vehicles.boundaries.VehicleTypeBoundary;
import garage.vehicles.misc.Wheel;

@Component
public class VehicleBoundaryEntityConverterImpl implements VehicleBoundaryEntityConverter { 
  private final ObjectMapper jackson;
  private final Random rand;
  private Util util;
  private int minPressure;
  
  public VehicleBoundaryEntityConverterImpl(Util util) {
    rand = new Random();
    jackson = new ObjectMapper();
    this.util = util;
  }
  
  @Value("${min.pressure:0}")
  public void setMinPressure(int minPressure) {
    this.minPressure = minPressure;
  }
  
  @Override
  public DetailedVehicleBoundary toBoundary(VehicleEntity entity) {
    String energySource = null;
    if(entity.getEnergySource() != null) {
      energySource = entity.getEnergySource().getEnergyType();
    }
    return new DetailedVehicleBoundary(new VehicleTypeBoundary(entity.getVehicleType().getVehicleType(), energySource), 
            jsonToMap(entity.getWheels()), 
            entity.getModelName(), 
            entity.getLicenseNumber(), 
            entity.getEnergyPercentage(), 
            entity.getMaxTirePressure());
  }
  
  @Override
  public VehicleEntity toEntity(VehicleBoundary boundary) {
    var energyPercantage = boundary.energyPercentage();
    
    // treat null energyPercantage value as 0
    if(energyPercantage == null) energyPercantage = 0;
    
    return new VehicleEntity(mapToJson(getWheels(boundary)), 
            boundary.modelName().toLowerCase(), 
            boundary.licenseNumber(), 
            energyPercantage, 
            boundary.maxTirePressure());
  }

  @Override
  public Map<String, Wheel> jsonToMap(String wheels) {
    try {
      return jackson.readValue(wheels, new TypeReference<Map<String, Wheel>>() {});
    } catch (JsonProcessingException jp) {
      throw new RuntimeException(jp);
    }
  }

  @Override
  public String mapToJson(Map<String, Wheel> wheels) {
    try {
      return jackson.writeValueAsString(wheels);
    }catch (JsonProcessingException je) {
      throw new RuntimeException(je);
    }
  }
  
  /**
   * Returns a {@code Map} of {@code Wheel}s initialized as random values
   * @param boundary : {@code VehicleBoundary}
   * @return {@code Map<String, Wheel>}
   */
  private Map<String, Wheel> getWheels(VehicleBoundary boundary) {
    var type = util.getTypeAsEnum(boundary.vehicleType());
    var numOfWheels = util.getNumberOfWheels(type);
    
    // create a Map of wheels. Map structure represent the wheel & it's current pressure percentage,
    // i.e. (wheel_0, 0), (wheel_3, 50) and so on
    return IntStream.range(0, numOfWheels)
            .boxed()
            .collect(Collectors.toMap(k -> "wheel_" + k, 
                    v -> new Wheel(rand.nextInt(boundary.maxTirePressure() - minPressure + 1) + minPressure), 
                    (k1, k2) -> k2, 
                    TreeMap::new));
  }
}
