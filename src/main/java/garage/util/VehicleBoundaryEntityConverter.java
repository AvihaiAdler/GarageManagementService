package garage.util;

import garage.data.VehiclesEntity;
import garage.vehicles.DetailedVehicleBoundary;
import garage.vehicles.VehicleBoundary;
import garage.vehicles.util.EnergySource;
import garage.vehicles.util.VehicleType;

public interface VehicleBoundaryEntityConverter {
  public DetailedVehicleBoundary toBoundary(VehiclesEntity entity);
  
  public VehiclesEntity toEntity(VehicleBoundary boundary);
  
  public VehicleType getType(String type);
  
  public EnergySource getEnergySource(String energySource);
}
