package garage.dal;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import garage.data.EnergySourceEntity;

public interface EnergySourceDao extends PagingAndSortingRepository<EnergySourceEntity, String> {
  @Query("select e from EnergySourceEntity e where e.energyType = :energyType")
  Optional<EnergySourceEntity> findByEnergyType(@Param("energyType") String energyType);
}
