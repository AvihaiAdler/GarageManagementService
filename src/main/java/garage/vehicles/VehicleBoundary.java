package garage.vehicles;

public record VehicleBoundary (VehicleType vehicleType,
        String modelName,
        String licenseNumber,
        Integer energyPercentage,
        Integer maxTirePressure) { 
}

  
