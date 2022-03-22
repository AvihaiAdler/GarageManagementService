package garage.vehicles.misc;

public class Wheel {
  private int pressure;
  
  public Wheel() { }

  public Wheel(int pressure) {
    this.pressure = pressure;
  }

  public int getPressure() {
    return pressure;
  }

  public void setPressure(int pressure) {
    this.pressure = pressure;
  }

  @Override
  public String toString() {
    return "Wheel [pressure=" + pressure + "]";
  }
}
