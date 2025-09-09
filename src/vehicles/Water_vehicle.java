package vehicles;

public abstract class Water_vehicle extends Vehicle{
    private boolean hasSail;

    public Water_vehicle(String id, String model, double maxSpeed, double currentMileage, boolean hasSail){

        super(id, model, maxSpeed, currentMileage);
        this.hasSail = hasSail;
    }

    public boolean hasSail() {
        return hasSail;
    }

    @Override
    public double estimateJourneyTime(double distance) {
        double baseTime = distance/getMaxSpeed();
        return baseTime * 1.15; 
    }
}
