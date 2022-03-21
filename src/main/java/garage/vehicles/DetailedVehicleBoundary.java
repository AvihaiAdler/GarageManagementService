package garage.vehicles;

import java.util.Map;

public record DetailedVehicleBoundary(String vehicleType,
        Map<String, Integer> wheels,
        String modelName,
        String licenseNumber,
        Integer availableEnergyPercentage,
        Integer maxTirePressure) { 
  
}
