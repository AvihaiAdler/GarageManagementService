package garage.dal;

import org.springframework.data.mongodb.repository.MongoRepository;

import garage.data.VehiclesEntity;

public interface VehiclesDao extends MongoRepository<VehiclesEntity, String> {

}
