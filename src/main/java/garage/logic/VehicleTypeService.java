package garage.logic;

import garage.data.VehicleEntity;
import garage.vehicles.misc.VehicleTypes;

public interface VehicleTypeService {
  public void linkVehicleToService(VehicleEntity vehicleEntity, VehicleTypes type);
}
