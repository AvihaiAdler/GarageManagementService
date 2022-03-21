package garage.vehicles;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
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
  
  @PostMapping(/*path = "/add",*/
          consumes = MediaType.APPLICATION_JSON_VALUE)
  public void addVehicle(@RequestBody VehicleBoundary vehicleBoundary) {
    vehiclesService.addVehicle(vehicleBoundary);
  }
  
  @GetMapping(path = "/{licenseNumber}",
           produces = MediaType.APPLICATION_JSON_VALUE)
  public DetailedVehicleBoundary getVehicle(@PathVariable("licenseNumber") String licenseNumber) {
    return vehiclesService.getVehicle(licenseNumber);
  }
  
  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public DetailedVehicleBoundary[] getAllVehicles(
          @RequestParam(name = "size", required = false, defaultValue = "20") int size,
          @RequestParam(name = "page", required = false, defaultValue = "0") int page,
          @RequestParam(name = "sortBy", required = false, defaultValue = "licenseNumber") String sortBy,
          @RequestParam(name = "order", required = false, defaultValue = "ASC") String order) {
    return vehiclesService
            .getAllVehicles(size, page, sortBy, order)
            .toArray(new DetailedVehicleBoundary[0]);
  }
  
  @PutMapping(path = "/inflate/{licenseNumber}", 
          consumes = MediaType.APPLICATION_JSON_VALUE)
  public void inflateTires(@PathVariable("licenseNumber") String licenseNumber) {
    vehiclesService.inflateTires(licenseNumber);
  }
  
  @PutMapping(path = "/refuel/{licenseNumber}", 
          consumes = MediaType.APPLICATION_JSON_VALUE)
  public void refuel(@PathVariable("licenseNumber") String licenseNumber) {
    vehiclesService.refuel(licenseNumber);
  }
}
