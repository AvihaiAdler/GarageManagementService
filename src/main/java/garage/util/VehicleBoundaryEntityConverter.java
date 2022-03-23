package garage.util;

import java.util.Map;
import garage.data.VehicleEntity;
import garage.vehicles.boundaries.DetailedVehicleBoundary;
import garage.vehicles.boundaries.VehicleBoundary;
import garage.vehicles.misc.Wheel;

public interface VehicleBoundaryEntityConverter {
  /**
   * Converts a {@code VehicleEntity} to {@code DetailedVehicleBoundary}
   * @param entity : {@code VehicleEntity}
   * @return {@code DetailedVehicleBoundary}
   */
  public DetailedVehicleBoundary toBoundary(VehicleEntity entity);
  
  /**
   * Converts a {@code VehicleBoundary} to {@code VehicleEntity} and initializes {@code VehicleEntity}'s wheels with random values
   * @param boundary : {@code VehicleBoundary}
   * @return {@code VehicleEntity}
   */
  public VehicleEntity toEntity(VehicleBoundary boundary);
  
  public Map<String, Wheel> jsonToMap(String wheels);
  
  public String mapToJson(Map<String, Wheel> wheels);
}
