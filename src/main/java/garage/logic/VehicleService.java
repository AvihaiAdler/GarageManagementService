package garage.logic;

import java.util.List;

import garage.vehicles.DetailedVehicleBoundary;
import garage.vehicles.VehicleBoundary;

public interface VehicleService {
  public void addVehicle(VehicleBoundary vehicleBoundary);
  
  public DetailedVehicleBoundary getVehicle(String licenseNumber);
  
  public List<DetailedVehicleBoundary> getAllVehicles(int size, int page, String sortBy, String order);
  
  public void inflateTires(String licenseNumber);
  
  public void refuel(String licenseNumber);
}
