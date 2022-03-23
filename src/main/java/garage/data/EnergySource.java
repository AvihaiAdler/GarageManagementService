package garage.data;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "energy_source")
public class EnergySource {
  private String id;
  private String energySourceType;
  
  private Set<VehicleEntity> vehicles;

  public String getId() {
    return id;
  }

  @Id
  @GeneratedValue
  public void setId(String id) {
    this.id = id;
  }

  public String getEnergySourceType() {
    return energySourceType;
  }

  public void setEnergySourceType(String energySourceType) {
    this.energySourceType = energySourceType;
  }
  
  @OneToMany(fetch = FetchType.LAZY, 
          mappedBy = "energy_source", 
          cascade = CascadeType.ALL)
  public Set<VehicleEntity> getVehicles() {
    return vehicles;
  }

  public void setVehicles(Set<VehicleEntity> vehicles) {
    this.vehicles = vehicles;
  }
  
  
}
