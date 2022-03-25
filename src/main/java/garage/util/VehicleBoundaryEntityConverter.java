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
  DetailedVehicleBoundary toBoundary(VehicleEntity entity);
  
  /**
   * Converts a {@code VehicleBoundary} to {@code VehicleEntity} and initializes {@code VehicleEntity}'s wheels with random values
   * @param boundary : {@code VehicleBoundary}
   * @return {@code VehicleEntity}
   */
  VehicleEntity toEntity(VehicleBoundary boundary);
  
  Map<String, Wheel> jsonToMap(String wheels);
  
  String mapToJson(Map<String, Wheel> wheels);
}
