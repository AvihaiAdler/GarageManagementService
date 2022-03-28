package garage.dal;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import garage.data.VehicleTypeEntity;

public interface VehicleTypeDao extends PagingAndSortingRepository<VehicleTypeEntity, String> {
  @Query("select v from VehicleTypeEntity v where v.vehicleType = :vehicleType")
  Optional<VehicleTypeEntity> findByVehicleType(@Param("vehicleType") String vehicleType);
}
