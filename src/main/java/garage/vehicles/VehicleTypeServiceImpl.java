package garage.vehicles;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import garage.dal.VehicleTypeDao;
import garage.data.VehicleEntity;
import garage.data.VehicleTypeEntity;
import garage.logic.VehicleTypeService;
import garage.vehicles.misc.VehicleTypes;

@Service
public class VehicleTypeServiceImpl implements VehicleTypeService {
  private VehicleTypeDao vehicleTypeDao;
  
  public VehicleTypeServiceImpl(VehicleTypeDao vehicleTypeDao) {
    this.vehicleTypeDao = vehicleTypeDao;
  }
  
  @Override
  @Transactional
  public void linkVehicleToService(VehicleEntity vehicleEntity, VehicleTypes type) {
    var typeEntity = vehicleTypeDao
            .findByVehicleType(type.toString().toLowerCase())
            .orElse(new VehicleTypeEntity(type.toString().toLowerCase()));
    
    vehicleTypeDao.save(typeEntity.addVehicle(vehicleEntity));    
  }

  @Override
  public void deleteAll() {
    vehicleTypeDao.deleteAll();
  }
}
