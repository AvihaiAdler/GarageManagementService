package garage;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import javax.annotation.PostConstruct;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import garage.data.dal.VehiclesDao;
import garage.util.Util;
import garage.vehicles.boundaries.DetailedVehicleBoundary;
import garage.vehicles.boundaries.VehicleBoundary;
import garage.vehicles.boundaries.VehicleTypeBoundary;
import garage.vehicles.misc.VehicleTypes;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class AddVehicleTests {
  private int port;
  private WebClient webClient;
  private VehiclesDao vehiclesDao;
  private Util util;
  private int minPressure;
  private int maxPressure;

  @Autowired
  public void setVehiclesDao(VehiclesDao vehiclesDao) {
    this.vehiclesDao = vehiclesDao;
  }
  
  @Autowired
  public void setUtil(Util util) {
    this.util = util;
  }
  
  @LocalServerPort
  public void setPort(int port) {
    this.port = port;
  }
  
  @Value("${min.pressure}")
  public void setMinPressure(int minPressure) {
    this.minPressure = minPressure;
  }
  
  @Value("${max.pressure}")
  public void setMaxPressure(int maxPressure) {
    this.maxPressure = maxPressure;
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
  public void addValidVehicleTest() throws Exception {
    // given
    var vehicleBoundary = new VehicleBoundary(new VehicleTypeBoundary("car", "Electric"), "Hyundai", "00-000-00", 20,
            50);

    // when
    var response = webClient.post()
            .bodyValue(vehicleBoundary)
            .retrieve()
            .bodyToMono(DetailedVehicleBoundary.class)
            .log()
            .block();

    // then
    assertThat(response).isNotNull();
    assertThat(response.vehicleType().getType()).isEqualTo(vehicleBoundary.vehicleType().getType().toLowerCase());
    assertThat(response.vehicleType().getEnergySource())
            .isEqualTo(vehicleBoundary.vehicleType().getEnergySource().toLowerCase());
    assertThat(response.wheels().size()).isEqualTo(util.getNumberOfWheels(VehicleTypes.Car));
    assertThat(response.modelName()).isEqualTo(vehicleBoundary.modelName().toLowerCase());
    assertThat(response.licenseNumber()).isEqualTo(vehicleBoundary.licenseNumber());
    assertThat(response.energyPercentage()).isEqualTo(vehicleBoundary.energyPercentage());
    assertThat(response.maxTirePressure()).isEqualTo(vehicleBoundary.maxTirePressure());

    // wheels checks
    assertThat(response.wheels()).isNotNull();
    assertThat(response.wheels()).isNotEmpty();
    assertThat(response.wheels().get("wheel_0")).isNotNull();
    assertThat(response.wheels().get("wheel_0").getPressure()).isBetween(minPressure, maxPressure);

    // and
    assertThat(vehiclesDao.count()).isEqualTo(1);
  }
  
  @Test
  public void addTruckTest() throws Exception {
    // given
    var truck = new VehicleBoundary(new VehicleTypeBoundary("Truck", null), "Hyundai", "00-000-00", 20, 50);
    
    // when
    var response = webClient.post()
            .bodyValue(truck)
            .retrieve()
            .bodyToMono(DetailedVehicleBoundary.class)
            .log()
            .block();
    
    // then
    assertThat(response).isNotNull();
    assertThat(response.vehicleType().getEnergySource()).isNull();
  }
  
  @Test
  public void addVehicleInvalidTypeTest() throws Exception {
    // given
    var vehicleBoundary = new VehicleBoundary(new VehicleTypeBoundary("Ship", "Electric"), "Hyundai", "00-000-00", 20, 50);
    
    // when - POSTing the invalid vehicle
    // then - an exception should be thrown
    assertThrows(WebClientResponseException.BadRequest.class, () -> webClient.post()
            .bodyValue(vehicleBoundary)
            .retrieve()
            .bodyToMono(DetailedVehicleBoundary.class)
            .log()
            .block());
  }
  
  @Test
  public void addVehicleWithExsistingLicenseNumberTest() throws Exception {
    // given
    var vehicleBoundary = new VehicleBoundary(new VehicleTypeBoundary("car", "Electric"), "Hyundai", "00-000-00", 20, 50);
    
    // and
    webClient.post()
            .bodyValue(vehicleBoundary)
            .retrieve()
            .bodyToMono(DetailedVehicleBoundary.class)
            .log()
            .block();
    // and
    var anotherVehicleBoundary = new VehicleBoundary(new VehicleTypeBoundary("Truck", null), "Man", "00-000-00", 15, 95);
    
    // when
    // then
    assertThrows(WebClientResponseException.Conflict.class, () -> webClient.post()
            .bodyValue(anotherVehicleBoundary)
            .retrieve()
            .bodyToMono(DetailedVehicleBoundary.class)
            .log()
            .block());
  }
}
