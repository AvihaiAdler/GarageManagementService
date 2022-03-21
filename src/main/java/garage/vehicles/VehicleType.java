package garage.vehicles;

import java.util.Objects;

public class VehicleType {
  private String type;
  private String energySource;
  
  public VehicleType() { }

  public VehicleType(String type, String energySource) {
    this.type = type;
    this.energySource = energySource;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public String getEnergySource() {
    return energySource;
  }

  public void setEnergySource(String energySource) {
    this.energySource = energySource;
  }
  
  @Override
  public int hashCode() {
    return Objects.hash(energySource, type);
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    VehicleType other = (VehicleType) obj;
    return Objects.equals(energySource, other.energySource) && Objects.equals(type, other.type);
  }

  @Override
  public String toString() {
    return "VehicleType [type=" + type + ", energySource=" + energySource + "]";
  }
}
