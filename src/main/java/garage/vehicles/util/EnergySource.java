package garage.vehicles.util;

public enum EnergySource {
  Electric("electric"),
  Fuel("fuel");
  
  private final String energyType;
  
  private EnergySource(final String energyType) {
    this.energyType = energyType;
  }
  
  public String toString() {
    return energyType;
  }
}
