package garage.logic;

import java.util.List;
import garage.vehicles.boundaries.DetailedVehicleBoundary;
import garage.vehicles.boundaries.FuelBoundary;
import garage.vehicles.boundaries.PressureBoundary;
import garage.vehicles.boundaries.VehicleBoundary;

public interface VehicleService {
  DetailedVehicleBoundary addVehicle(VehicleBoundary vehicleBoundary);
  
  DetailedVehicleBoundary getVehicle(String licenseNumber);
  
  List<DetailedVehicleBoundary> getAllVehicles(String filterType, String filterValue, int size, int page, String sortBy, String order);
  
  void inflateTires(String licenseNumber, PressureBoundary pressure);
  
  void refuel(String licenseNumber, FuelBoundary fuel);
  
  void deleteAllVehicles();

  void delete(String licenseNumber);
}
