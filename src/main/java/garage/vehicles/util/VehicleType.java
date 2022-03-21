package garage.vehicles.util;

public enum VehicleType {
  Motorcycle("motorcycle"),
  Car("car"),
  Truck("truck");
  
  private final String type;
  
  private VehicleType(final String type) {
    this.type = type;
  }
  
  public String toString() {
    return type;
  }
}
