package garage.vehicles.util;

public enum EnergySource {
  Electric("electric"),
  Regular("regular");
  
  private final String energyType;
  
  private EnergySource(final String energyType) {
    this.energyType = energyType;
  }
  
  public String toString() {
    return energyType;
  }
}
