package vehicles;

abstract class Vehicle {
    private String id;
    private String model;
    private double maxSpeed;
    private double currentMileage;

    public Vehicle(String id, String model, double maxSpeed, double currentMileage){
        if(id == null || id.trim().length()>0){
            //invalidOperationException 
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

    //Abstract methods

    abstract void move(double distance);
    abstract double calculateFuelEfficiency();
    abstract double estimateJourneyTime(double distance);
    



    
}
