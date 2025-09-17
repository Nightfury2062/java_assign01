# README.txt

## Overview

This project implements a **Transportation Fleet Management System** demonstrating OOP concepts: inheritance, polymorphism, abstract classes, interfaces, and custom exceptions. It includes a menu-driven CLI (`Main`) and a `FleetManager` for persistence and fleet operations.

## How the code demonstrates OOP concepts

* **Inheritance**: `Vehicle` is an abstract base; `LandVehicle`, `AirVehicle`, `WaterVehicle` are abstract subclasses; `Car`, `Truck`, `Bus`, `Airplane`, `CargoShip` are concrete subclasses.
* **Abstract classes & methods**: `Vehicle` declares abstract methods `move`, `calculateFuelEfficiency`, and `estimateJourneyTime`. Subclasses implement these.
* **Interfaces**: `FuelConsumable`, `CargoCarrier`, `PassengerCarrier`, `Maintainable` define contracts implemented by concrete classes (e.g., `Car implements FuelConsumable, PassengerCarrier, Maintainable`).
* **Polymorphism**: `FleetManager` stores `List<Vehicle>` and calls `move()` polymorphically. `searchByType` accepts classes/interfaces.
* **Custom exceptions**: `OverloadException`, `InvalidOperationException`, `InsufficientFuelException` are used to signal domain errors.

## Files to ensure present

* `src/vehicles/Vehicle.java` (and other vehicle classes)
* `src/interfaces/*.java` (FuelConsumable, CargoCarrier, PassengerCarrier, Maintainable)
* `src/exceptions/*.java` (OverloadException, InvalidOperationException, InsufficientFuelException)
* `src/fleet/FleetManager.java`
* `src/app/Main.java`
* `sample_fleet.csv` (provided in project root)

## Compile instructions

From project root (Unix/macOS):

```bash
mkdir -p bin
find src -name "*.java" > sources.txt
javac -d bin @sources.txt
rm sources.txt
```

PowerShell (Windows):

```powershell
$files = Get-ChildItem -Path src -Recurse -Filter *.java | ForEach-Object { $_.FullName }
javac -d bin $files
```

## Run

```bash
java -cp bin app.Main
```

This runs the demo (creates sample vehicles, simulates a journey) and then launches the interactive CLI.

## UML diagram in folder out

## Testing persistence with the sample CSV

1. Place `sample_fleet.csv` in the project root.
2. Run the program and choose menu option **8. Load Fleet** and enter `sample_fleet.csv`.
3. Verify the fleet by choosing **6. Generate Report** and **9. Search by Type**.
4. Add/remove vehicles as desired (menu options 1 and 2).
5. Save to a new file via **7. Save Fleet** (e.g., `fleet_after_edit.csv`).
6. Restart the program and load the saved file to confirm the round-trip.


## CLI usage (quick walkthrough)

1. Start program: `java -cp bin app.Main`
2. Demo runs: creates one of each vehicle, simulates a 100 km journey, prints report.
3. CLI menu appears. Typical flow:

   * **1 Add Vehicle**: choose type and supply properties
   * **3 Start Journey**: enter distance (e.g., 100)
   * **4 Refuel All**: add fuel to all fuel-using vehicles
   * **7 Save Fleet**: write current fleet to CSV
   * **8 Load Fleet**: load from CSV (replaces current fleet)
   * **11 Exit**: quit

## Expected demo output (example)

* Demo prints journey outputs like:

```
Driving on road for 100.0 km
Transporting passengers and cargo for 100.0 km...
Sailing with cargo for 100.0 km using sails
Flying at 10000.0 meters for 100.0 km...
```

* Followed by a report summarizing total vehicles, counts by type, average efficiencies, total mileage, and maintenance needs.


