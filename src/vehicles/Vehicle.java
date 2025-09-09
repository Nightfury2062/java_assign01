package vehicles;

abstract class Vehicle {
    private String id;
    private String model;
    private double maxSpeed;
    private double currentMileage;

    public Vehicle(String id, String model, double maxSpeed,double currentMileage){
        if(id == null || id.trim().isEmpty()){
            // throw new InvalidOperationException("Vehicle ID cannot be empty");
             
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
    double getMaxSpeed(){
        return maxSpeed;
    }
    public void setCurrentMileage(double mileage) {
    this.currentMileage = mileage;
}


    //Abstract methods

    abstract void move(double distance);
    abstract double calculateFuelEfficiency();
    abstract double estimateJourneyTime(double distance);




    
}
