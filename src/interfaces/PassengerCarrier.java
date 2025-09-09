package interfaces;

public interface PassengerCarrier{
    void boardPassengers(int count);
    void disembarkPassengers(int count);
    int getPassengerCapacity();
    int getCurrentPassengers();
}
