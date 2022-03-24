package garage.data;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "vehicle_type")
public class VehicleTypeEntity {
  @Id
  private String id;
  
  private String vehicleType;
  
  @OneToMany(fetch = FetchType.LAZY,
          mappedBy = "vehicleType", 
          cascade = CascadeType.ALL)
  private Set<VehicleEntity> vehicles;
  
  public VehicleTypeEntity() { 
    id = UUID.randomUUID().toString();
    vehicles = new HashSet<>();
  }

  public VehicleTypeEntity(String vehicleType) {
    id = UUID.randomUUID().toString();
    this.vehicleType = vehicleType;
    
    vehicles = new HashSet<>();
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getVehicleType() {
    return vehicleType;
  }

  public void setVehicleType(String type) {
    this.vehicleType = type;
  }

  public Set<VehicleEntity> getVehicles() {
    return vehicles;
  }

  public void setVehicles(Set<VehicleEntity> vehicles) {
    this.vehicles = vehicles;
  }

  public VehicleTypeEntity addVehicle(VehicleEntity vehicle) {
    vehicles.add(vehicle);
    vehicle.setVehicleType(this);
    return this;
  }
  
  @Override
  public int hashCode() {
    return Objects.hash(id, vehicleType, vehicles);
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    VehicleTypeEntity other = (VehicleTypeEntity) obj;
    return Objects.equals(id, other.id) && Objects.equals(vehicleType, other.vehicleType) && Objects.equals(vehicles, other.vehicles);
  }

  @Override
  public String toString() {
    return "VehicleType [id=" + id + ", type=" + vehicleType + ", vehicles=" + vehicles + "]";
  }
}
