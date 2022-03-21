package garage.data;

import java.util.List;
import java.util.Objects;

import garage.vehicles.util.Wheel;

public class VehicleEntity {
  private String id;
  private String vehicleType;
  private String energySource;
  private List<Wheel> wheels;
  private String modelName;
  private String licenseNumber;
  private int availableEnergyPercentage;
  private int maxTirePressure;
  
  public VehicleEntity() { }
  
  public VehicleEntity(String vehicleType, 
          String energySource, 
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
    VehicleEntity other = (VehicleEntity) obj;
    return Objects.equals(id, other.id);
  }

  @Override
  public String toString() {
    return "VehicleEntity [id=" + id + ", vehicleType=" + vehicleType + ", energySource=" + energySource + ", wheels="
            + wheels + ", modelName=" + modelName + ", licenseNumber=" + licenseNumber + ", availableEnergyPercentage="
            + availableEnergyPercentage + ", maxTirePressure=" + maxTirePressure + "]";
  }
}
