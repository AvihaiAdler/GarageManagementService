package garage.vehicles;

import java.util.List;

import garage.vehicles.util.Wheel;

public record DetailedVehicleBoundary(String vehicleType,
        List<Wheel> wheels,
        String modelName,
        String licenseNumber,
        Integer availableEnergyPercentage,
        Integer maxTirePressure) { 
  
}
