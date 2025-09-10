package interfaces;

public interface CargoCarrier {
    void loadCargo(double weight);
    void unloadCargo(double weight);
    double getCargoCapacity();
    double getCurrentCargo();
}