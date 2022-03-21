package garage.vehicles;

public record VehicleBoundary (String vehicleType,
        String modelName,
        String licenseNumber,
        Integer availableEnergyPercentage,
        Integer maxTirePressure) { 
  
}

  
