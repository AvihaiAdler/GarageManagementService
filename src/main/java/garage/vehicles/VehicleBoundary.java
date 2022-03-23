package garage.vehicles;

public record VehicleBoundary (VehicleTypeBoundary vehicleType,
        String modelName,
        String licenseNumber,
        Integer energyPercentage,
        Integer maxTirePressure) { 
}

  
