package garage.data;

import java.util.List;
import java.util.Objects;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import garage.vehicles.util.EnergySource;
import garage.vehicles.util.VehicleType;
import garage.vehicles.util.Wheel;

@Document(collection = "vehicles")
public class VehiclesEntity {
  private String id;
  private VehicleType vehicleType;
  private EnergySource energySource;
  private List<Wheel> wheels;
  private String modelName;
  private String licenseNumber;
  private int availableEnergyPercentage;
  private int maxTirePressure;
  
  public VehiclesEntity() { }
  
  public VehiclesEntity(VehicleType vehicleType, 
          EnergySource energySource, 
          List<Wheel> wheels, 
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

  public VehicleType getVehicleType() {
    return vehicleType;
  }

  public void setVehicleType(VehicleType vehicleType) {
    this.vehicleType = vehicleType;
  }

  public EnergySource getEnergySource() {
    return energySource;
  }

  public void setEnergySource(EnergySource energySource) {
    this.energySource = energySource;
  }

  public List<Wheel> getWheels() {
    return wheels;
  }

  public void setWheels(List<Wheel> wheels) {
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
    VehiclesEntity other = (VehiclesEntity) obj;
    return Objects.equals(id, other.id);
  }

  @Override
  public String toString() {
    return "VehiclesEntity [id=" + id + ", vehicleType=" + vehicleType + ", energySource=" + energySource + ", wheels="
            + wheels + ", modelName=" + modelName + ", licenseNumber=" + licenseNumber + ", availableEnergyPercentage="
            + availableEnergyPercentage + ", maxTirePressure=" + maxTirePressure + "]";
  }
}
