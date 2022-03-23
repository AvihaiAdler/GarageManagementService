package garage.vehicles.boundaries;

public record VehicleBoundary (VehicleTypeBoundary vehicleType,
        String modelName,
        String licenseNumber,
        Integer energyPercentage,
        Integer maxTirePressure) { 
}

  
