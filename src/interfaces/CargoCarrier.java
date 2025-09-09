package interfaces;

public interface CargoCarrier {
    void loadCargo(double weight);
    double unloadCargo(double weight);
    double getCargoCapacity();
    double getCurrentCargo();
}