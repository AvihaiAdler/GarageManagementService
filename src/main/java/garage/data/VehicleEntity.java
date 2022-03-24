package garage.data;

import java.util.Objects;
import java.util.UUID;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


@Entity
@Table(name = "vehicles")
public class VehicleEntity {
  @Id
  private String id;
  
  @Lob
  private String wheels;
  private String modelName;
  private String licenseNumber;
  private int energyPercentage;
  private int maxTirePressure;
  
  @ManyToOne
  private EnergySourceEntity energySource;
  
  @ManyToOne
  private VehicleTypeEntity vehicleType;
  
  public VehicleEntity() { }
  
  public VehicleEntity(String wheels, 
          String modelName, 
          String licenseNumber, 
          int energyPercentage, 
          int maxTirePressure) {
    id = UUID.randomUUID().toString();
    this.wheels = wheels;
    this.modelName = modelName;
    this.licenseNumber = licenseNumber;
    this.energyPercentage = energyPercentage;
    this.maxTirePressure = maxTirePressure;
  }

  
  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

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
  
  public EnergySourceEntity getEnergySource() {
    return energySource;
  }

  public void setEnergySource(EnergySourceEntity energySource) {
    this.energySource = energySource;
  }

  public VehicleTypeEntity getVehicleType() {
    return vehicleType;
  }

  public void setVehicleType(VehicleTypeEntity vehicleType) {
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

  @Override
  public String toString() {
    return "VehicleEntity [id=" + id + ", wheels=" + wheels + ", modelName=" + modelName + ", licenseNumber="
            + licenseNumber + ", energyPercentage=" + energyPercentage + ", maxTirePressure=" + maxTirePressure
            + ", energySource=" + energySource + ", vehicleType=" + vehicleType + "]";
  }
}
