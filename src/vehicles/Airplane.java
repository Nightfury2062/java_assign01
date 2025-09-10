package vehicles;

import interfaces.FuelConsumable;
import interfaces.PassengerCarrier;
import interfaces.CargoCarrier;
import interfaces.Maintainable;

public class Airplane extends AirVehicle implements FuelConsumable, PassengerCarrier, CargoCarrier, Maintainable{

    private double fuelLevel;
    private final int passengerCapacity;
    private int currentPassengers;
    private final double cargoCapacity;
    private double currentCargo;
    private boolean maintenanceNeeded;

    public Airplane(String id, String model, double maxSpeed, double currentMileage, double maxAltitude){
        super(id, model,maxSpeed,currentMileage,maxAltitude);
        this.fuelLevel = 0.0;
        this.passengerCapacity = 200;
        this.cargoCapacity = 10000.0;
    }

    @Override
    public void move(double distance){
        if (distance< 0){
            System.out.println("Invalid distance.");
            return;
        }
        double fuelNeeded = distance/calculateFuelEfficiency();
        if (fuelLevel >= fuelNeeded){
            fuelLevel -= fuelNeeded;
            setCurrentMileage(getCurrentMileage() + distance);
            System.out.println("Flying at " + getMaxAltitude() + " meters for " + distance + " km");
        } 
        else{
            System.out.println("Not enough fuel to fly " + distance + " km");
        }
    }

    @Override
    public double calculateFuelEfficiency(){
        return 5.0;
    }

    @Override
    public void refuel(double amount){
        if (amount>0){
            fuelLevel += amount;
        }
    }

    @Override
    public double getFuelLevel(){
        return fuelLevel;
    }

    @Override
    public double consumeFuel(double distance){
        double needed = distance/calculateFuelEfficiency();
        if (fuelLevel >= needed){
            fuelLevel -= needed;
            return needed;
        }
        return 0;
    }

    @Override
    public void boardPassengers(int count){
        if (currentPassengers + count <= passengerCapacity) {
            currentPassengers += count;
        } 
        else{
            System.out.println("Passenger Overflow: Cannot board passengers");
        }
    }

    @Override
    public void disembarkPassengers(int count){
        if (count <= currentPassengers) {
            currentPassengers -= count;
        } 
        else{
            System.out.println("Cannot disembark more passengers than present.");
        }
    }

    @Override
    public int getPassengerCapacity(){
        return passengerCapacity;
    }

    @Override
    public int getCurrentPassengers(){
        return currentPassengers;
    }

    @Override
    public void loadCargo(double weight){
        if (weight > 0 && currentCargo + weight <= cargoCapacity){
            currentCargo += weight;
        } 
        else{
            System.out.println("Cannot load cargo: capacity exceeded or invalid weight.");
        }
    }

    @Override
    public void unloadCargo(double weight){
        if (weight > 0 && weight <= currentCargo){
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
        System.out.println("Airplane maintenance done.");
    }
}
