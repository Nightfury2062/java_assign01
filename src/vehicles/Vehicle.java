package vehicles;
import exceptions.InsufficientFuelException;
import exceptions.InvalidOperationException;

abstract class Vehicle {
    private String id;
    private String model;
    private double maxSpeed;
    private double currentMileage;

    public Vehicle(String id, String model, double maxSpeed,double currentMileage)throws InvalidOperationException{
        if(id == null || id.trim().isEmpty()){
            throw new InvalidOperationException("Vehicle ID cannot be empty");
             
        }
        this.id = id;
        this.model = model;
        this.maxSpeed = maxSpeed;
        this.currentMileage = currentMileage;
    }

    //Concrete methods

    void displayInfo(){
        System.out.printf(
            "Vehicle Info:\nID: %s\nModel: %s\nMax Speed: %.2f km/h\nCurrent Mileage: %.2f km\n",
            id, model, maxSpeed, currentMileage
        );
    }
    double getCurrentMileage(){
        return currentMileage;
    }
    String getID(){
        return id;
    }
    String getmModel(){
        return model;
    }
    double getMaxSpeed(){
        return maxSpeed;
    }
    public void setCurrentMileage(double mileage) {
    this.currentMileage = mileage;
}


    //Abstract methods

    abstract void move(double distance)throws InvalidOperationException, InsufficientFuelException;;
    abstract double calculateFuelEfficiency();
    abstract double estimateJourneyTime(double distance);




    
}
