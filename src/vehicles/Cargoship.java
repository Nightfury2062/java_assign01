package vehicles;

import interfaces.CargoCarrier;
import interfaces.Maintainable;
import interfaces.FuelConsumable;

public class CargoShip extends WaterVehicle implements CargoCarrier, Maintainable, FuelConsumable{

    private final double cargoCapacity;
    private double currentCargo;
    private boolean maintenanceNeeded;
    private double fuelLevel;

    public CargoShip(String id, String model, double maxSpeed, double currentMileage, boolean hasSail){
        super(id, model, maxSpeed, currentMileage, hasSail);
        this.cargoCapacity = 50000.0;
        this.currentCargo = 0.0;
        this.maintenanceNeeded = false;
        this.fuelLevel = 0.0;
    }

    @Override
    public void move(double distance){
        if (distance < 0){
            System.out.println("Invalid distance.");
            return;
        }
        double efficiency = calculateFuelEfficiency();
        if (efficiency == 0){
            System.out.println("Sailing with cargo for " + distance + " km with sail");
            setCurrentMileage(getCurrentMileage() + distance);
        } else {
            double fuelNeeded = distance/efficiency;
            if (fuelLevel >= fuelNeeded){
                fuelLevel -= fuelNeeded;
                setCurrentMileage(getCurrentMileage() + distance);
                System.out.println("Sailing with cargo for " + distance + " km using fuel");
            } else{
                System.out.println("Not enough fuel to sail " + distance + " km");
            }
        }
    }

    @Override
    public double calculateFuelEfficiency(){
        if (hasSail()){
            return 0.0;
        }
        return 4.0;
    }

    @Override
    public void loadCargo(double weight){
        if (weight > 0 && currentCargo + weight <= cargoCapacity) {
            currentCargo += weight;
        } 
        else{
            System.out.println("Cargo overload or invalid weight");
        }
    }

    @Override
    public void unloadCargo(double weight){
        if ((weight>0) && weight <= currentCargo){
            currentCargo -= weight;
        } 
        else{
            System.out.println("Cannot unload cargo: invalid weight or more than present.");
        }
    }

    @Override
    public double getCargoCapacity(){
        return cargoCapacity;
    }

    @Override
    public double getCurrentCargo(){
        return currentCargo;
    }

    @Override
    public void scheduleMaintenance(){
        maintenanceNeeded = true;
    }

    @Override
    public boolean needsMaintenance(){
        return ((getCurrentMileage()>10000) || maintenanceNeeded);
    }

    @Override
    public void performMaintenance(){
        maintenanceNeeded = false;
        System.out.println("Cargo ship maintenance done.");
    }

    @Override
    public void refuel(double amount){
        if (!hasSail() && amount>0){
            fuelLevel += amount;
        } 
        else if (hasSail()){
            System.out.println("This ship doesn't use fuel.");
        }
    }

    @Override
    public double getFuelLevel(){
        if (hasSail()) return 0.0;
        else return fuelLevel;
    }

    
    @Override
    public double consumeFuel(double distance){
        if (hasSail()){
            return 0.0;
        }
        double needed = distance/calculateFuelEfficiency();
        if (fuelLevel >= needed){
            fuelLevel -= needed;
            return needed;
        }
        return 0;
    }
}
