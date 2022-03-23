package garage.logic;

import garage.data.VehicleEntity;
import garage.vehicles.misc.EnergySourceTypes;

public interface EnergySourceService {
  public void linkVehicleToSource(VehicleEntity vehicleEntity, EnergySourceTypes type);
}
