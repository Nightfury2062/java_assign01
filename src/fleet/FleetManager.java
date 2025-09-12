package fleet;

import java.util.*;
import java.io.*;

import vehicles.Airplane;
import vehicles.Bus;
import vehicles.Car;
import vehicles.CargoShip;
import vehicles.Truck;
import vehicles.Vehicle;
import interfaces.FuelConsumable;
import interfaces.Maintainable;
import exceptions.InvalidOperationException;
import exceptions.InsufficientFuelException;
import exceptions.OverloadException;

public class FleetManager{
    private final List<Vehicle> fleet;

    public FleetManager(){
        this.fleet = new ArrayList<>();
    }

    public void addVehicle(Vehicle v) throws InvalidOperationException{
        Objects.requireNonNull(v, "Vehicle cannot be null");
        String id = v.getID();
        boolean exists = fleet.stream().anyMatch(x -> x.getID().equals(id));
        if (exists){
            throw new InvalidOperationException("Duplicate vehicle id: " + id);
        }
        fleet.add(v);
    }

    public void removeVehicle(String id) throws InvalidOperationException{
        Iterator<Vehicle> it=fleet.iterator();
        while (it.hasNext()){
            if (it.next().getID().equals(id)){
                it.remove();
                return;
            }
        }
        throw new InvalidOperationException("Vehicle not found: " + id);
    }

    public Map<String, String> startAllJourneys(double distance){
        Map<String, String> result=new LinkedHashMap<>();
        for (Vehicle v:fleet){
            try{
                v.move(distance);
                result.put(v.getID(), "Ok");
            } 
            catch(InvalidOperationException | InsufficientFuelException e){
                result.put(v.getID(), "failed: " + e.getMessage());
            } 
            catch (Exception e){
                result.put(v.getID(), "unexpected fail: " + e.getMessage());
            }
        }
        return result;
    }

    public double consumeFuelForAll(double distance) {
        double tot=0.0;
        for (Vehicle v:fleet){
            if (v instanceof FuelConsumable){
                FuelConsumable fuel_consum= (FuelConsumable) v;
                try{
                    double used = fuel_consum.consumeFuel(distance);
                    tot += used;
                } 
                catch (InsufficientFuelException e){
                    System.out.println("Vehicle " + v.getID() + " skipped: " + e.getMessage());
                }
            }
        }
        return tot;
    }

    public void maintainAll(){
        for (Vehicle v:fleet){
            if (v instanceof Maintainable){
                Maintainable m=(Maintainable)v;
                if (m.needsMaintenance()){
                    m.performMaintenance();
                }
            }
        }
    }

    public List<Vehicle> searchByType(Class<?> type){
        List<Vehicle> ans=new ArrayList<>();
        for (Vehicle v:fleet){
            if (type.isInstance(v)) ans.add(v);
        }
        return ans;
    }

    public void sortFleetByEfficiency(){
        Collections.sort(fleet);
    }

    public String generateReport(){
        StringBuilder str_bldr = new StringBuilder();
        str_bldr.append("Fleet report\n------------\n");
        str_bldr.append("Total vehicles: ").append(fleet.size()).append("\n");

        Map<String,Integer> byType=new TreeMap<>();
        double totalEff=0;
        int effCount=0;
        double totalMileage=0;
        int maintenanceCount=0;

        for (Vehicle v:fleet){
            byType.merge(v.getClass().getSimpleName(), 1, Integer::sum);
            double eff=v.calculateFuelEfficiency();
            if (eff>0) { totalEff += eff; effCount++; }
            totalMileage += v.getCurrentMileage();
            if (v instanceof Maintainable && ((Maintainable) v).needsMaintenance()) maintenanceCount++;
        }

        str_bldr.append("By type:\n");
        byType.forEach((k,c)-> str_bldr.append("  ").append(k).append(": ").append(c).append("\n"));

        str_bldr.append("Avg fuel efficiency: ");
        str_bldr.append(effCount>0 ? String.format("%.2f km/l", totalEff / effCount) : "N/A").append("\n");

        str_bldr.append("Total mileage: ").append(totalMileage).append(" km\n");
        str_bldr.append("Vehicles needing maintenance: ").append(maintenanceCount).append("\n");

        return str_bldr.toString();
    }

    public List<Vehicle> getVehiclesNeedingMaintenance(){
        List<Vehicle> output=new ArrayList<>();
        for (Vehicle v:fleet){
            if (v instanceof Maintainable && ((Maintainable) v).needsMaintenance()){
                output.add(v);
            }
        }
        return output;
    }

    public void saveToFile(String filename) throws IOException{
        try (PrintWriter pw = new PrintWriter(new FileWriter(filename))){
                for (Vehicle v:fleet){
                    if (v instanceof Car){
                        Car c=(Car) v;
                        pw.printf("Car,%s,%s,%.2f,%.2f,%d,%.2f,%d,%d%n",
                                c.getID(), c.getModel(), c.getMaxSpeed(), c.getCurrentMileage(),
                                c.getNumWheels(), c.getFuelLevel(), 
                                c.getPassengerCapacity(), c.getCurrentPassengers());
                    } 
                    else if (v instanceof Truck){
                        Truck t=(Truck)v;
                        pw.printf("Truck,%s,%s,%.2f,%.2f,%d,%.2f,%.2f,%.2f%n",
                                t.getID(), t.getModel(), t.getMaxSpeed(), t.getCurrentMileage(),
                                t.getNumWheels(), t.getFuelLevel(),
                                t.getCargoCapacity(), t.getCurrentCargo());
                    } 
                    else if (v instanceof Bus){
                        Bus b=(Bus)v;
                        pw.printf("Bus,%s,%s,%.2f,%.2f,%d,%.2f,%d,%d,%.2f,%.2f%n",
                                b.getID(), b.getModel(), b.getMaxSpeed(), b.getCurrentMileage(),
                                b.getNumWheels(), b.getFuelLevel(),
                                b.getPassengerCapacity(), b.getCurrentPassengers(),
                                b.getCargoCapacity(), b.getCurrentCargo());
                    }
                    else if (v instanceof Airplane){
                        Airplane a = (Airplane) v;
                        pw.printf("Airplane,%s,%s,%.2f,%.2f,%.2f,%.2f,%d,%d,%.2f,%.2f%n",
                                a.getID(), a.getModel(), a.getMaxSpeed(), a.getCurrentMileage(),
                                a.getMaxAltitude(), a.getFuelLevel(),
                                a.getPassengerCapacity(), a.getCurrentPassengers(),
                                a.getCargoCapacity(), a.getCurrentCargo());
                    } 
                    else if (v instanceof CargoShip){
                        CargoShip cs=(CargoShip) v;
                        pw.printf("CargoShip,%s,%s,%.2f,%.2f,%b,%.2f,%.2f,%.2f%n",
                                cs.getID(), cs.getModel(), cs.getMaxSpeed(), cs.getCurrentMileage(),
                                cs.hasSail(), cs.getFuelLevel(),
                                cs.getCargoCapacity(), cs.getCurrentCargo());
                    }
                }
            }
        }














}
