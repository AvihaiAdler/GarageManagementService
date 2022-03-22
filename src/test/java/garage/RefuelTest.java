package garage;

import static org.assertj.core.api.Assertions.assertThat;
import javax.annotation.PostConstruct;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.web.reactive.function.client.WebClient;
import garage.vehicles.DetailedVehicleBoundary;
import garage.vehicles.FuelBoundary;
import garage.vehicles.VehicleBoundary;
import garage.vehicles.VehicleType;
import garage.vehicles.misc.EnergySource;
import garage.vehicles.misc.VehicleTypes;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class RefuelTest {
  private int port;
  private String baseUrl;
  private WebClient webClient;
  
  @LocalServerPort
  public void setPort(int port) {
    this.port = port;
  }
  
  @PostConstruct
  public void init() {
    baseUrl = "http://localhost:" + port + "/api/v1/vehicles";
    webClient = WebClient.create(baseUrl);
  }
  
  @AfterEach
  public void tearDown() {
    webClient.delete()
            .uri("/admin/delete")
            .retrieve()
            .bodyToMono(Void.class)
            .log()
            .block();
  }
  
  @Test
  public void refuelTest() throws Exception {
    // given
    var licenseNumber = "000-00-000";
    var registedFuelPercent = 5;
    var fuel = new FuelBoundary(99);
    var vehicle = new VehicleBoundary(new VehicleType(VehicleTypes.Car.toString(), EnergySource.Electric.toString()), 
            "Honda", 
            licenseNumber, 
            registedFuelPercent, 
            55);
    
    // and
    webClient.post()
            .bodyValue(vehicle)
            .retrieve()
            .bodyToMono(DetailedVehicleBoundary.class)
            .log()
            .block();
    
    // when
    webClient.put()
            .uri("/{licenseNumber}/refuel", licenseNumber)
            .bodyValue(fuel)
            .retrieve()
            .bodyToMono(Void.class)
            .log()
            .block();
    
    // and
    var response = webClient.get()
            .uri("/{licenseNumber}", licenseNumber)
            .retrieve()
            .bodyToMono(DetailedVehicleBoundary.class)
            .log()
            .block();
    
    // then
    assertThat(response.energyPercentage()).isNotEqualTo(registedFuelPercent);
    assertThat(response.energyPercentage()).isEqualTo(fuel.fuelPercentage());
  }
}
