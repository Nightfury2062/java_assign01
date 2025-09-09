package vehicles;

abstract class LandVehicle extends Vehicle{
    private int numWheels;

    public LandVehicle(String id, String model, double maxSpeed, double currentMileage, int numWheels){
        super(id,model,maxSpeed,currentMileage);
        this.numWheels = numWheels;

    }

    @Override
    double estimateJourneyTime(double distance){

        if(distance<0){
            //InvalidOperationException
            }

        double base_time = distance / getMaxSpeed();
        return base_time*1.1;
        
    }
}
