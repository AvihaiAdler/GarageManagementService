package garage.logic;

import garage.data.VehicleEntity;
import garage.vehicles.misc.VehicleTypes;

public interface VehicleTypeService {
  void linkVehicleToService(VehicleEntity vehicleEntity, VehicleTypes type);
  
  void deleteAll();
}
