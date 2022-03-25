package garage;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import javax.annotation.PostConstruct;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import garage.util.Util;
import garage.vehicles.boundaries.DetailedVehicleBoundary;
import garage.vehicles.boundaries.PressureBoundary;
import garage.vehicles.boundaries.VehicleBoundary;
import garage.vehicles.boundaries.VehicleTypeBoundary;
import garage.vehicles.misc.EnergySourceTypes;
import garage.vehicles.misc.VehicleTypes;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class InflateTests {
  private int port;
  private WebClient webClient;
  private Util util;
  
  @Autowired
  public void setUtil(Util util) {
    this.util = util;
  }
  
  @LocalServerPort
  public void setPort(int port) {
    this.port = port;
  }
  
  @PostConstruct
  public void init() {
    String baseUrl = "http://localhost:" + port + "/api/v1/vehicles";
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
  public void inflateValidPressureTest() throws Exception {
    // given
    var licenseNumber = "000-00-000";
    var vehicle = new VehicleBoundary(new VehicleTypeBoundary(VehicleTypes.Car.toString(), EnergySourceTypes.Electric.toString()), "Honda", licenseNumber, 5, 55);
    var pressure = new PressureBoundary(50);
    
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
            .bodyValue(pressure)
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
    assertThat(response).isNotNull();
    for(int i = 0; i < util.getNumberOfWheels(VehicleTypes.Car); i++) {
      assertThat(response.wheels().get("wheel_" + i).getPressure()).isEqualTo(pressure.pressure());      
    }
  }
  
  @Test
  public void inflateInvalidPressureTest() throws Exception {
    // given
    var licenseNumber = "000-00-000";
    var originalPressure = 55;
    var vehicle = new VehicleBoundary(new VehicleTypeBoundary(VehicleTypes.Car.toString(), EnergySourceTypes.Electric.toString()), 
            "Honda", 
            licenseNumber, 
            5, 
            originalPressure);
    var invalidPressure = new PressureBoundary(60);
    
    // and
    webClient.post()
            .bodyValue(vehicle)
            .retrieve()
            .bodyToMono(DetailedVehicleBoundary.class)
            .log()
            .block();
    
    // when
    // then
    assertThrows(WebClientResponseException.BadRequest.class, () -> webClient.put()
            .uri("/{licenseNumber}/inflate", licenseNumber)
            .bodyValue(invalidPressure)
            .retrieve()
            .bodyToMono(Void.class)
            .log()
            .block());
    
    // and
    var response = webClient.get()
            .uri("/{licenseNumber}", licenseNumber)
            .retrieve()
            .bodyToMono(DetailedVehicleBoundary.class)
            .log()
            .block();
    
    // and
    assertThat(response).isNotNull();
    for(int i = 0; i < util.getNumberOfWheels(VehicleTypes.Car); i++) {
      assertThat(response.wheels().get("wheel_" + i).getPressure()).isNotEqualTo(invalidPressure.pressure()); 
    }
  }
}
