### Garage Management Service

is a simple service simulating a garage management systems. The service implemented in Java over Spring Boot, and uses MongoDB as a database.
The service provide the following utilities:

- add a vehicle to the garage
- retrieve a vehicle
- retrieve all vehicles
- inflate the tires of a vehicle
- refuel a vehicle
- delete a vehicle
- delete all vehicles (for debug porpuses)

### Functionallity

| description                           | method | endpoint                                   | input              | output                              |
| ------------------------------------- | ------ | ------------------------------------------ | ------------------ | ----------------------------------- |
| add a vehicle to the garage           | POST   | `/api/v1/vehicles`                         | `Vehicle` boundary | `DetailedVehicle` boundary          |
| retrieve a vehicle from the garage    | GET    | `/api/v1/vehicles/{licenseNumber}`         | ------             | `DetailedVehicle` boundary          |
| retrieve all vehicles from the garage | GET    | `/api/v1/vehicles`                         | ------             | Array of `DetailedVehicle` boundary |
| inflate a tire of a vehicle           | PUT    | `/api/v1/vehicles/{licenseNumber}/inflate` | ------             | ------                              |
