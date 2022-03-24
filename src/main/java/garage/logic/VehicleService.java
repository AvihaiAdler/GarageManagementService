package garage.logic;

import java.util.List;
import garage.vehicles.boundaries.DetailedVehicleBoundary;
import garage.vehicles.boundaries.FuelBoundary;
import garage.vehicles.boundaries.PressureBoundary;
import garage.vehicles.boundaries.VehicleBoundary;

public interface VehicleService {
  public DetailedVehicleBoundary addVehicle(VehicleBoundary vehicleBoundary);
  
  public DetailedVehicleBoundary getVehicle(String licenseNumber);
  
  public List<DetailedVehicleBoundary> getAllVehicles(String filterType, String filterValue, int size, int page, String sortBy, String order);
  
  public void inflateTires(String licenseNumber, PressureBoundary pressure);
  
  public void refuel(String licenseNumber, FuelBoundary fuel);
  
  public void deleteAllVehicles();

  public void delete(String licenseNumber);
}
