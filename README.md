### Garage Management Service

is a simple service simulating a garage management system. The service is written in Java with Spring Boot, and uses PosgreSQL as its database. The data model for this specific instance looks like ![this](https://cdn.discordapp.com/attachments/956515259307405332/956515301283999754/Relational.png)
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
| inflate the tires of a vehicle        | **PUT**    | `/api/v1/vehicles/{licenseNumber}/inflate` | `Pressure` boundary | ------                              |
| refuel/re-charge a vehicle            | **PUT**    | `/api/v1/vehicles/{licenseNumber}/refuel`  | `Fuel` boundary     | ------                              |
| delete a vehicle                      | **DELETE** | `/api/v1/vehicles/{licenseNumber}/delete`  | ------              | ------                              |
| delete all vehicles                   | **DELETE** | `/api/v1/vehicles/admin/delete`            | ------              | ------                              |

#### Paging and sorting

The endpoint **GET** `/api/v1/vehicles/{licenseNumber}` returns an Array of `DetailedVehicle` boundaries which represent a Page of 20 elements with default sort parameters.
The endpoint will accept optional variables to further customize the returned data.

- `filterType`: will invoke a query based on its value. Permitted values are (all values are case sensitive):
  - none (i.e. empty String)
  - byEnergyType
  - byModelName
  - byVehicleType
- `filterValue` will supply the value the above query will look for. For example, for `FilterType = byEnergyType` and `filterValue = electric` - the data returned will only contain vehicles with an electric source
- `size`: the number of elements in a page
- `page`: a collection of data with a size `size`
- `sortBy`: will return the data sorted based on the supplied argument. Permitted arguments are:
  - id
  - model
  - licenseNumber
  - energy (represent the available energy for a vehicle)
  - maxPressure
- `order`: the order of the sort. Can be either **ASC** or **DESC** (case sensitive)

To summarize if we were to access the endpoint above with the following values:

- `filterType = byModelName`
- `filterValue = honda civic`
- `size = 5`
- `page = 0`
- `sortBy = licenseNumber`
- `order = ASC`

We will get the first page with 5 elements at most, all the elements has the model name of **honda civic** sorted by their license number in ascending order.

#### Building and Running

The convenient way to Build and run the app is with docker-compose.

- make sure you have docker up and running
- navigate to the app root directory using a CLI of your choice
- run the command `docker-compose -f garage-compose.yaml up` (you can add a `-d` flag to have the app run in the background)
- if you ran the command with the `-d` flag - in order to shutdown the app run `docker-compose -f garage-compose.yaml down`

You can also run the app from your preferred IDE, just make sure you create 3 enbironment variables:

- `POSTGRESS_URI` with a URI to your postgress instance (look at `garage-compose.yaml` for a reference should you need any)
- `POSTRGESS_USER`
- `POSTGRESS_PASSWORD`
  Alternatively you can setup that variable as environment variable on your machine - build the app with gradle (`gradlew assemble`) and run the app through your CLI.
