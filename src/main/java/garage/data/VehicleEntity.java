package garage.data;

import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


@Entity
@Table(name = "vehicles")
public class VehicleEntity {
  private String id;
  private String wheels;
  private String modelName;
  private String licenseNumber;
  private int energyPercentage;
  private int maxTirePressure;
  
  private EnergySource energySource;
  
  private VehicleType vehicleType;
  
  public VehicleEntity() { }
  
  public VehicleEntity(String wheels, 
          String modelName, 
          String licenseNumber, 
          int energyPercentage, 
          int maxTirePressure) {
    this.wheels = wheels;
    this.modelName = modelName;
    this.licenseNumber = licenseNumber;
    this.energyPercentage = energyPercentage;
    this.maxTirePressure = maxTirePressure;
  }

  
  public String getId() {
    return id;
  }

  @Id
  @GeneratedValue
  public void setId(String id) {
    this.id = id;
  }

  @Lob
  public String getWheels() {
    return wheels;
  }

  public void setWheels(String wheels) {
    this.wheels = wheels;
  }

  public String getModelName() {
    return modelName;
  }

  public void setModelName(String modelName) {
    this.modelName = modelName;
  }

  public String getLicenseNumber() {
    return licenseNumber;
  }

  public void setLicenseNumber(String licenseNumber) {
    this.licenseNumber = licenseNumber;
  }

  public int getEnergyPercentage() {
    return energyPercentage;
  }

  public void setEnergyPercentage(int energyPercentage) {
    this.energyPercentage = energyPercentage;
  }

  public int getMaxTirePressure() {
    return maxTirePressure;
  }

  public void setMaxTirePressure(int maxTirePressure) {
    this.maxTirePressure = maxTirePressure;
  }
  
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "energy_source")
  public EnergySource getEnergySource() {
    return energySource;
  }

  public void setEnergySource(EnergySource energySource) {
    this.energySource = energySource;
  }

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "vehicle_type")
  public VehicleType getVehicleType() {
    return vehicleType;
  }

  public void setVehicleType(VehicleType vehicleType) {
    this.vehicleType = vehicleType;
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    VehicleEntity other = (VehicleEntity) obj;
    return Objects.equals(id, other.id);
  }
}
