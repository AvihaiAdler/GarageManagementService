package garage;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import javax.annotation.PostConstruct;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import garage.vehicles.boundaries.DetailedVehicleBoundary;
import garage.vehicles.boundaries.VehicleBoundary;
import garage.vehicles.boundaries.VehicleTypeBoundary;
import garage.vehicles.misc.EnergySourceTypes;
import garage.vehicles.misc.VehicleTypes;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class DeleteVehicleTest {
  private int port;
  private WebClient webClient;
  
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
            .log()
            .block();
  }
  
  @Test
  public void deleteVehicleTest() throws Exception {
    // given
    var licenseNumber = "000-00-000";
    var vehicle = new VehicleBoundary(new VehicleTypeBoundary(VehicleTypes.Motorcycle.toString(), EnergySourceTypes.Regular.toString()),
            "Suzuki",
            licenseNumber,
            90,
            35);
    
    // and
    webClient.post()
            .bodyValue(vehicle)
            .retrieve()
            .bodyToMono(DetailedVehicleBoundary.class)
            .log()
            .block();
    
    // when
    webClient.delete()
            .uri("/{licenseNumber}/delete", licenseNumber)
            .retrieve()
            .bodyToMono(Void.class)
            .log()
            .block();
    
    // and
    // then
    assertThrows(WebClientResponseException.NotFound.class, () -> webClient.get()
            .uri("/{licenseNumber}", licenseNumber)
            .retrieve()
            .bodyToMono(DetailedVehicleBoundary.class)
            .log()
            .block());
  }
  
  @Test
  public void deleteInavlidVehicleTest() throws Exception {
    // given
    var originalLicenseNumber = "000-00-000";
    var anotherLicenseNumber = "00-000-00";
    var vehicle = new VehicleBoundary(new VehicleTypeBoundary(VehicleTypes.Motorcycle.toString(), EnergySourceTypes.Regular.toString()),
            "Suzuki",
            originalLicenseNumber,
            90,
            35);
    
    // and
    webClient.post()
            .bodyValue(vehicle)
            .retrieve()
            .bodyToMono(DetailedVehicleBoundary.class)
            .log()
            .block();
    
    // when
    // then
    assertThrows(WebClientResponseException.NotFound.class, () -> webClient.delete()
            .uri("/{licenseNumber}/delete", anotherLicenseNumber)
            .retrieve()
            .bodyToMono(Void.class)
            .log()
            .block());
    
    // and
    var response = webClient.get()
            .uri("/{licenseNumber}", originalLicenseNumber)
            .retrieve()
            .bodyToMono(DetailedVehicleBoundary.class)
            .log()
            .block();
    
    assertThat(response).isNotNull();
    assertThat(response.licenseNumber()).isEqualTo(originalLicenseNumber);
  }
}
