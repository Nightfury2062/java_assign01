package app;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import fleet.FleetManager;
import vehicles.*;
import interfaces.FuelConsumable;
import interfaces.Maintainable;
import exceptions.*;

public class Main {

    public static void main(String[] args) {
        FleetManager fleet_m = new FleetManager();
        // Demo
        createDemo(fleet_m);

        // Launch CLI
        try (Scanner sc = new Scanner(System.in)){
            runCliLoop(sc, fleet_m);
        }
    }

    private static void createDemo(FleetManager fm){
        System.out.println("Creating demo vehicles");
        try {
            Car car1 = new Car("CAR001", "Maruti", 110.0, 0.0, 4);
            Truck truck1 = new Truck("TRK001", "Tesla", 90.0, 0.0, 6);
            Bus bus1 = new Bus("BUS001", "Tata", 80.0, 0.0, 6);
            Airplane ap1 = new Airplane("AIR001", "Indigo", 900.0, 0.0, 10000.0);
            CargoShip cs1 = new CargoShip("SHP001", "SCI", 40.0, 0.0, true);

            fm.addVehicle(car1);
            fm.addVehicle(truck1);
            fm.addVehicle(bus1);
            fm.addVehicle(ap1);
            fm.addVehicle(cs1);
        } 
        catch (InvalidOperationException e){
            System.out.println("Demo setup error: " + e.getMessage());
        }

        // Simulate a 100 km journey and print report
        Map<String, String> outcomes = fm.startAllJourneys(100.0);
        System.out.println("Demo journeys:");
        outcomes.forEach((id, status) -> System.out.println(id + " -> " + status));

        System.out.println("\nDemo report:\n" + fm.generateReport());

        //UNCOMMENT FOR 1ST RUN-: (TO GENERATE SAMPLE CSV)

        // try { fm.saveToFile("sample_fleet.csv"); } catch (IOException e) { System.out.println("Failed to save demo CSV: " + e.getMessage()); }
    }

    private static void runCliLoop(Scanner sc,FleetManager fm){
        while (true){
            printMenu();
            int choose= readInt(sc, "Choose an option: ", 1, 11);
            try{
                switch(choose){
                    case 1: handleAddVehicle(sc,fm);break;
                    case 2: handleRemoveVehicle(sc,fm);break;
                    case 3: handleStartJourney(sc, fm);break;
                    case 4: handleRefuelAll(sc,fm);break;
                    case 5: handlePerformMaintenance(fm);break;
                    case 6: System.out.println(fm.generateReport());break;
                    case 7: handleSaveFleet(sc,fm);break;
                    case 8: handleLoadFleet(sc,fm);break;
                    case 9: handleSearchByType(sc,fm);break;
                    case 10: handleListMaintenance(fm);break;
                    case 11: System.out.println("Exiting"); return;
                    default: System.out.println("Invalid choice.");
                }
            } 
            catch (Exception e){
                System.out.println("Error: " + e.getMessage());
            }
            System.out.println();
        }
    }

    private static void printMenu() {
        System.out.println("   Fleet Manager Menu   ");
        System.out.println("1. Add Vehicle");
        System.out.println("2. Remove Vehicle");
        System.out.println("3. Start Journey");
        System.out.println("4. Refuel All");
        System.out.println("5. Perform Maintenance");
        System.out.println("6. Generate Report");
        System.out.println("7. Save Fleet");
        System.out.println("8. Load Fleet");
        System.out.println("9. Search by Type");
        System.out.println("10. List Vehicles Needing Maintenance");
        System.out.println("11. Exit");
    }
     //handlers
    private static void handleAddVehicle(Scanner sc, FleetManager fm){
        System.out.println("Select vehicle type: 1=Car 2=Truck 3=Bus 4=Airplane 5=CargoShip");
        int t = readInt(sc, "Type: ", 1, 5);
        String id = readNonEmptyString(sc, "Enter ID: ");
        String model = readNonEmptyString(sc, "Enter model: ");
        double maxSpeed = readDouble(sc, "Enter max speed (km/h): ", 0, Double.MAX_VALUE);
        double currentMileage = readDouble(sc, "Enter current mileage (km): ", 0, Double.MAX_VALUE);

        try{
            switch(t){
                case 1:{ 
                    int wheels=readInt(sc, "Enter number of wheels: ", 1, 20);
                    Car c=new Car(id, model, maxSpeed, currentMileage, wheels);
                    fm.addVehicle(c);
                    System.out.println("Car added.");
                    break;
                }
                case 2:{
                    int wheels=readInt(sc, "Enter number of wheels: ", 1, 20);
                    Truck trk=new Truck(id, model, maxSpeed, currentMileage, wheels);
                    fm.addVehicle(trk);
                    System.out.println("Truck added.");
                    break;
                }
                case 3:{
                    int wheels=readInt(sc, "Enter number of wheels: ", 2, 20);
                    Bus b=new Bus(id, model, maxSpeed, currentMileage, wheels);
                    fm.addVehicle(b);
                    System.out.println("Bus added.");
                    break;
                }
                case 4:{
                    double maxAlt = readDouble(sc, "Enter max altitude (meters): ", 0, Double.MAX_VALUE);
                    Airplane a=new Airplane(id, model, maxSpeed, currentMileage, maxAlt);
                    fm.addVehicle(a);
                    System.out.println("Airplane added.");
                    break;
                }
                case 5:{
                    boolean hasSail = readBooleanYesNo(sc, "Does the ship have sails? (y/n): ");
                    CargoShip cs = new CargoShip(id, model, maxSpeed, currentMileage, hasSail);
                    fm.addVehicle(cs);
                    System.out.println("CargoShip added.");
                    break;
                }
            }
        } 
        catch (InvalidOperationException e){
            System.out.println("Failed to add vehicle: " + e.getMessage());
        }
    }

    private static void handleRemoveVehicle(Scanner sc, FleetManager fm){
        String id = readNonEmptyString(sc, "Enter vehicle ID to remove: ");
        try{
            fm.removeVehicle(id);
            System.out.println("Vehicle removed.");
        }
        catch (InvalidOperationException e) {
            System.out.println("Remove failed " + e.getMessage());
        }
    }

    private static void handleStartJourney(Scanner sc, FleetManager fm){
        double d = readDouble(sc, "Enter distance in km : ", 0, Double.MAX_VALUE);
        Map<String, String> res = fm.startAllJourneys(d);
        System.out.println("Journey results:");
        res.forEach((id, status) -> System.out.println(id + " -> " + status));
    }

    private static void handleRefuelAll(Scanner sc, FleetManager fm){
        double amt = readDouble(sc, "Enter amount of fuel to add to each fuel vehicle (liters): ", 0.0001, Double.MAX_VALUE);
        for (Vehicle v:fm.getFleetSnapshot()){
            if (v instanceof FuelConsumable){
                FuelConsumable fc=(FuelConsumable)v;
                try{
                    fc.refuel(amt);
                    System.out.println(v.getID() + ": refueled by " + amt);
                } 
                catch (InvalidOperationException e){
                    System.out.println(v.getID() + ": refuel failed " + e.getMessage());
                }
            }
        }
    }

    private static void handlePerformMaintenance(FleetManager fm){
        fm.maintainAll();
        System.out.println("Maintenance check performed for all vehicles :) .");
    }

    private static void handleSaveFleet(Scanner sc, FleetManager fm){
        String file_name = readNonEmptyString(sc, "Enter filename to save (e.g., fleet.csv): ");
        try{
            fm.saveToFile(file_name);
            System.out.println("Fleet saved to " + file_name);
        }
        catch (IOException e) {
            System.out.println("Save failed: " + e.getMessage());
        }
    }

    private static void handleLoadFleet(Scanner sc, FleetManager fm){
        String file_name = readNonEmptyString(sc, "Enter filename to load (e.g., fleet.csv): ");
        System.out.println("This will replace the current fleet. Continue? (y/n)");
        if (!readBooleanYesNo(sc, "> ")){
            System.out.println("Load cancelled.");
            return;
        }
        try{
            fm.loadFromFile(file_name);
            System.out.println("Fleet loaded from " + file_name);
        } 
        catch (IOException e){
            System.out.println("Load failed: " + e.getMessage());
        }
    }

    private static void handleSearchByType(Scanner sc, FleetManager fm){
        System.out.println("Search type: 1=Car 2=Truck 3=Bus 4=Airplane 5=CargoShip 6=FuelConsumable 7=Maintainable");
        int opt = readInt(sc, "Choose: ", 1, 7);
        Class<?> cls=null;
        switch (opt){
            case 1: cls=Car.class;break;
            case 2: cls=Truck.class;break;
            case 3: cls=Bus.class;break;
            case 4: cls=Airplane.class;break;
            case 5: cls=CargoShip.class;break;
            case 6: cls=FuelConsumable.class;break;
            case 7: cls=Maintainable.class;break;
        }

        List<Vehicle> found = fm.searchByType(cls);
        if (found.isEmpty()) System.out.println("No vehicles found of that type.");
        else{
            System.out.println("Found vehicles:");
            for (Vehicle v:found) v.displayInfo();
        }
    }


    //   EDIT FROM HERE

    
    private static void handleListMaintenance(FleetManager fm){
        List<Vehicle> needing = fm.getVehiclesNeedingMaintenance();
        if (needing.isEmpty()){
            System.out.println("No vehicles need maintenance.");
        } 
        else{
            System.out.println("Vehicles needing maintenance:");
            needing.forEach(v -> v.displayInfo());
        }
    }


    private static String readNonEmptyString(Scanner sc, String prompt) {
        String s;
        do {
            System.out.print(prompt);
            s = sc.nextLine().trim();
        } while (s.isEmpty());
        return s;
    }

    private static int readInt(Scanner sc, String prompt, int min, int max) {
        while (true) {
            System.out.print(prompt);
            String line = sc.nextLine().trim();
            try {
                int val = Integer.parseInt(line);
                if (val < min || val > max) {
                    System.out.println("Please enter an integer between " + min + " and " + max + ".");
                } else return val;
            } catch (NumberFormatException e) {
                System.out.println("Invalid integer. Try again.");
            }
        }
    }

    private static double readDouble(Scanner sc, String prompt, double min, double max) {
        while (true) {
            System.out.print(prompt);
            String line = sc.nextLine().trim();
            try {
                double val = Double.parseDouble(line);
                if (val < min || val > max) {
                    System.out.println("Please enter a number between " + min + " and " + max + ".");
                } else return val;
            } catch (NumberFormatException e) {
                System.out.println("Invalid number. Try again.");
            }
        }
    }

    private static boolean readBooleanYesNo(Scanner sc, String prompt) {
        while (true) {
            System.out.print(prompt);
            String s = sc.nextLine().trim().toLowerCase();
            if (s.equals("y") || s.equals("yes")) return true;
            if (s.equals("n") || s.equals("no")) return false;
            System.out.println("Please enter y (yes) or n (no).");
        }
    }
}
