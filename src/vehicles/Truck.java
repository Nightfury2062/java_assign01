package vehicles;

import interfaces.FuelConsumable;
import interfaces.PassengerCarrier;
import interfaces.Maintainable;

public class Truck extends LandVehicle implements FuelConsumable,PassengerCarrier,Maintainable{

    private double fuelLevel;
    private double cargoCapacity;
    private int currentCargo;
    private boolean maintenanceNeeded;
    private int passengerCapacity;
    private int currentPassengers;

    public Truck(String id,String model,double maxSpeed,double currentMileage,int numWheels){
        super(id, model, maxSpeed, currentMileage, numWheels);
        this.fuelLevel=0.0;
        this.cargoCapacity=5000;
        this.passengerCapacity = 2;
        //passengers, currentCargo will automatically be set to 0 maintainceNeeded will automatically be false

    }

     @Override
    public void move(double distance){
        if (distance<0){
            System.out.println("Invalid distance.");
            return;
        }
        double fuelNeeded = distance/calculateFuelEfficiency();
        if (fuelLevel >= fuelNeeded){
            fuelLevel -= fuelNeeded;
            setCurrentMileage(getCurrentMileage() + distance);
            System.out.println("Hauling cargo for " + distance + " km");
        }
        else{
            System.out.println("Not enough fuel to haul cargo for " + distance + " km");
        }
    }

    @Override
    public double calculateFuelEfficiency(){
        double base=8.0;
        if (currentCargo>cargoCapacity*0.5){
            base *= 0.9; // reduce efficiency by 10% when loaded more than 50%
        }
        return base;
    }

    @Override
    public void refuel(double amount){
        if (amount>0){
            fuelLevel += amount;
        }
        else{
            System.out.println("Not enough fuel");
        }
    }

    @Override
    public double getFuelLevel(){
        return fuelLevel;
    }

    @Override
    public double consumeFuel(double distance){
        double fuelNeeded=distance/calculateFuelEfficiency();
        if (fuelLevel >= fuelNeeded){
            fuelLevel -= fuelNeeded;
            return fuelNeeded;
        }

        return 0;
    }

    @Override
    public void boardPassengers(int count){
        if (currentPassengers+count <= passengerCapacity){
            currentPassengers += count;
        } 
        else{
            System.out.println("Passenger overflow: Limit exceeded");
        }
    }

    @Override
    public void disembarkPassengers(int count){
        if (count <= currentPassengers){
            currentPassengers -= count;
        } 
        else{
            System.out.println("Passenger underflow: Not enough passengers");
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
    public void scheduleMaintenance(){
        this.maintenanceNeeded = true;
    }

    @Override
    public boolean needsMaintenance(){
        return ((getCurrentMileage()>10000) || maintenanceNeeded);
    }

    @Override
    public void performMaintenance(){
        this.maintenanceNeeded = false;
        System.out.println("Truck maintenance has been done.");
    }

    

    
}