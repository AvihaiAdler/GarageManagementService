package garage;

import static org.assertj.core.api.Assertions.assertThat;

import javax.annotation.PostConstruct;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.web.reactive.function.client.WebClient;

import garage.util.Helper;
import garage.vehicles.DetailedVehicleBoundary;
import garage.vehicles.VehicleBoundary;
import garage.vehicles.VehicleType;
import garage.vehicles.util.EnergySource;
import garage.vehicles.util.VehicleTypes;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class InflateTests {
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
            .block();
  }
  
  @Test
  public void inflateTest() throws Exception {
    // given
    var licenseNumber = "000-00-000";
    var vehicle = new VehicleBoundary(new VehicleType(VehicleTypes.Car.toString(), EnergySource.Electric.toString()), "Honda", licenseNumber, 5, 55);
    
    // and
    webClient.post()
            .bodyValue(vehicle)
            .retrieve()
            .bodyToMono(DetailedVehicleBoundary.class)
            .log()
            .block();
    
    // when
    webClient.put()
            .uri("/{licenseNumber}/inflate", licenseNumber)
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
    for(int i = 0; i < Helper.TYPES.get(VehicleTypes.Car); i++) {
      assertThat(response.wheels().get("wheel_" + i).getPressure()).isEqualTo(response.maxTirePressure());      
    }
  }
}
