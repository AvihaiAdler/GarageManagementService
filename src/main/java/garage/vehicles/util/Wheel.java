package garage.vehicles.util;

public class Wheel {
  Integer tireInlationPercentage;
  TirePosition pos;
  
  public Wheel() { }

  public Wheel(int tireInlationPercentage, TirePosition pos) {
    this.tireInlationPercentage = tireInlationPercentage;
    this.pos = pos;
  }

  public Integer getTireInlationPercentage() {
    return tireInlationPercentage;
  }

  public void setTireInlationPercentage(int tireInlationPercentage) {
    this.tireInlationPercentage = tireInlationPercentage;
  }

  public TirePosition getPos() {
    return pos;
  }

  public void setPos(TirePosition pos) {
    this.pos = pos;
  }
}
