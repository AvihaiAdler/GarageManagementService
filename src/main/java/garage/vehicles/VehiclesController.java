package garage.vehicles;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import garage.logic.VehicleService;

@RestController
@RequestMapping(path = "/api/v1/vehicles")
public class VehiclesController {
  private VehicleService vehiclesService;
  
  @Autowired
  public void setVehiclesService(VehicleService vehiclesService) {
    this.vehiclesService = vehiclesService;
  }
  
  @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, 
          produces = MediaType.APPLICATION_JSON_VALUE)
  public DetailedVehicleBoundary addVehicle(@RequestBody VehicleBoundary vehicleBoundary) {
    return vehiclesService.addVehicle(vehicleBoundary);
  }
  
  @GetMapping(path = "/{licenseNumber}",
           produces = MediaType.APPLICATION_JSON_VALUE)
  public DetailedVehicleBoundary getVehicle(@PathVariable("licenseNumber") String licenseNumber) {
    return vehiclesService.getVehicle(licenseNumber);
  }
  
  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public DetailedVehicleBoundary[] getAllVehicles(
          @RequestParam(name = "filterType", required = false, defaultValue = "") String filterType,
          @RequestParam(name = "filterValue", required = false, defaultValue = "") String filterValue,
          @RequestParam(name = "size", required = false, defaultValue = "20") int size,
          @RequestParam(name = "page", required = false, defaultValue = "0") int page,
          @RequestParam(name = "sortBy", required = false, defaultValue = "licenseNumber") String sortBy,
          @RequestParam(name = "order", required = false, defaultValue = "ASC") String order) {
    return vehiclesService
            .getAllVehicles(filterType, filterValue, size, page, sortBy, order)
            .toArray(new DetailedVehicleBoundary[0]);
  }
  
  @PutMapping(path = "/{licenseNumber}/inflate",
          consumes = MediaType.APPLICATION_JSON_VALUE)
  public void inflateTires(
          @PathVariable("licenseNumber") String licenseNumber, 
          @RequestBody PressureBoundary pressure) {
    vehiclesService.inflateTires(licenseNumber, pressure);
  }
  
  @PutMapping(path = "/{licenseNumber}/refuel", 
          consumes = MediaType.APPLICATION_JSON_VALUE)
  public void refuel(
          @PathVariable("licenseNumber") String licenseNumber, 
          @RequestBody FuelBoundary fuel) {
    vehiclesService.refuel(licenseNumber, fuel);
  }
  
  @DeleteMapping(path = "/{licenseNumber}/delete")
  public void deleteVehicle(@PathVariable("licenseNumber") String licenseNumber) {
    vehiclesService.delete(licenseNumber);
  }
  
  @DeleteMapping(path = "/admin/delete")
  public void deleteAllVehicles() { 
    vehiclesService.deleteAllVehicles();
  }
}
