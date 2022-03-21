package garage.data;

import java.util.Map;
import java.util.Objects;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "vehicles")
public class VehicleEntity {
  private String id;
  private String vehicleType;
  private String energySource;
  private Map<String, Integer> wheels;
  private String modelName;
  private String licenseNumber;
  private int availableEnergyPercentage;
  private int maxTirePressure;
  
  public VehicleEntity() { }
  
  public VehicleEntity(String vehicleType, 
          String energySource, 
          Map<String, Integer> wheels, 
          String modelName, 
          String licenseNumber, 
          int availableEnergyPercentage, 
          int maxTirePressure) {
    id = null;
    this.vehicleType = vehicleType;
    this.energySource = energySource;
    this.wheels = wheels;
    this.modelName = modelName;
    this.licenseNumber = licenseNumber;
    this.availableEnergyPercentage = availableEnergyPercentage;
    this.maxTirePressure = maxTirePressure;
  }

  @Id
  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getVehicleType() {
    return vehicleType;
  }

  public void setVehicleType(String vehicleType) {
    this.vehicleType = vehicleType;
  }

  public String getEnergySource() {
    return energySource;
  }

  public void setEnergySource(String energySource) {
    this.energySource = energySource;
  }

  public Map<String, Integer> getWheels() {
    return wheels;
  }

  public void setWheels(Map<String, Integer> wheels) {
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

  public int getAvailableEnergyPercentage() {
    return availableEnergyPercentage;
  }

  public void setAvailableEnergyPercentage(int availableEnergyPercentage) {
    this.availableEnergyPercentage = availableEnergyPercentage;
  }

  public int getMaxTirePressure() {
    return maxTirePressure;
  }

  public void setMaxTirePressure(int maxTirePressure) {
    this.maxTirePressure = maxTirePressure;
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
    return "VehiclesEntity [id=" + id + ", vehicleType=" + vehicleType + ", energySource=" + energySource + ", wheels="
            + wheels + ", modelName=" + modelName + ", licenseNumber=" + licenseNumber + ", availableEnergyPercentage="
            + availableEnergyPercentage + ", maxTirePressure=" + maxTirePressure + "]";
  }
}
