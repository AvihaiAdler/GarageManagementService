package garage.data.dal;

import java.util.Optional;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import garage.data.EnergySourceEntity;

public interface EnergySourceDao extends PagingAndSortingRepository<EnergySourceEntity, String> {
  Optional<EnergySourceEntity> findByEnergyType(@Param("energyType") String energyType);
}
