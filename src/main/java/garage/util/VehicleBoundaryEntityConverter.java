package garage.util;

import garage.data.VehicleEntity;
import garage.vehicles.DetailedVehicleBoundary;
import garage.vehicles.VehicleBoundary;

public interface VehicleBoundaryEntityConverter {
  public DetailedVehicleBoundary toBoundary(VehicleEntity entity);
  
  public VehicleEntity toEntity(VehicleBoundary boundary);
}
