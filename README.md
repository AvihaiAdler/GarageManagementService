### Garage Management Service

is a simple service simulating a garage management systems. The service implemented in Java over Spring Boot, and uses MongoDB as a database.
The service provide the following utilities:

- add a vehicle to the garage
- retrieve a vehicle
- retrieve all vehicles
- inflate the tires of a vehicle
- refuel a vehicle
- delete a vehicle
- delete all vehicles (for debug purposes)

### Functionality

| description                           | method     | endpoint                                   | input               | output                              |
| ------------------------------------- | ---------- | ------------------------------------------ | ------------------- | ----------------------------------- |
| add a vehicle to the garage           | **POST**   | `/api/v1/vehicles`                         | `Vehicle` boundary  | `DetailedVehicle` boundary          |
| retrieve a vehicle from the garage    | **GET**    | `/api/v1/vehicles/{licenseNumber}`         | ------              | `DetailedVehicle` boundary          |
| retrieve all vehicles from the garage | **GET**    | `/api/v1/vehicles`                         | ------              | Array of `DetailedVehicle` boundary |
| inflate a tire of a vehicle           | **PUT**    | `/api/v1/vehicles/{licenseNumber}/inflate` | `Pressure` boundary | ------                              |
| refuel/rechage a vehicle              | **PUT**    | `/api/v1/vehicles/{licenseNumber}/refuel`  | `Fuel` boundary     | ------                              |
| delete a vehicle                      | **DELETE** | `/api/v1/vehicles/{licenseNumber}/delete`  | ------              | ------                              |
| delete all vehicles                   | **DELETE** | `/api/v1/vehicles/admin/delete`            | ------              | ------                              |

#### Paging and sorting

The endpoint **GET** `/api/v1/vehicles/{licenseNumber}` returns an Array of `DetailedVehicle` boundary which represent 1 Page of 20 size with default sort parameters.
The endpoint can accepts optional variables to customized the returned data.

- `filterType`: will invoke a query based on it's value. Permitted values are (all values are case sensitive):
  - none
  - byEnergyType
  - byModelName
  - byVehicleType
- `filterValue` will supply the value the above query will look for. For example, for `FilterType = byEnergyType` and `filterValue = electric` - the data returned will only contain vehicle with an electric source
- `size`: the number of elements in a page
- `page`: a collection of data with a size `size`
- `sortBy`: will return the data sorted based on the supplied argument. Permitted arguments are:
  - id
  - model
  - licenseNumber
  - energy (represent the available energy for a vehicle)
  - maxPressure
- `order`: the order of the sort. Can be either **ASC** or **DESC** (this argument is case sensitive too)

To summarize if we were to access the endpoint above with the following values:

- `filterType = byModelName`
- `filterValue = honda civic`
- `size = 5`
- `page = 0`
- `sortBy = licenseNumber`
- `order = ASC`

We will get the first page with maximum 5 elements in it all of the model **honda civic** sorted by their license number in ascending order.

#### Building and Running

The convenient way to Build and run the app is with docker-compose.

- make sure you have docker up and running
- navigate to the app root directory using a CLI of your choice
- run the command `docker-compose -f garage-compose.yaml up` (you can add a `-d` flag to have the app run at the background)
- if you ran the command with the `-f` flag - in order to shutdown the app run `docker-compose -f garage-compose.yaml down` 

You can also run the app from your preferred IDE, just make sure you create an environment variable called `MONGO_CRED` with the URL to your mongodb instance.
Alternatively you can setup that variable as environment variable on your machine - build the app with gradle (`gradlew assemble`) and run the app through your CLI.
