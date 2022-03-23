package garage.vehicles.boundaries;

import java.util.Map;

import garage.vehicles.misc.Wheel;

public record DetailedVehicleBoundary(VehicleTypeBoundary vehicleType,
        Map<String, Wheel> wheels,
        String modelName,
        String licenseNumber,
        Integer energyPercentage,
        Integer maxTirePressure) { 
}
