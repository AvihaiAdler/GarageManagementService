package garage.util;

import garage.data.VehicleEntity;
import garage.vehicles.DetailedVehicleBoundary;
import garage.vehicles.VehicleBoundary;

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
}
