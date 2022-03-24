package garage.dal;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import garage.data.VehicleEntity;


public interface VehiclesDao extends PagingAndSortingRepository<VehicleEntity, String> {
  
  public Optional<VehicleEntity> findByLicenseNumber(@Param("licenseNumber") String licenseNumber);
  
  public Page<VehicleEntity> findAllByVehicleType_vehicleType(@Param("type") String type, Pageable page);

  public Page<VehicleEntity> findAllByEnergySource_energyType(@Param("type") String type, Pageable page);
  
  public Page<VehicleEntity> findAllByModelName(@Param("modelName") String modelName, Pageable page);
}
