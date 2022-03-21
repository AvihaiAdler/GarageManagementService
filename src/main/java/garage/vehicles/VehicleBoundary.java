package garage.vehicles;

import java.util.List;

import garage.vehicles.util.EnergySource;
import garage.vehicles.util.VehicleType;
import garage.vehicles.util.Wheel;

public record VehicleBoundary (VehicleType vehicleType,
        EnergySource energySource,
        List<Wheel> wheels,
        String modelName,
        String licenseNumber,
        Integer availableEnergyPercentage,
        Integer maxTirePressure) { }

  
