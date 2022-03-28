package garage.vehicles;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import garage.data.dal.EnergySourceDao;
import garage.data.EnergySourceEntity;
import garage.data.VehicleEntity;
import garage.logic.EnergySourceService;
import garage.vehicles.misc.EnergySourceTypes;

@Service
public class EnergySourceServiceImpl implements EnergySourceService {
  private EnergySourceDao energySourceDao;

  public EnergySourceServiceImpl(EnergySourceDao energySourceDao) {
    this.energySourceDao = energySourceDao;
  }

  @Override
  @Transactional
  public void linkVehicleToSource(VehicleEntity vehicleEntity, EnergySourceTypes type) {
    var energySourceEntity = energySourceDao
            .findByEnergyType(type.toString().toLowerCase())
            .orElse(new EnergySourceEntity(type.toString().toLowerCase()));
    
    energySourceDao.save(energySourceEntity.addVehicle(vehicleEntity));
  }

  @Override
  public void deleteAll() {
    energySourceDao.deleteAll();
  }
}
