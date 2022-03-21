package garage.vehicles;

import java.util.Map;

public record DetailedVehicleBoundary(VehicleType vehicleType,
        Map<String, Integer> wheels,
        String modelName,
        String licenseNumber,
        Integer energyPercentage,
        Integer maxTirePressure) { 
}
