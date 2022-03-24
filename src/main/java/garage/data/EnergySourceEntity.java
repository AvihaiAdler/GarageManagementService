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
@Table(name = "energy_source")
public class EnergySourceEntity {
  @Id
  private String id;
  
  private String energyType;
  
  @OneToMany(fetch = FetchType.LAZY, 
          mappedBy = "energySource", 
          cascade = CascadeType.MERGE)
  private Set<VehicleEntity> vehicles;

  public EnergySourceEntity() { 
    id = UUID.randomUUID().toString();
    vehicles = new HashSet<>();
  }
  
  public EnergySourceEntity(String energyType) {
    id = UUID.randomUUID().toString();
    this.energyType = energyType;
    
    vehicles = new HashSet<>();
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getEnergyType() {
    return energyType;
  }

  public void setEnergyType(String energyType) {
    this.energyType = energyType;
  }
  
  public Set<VehicleEntity> getVehicles() {
    return vehicles;
  }

  public void setVehicles(Set<VehicleEntity> vehicles) {
    this.vehicles = vehicles;
  }

  public EnergySourceEntity addVehicle(VehicleEntity vehicleEntity) {
    vehicles.add(vehicleEntity);
    vehicleEntity.setEnergySource(this);
    return this;
  }
  
  @Override
  public int hashCode() {
    return Objects.hash(energyType, id, vehicles);
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    EnergySourceEntity other = (EnergySourceEntity) obj;
    return Objects.equals(energyType, other.energyType) && Objects.equals(id, other.id)
            && Objects.equals(vehicles, other.vehicles);
  }

  @Override
  public String toString() {
    return "EnergySource [id=" + id + ", energySourceType=" + energyType + ", vehicles=" + vehicles + "]";
  }

}
