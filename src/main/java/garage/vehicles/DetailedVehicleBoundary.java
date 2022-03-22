package garage.vehicles;

import java.util.Map;
import garage.vehicles.util.Wheel;

public record DetailedVehicleBoundary(VehicleType vehicleType,
        Map<String, Wheel> wheels,
        String modelName,
        String licenseNumber,
        Integer energyPercentage,
        Integer maxTirePressure) { 
}
