package garage.dal;

import java.util.Optional;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import garage.data.VehicleTypeEntity;

public interface VehicleTypeDao extends PagingAndSortingRepository<VehicleTypeEntity, String> {
  Optional<VehicleTypeEntity> findByVehicleType(@Param("vehicleType") String vehicleType);
}
