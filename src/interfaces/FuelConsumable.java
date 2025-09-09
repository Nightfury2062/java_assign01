package interfaces;

public interface FuelConsumable{
    void refuel(double amount);
    double getFuelLevel();
    double consumeFuel(double distance);
}
