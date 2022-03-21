package garage.util;

import garage.data.VehicleEntity;
import garage.vehicles.DetailedVehicleBoundary;
import garage.vehicles.VehicleBoundary;
import garage.vehicles.util.EnergySource;
import garage.vehicles.util.VehicleType;

public interface VehicleBoundaryEntityConverter {
  public DetailedVehicleBoundary toBoundary(VehicleEntity entity);
  
  public VehicleEntity toEntity(VehicleBoundary boundary);
  
  public VehicleType getType(String type);
  
  public EnergySource getEnergySource(String energySource);
}
