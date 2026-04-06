import java.util.*;

public class HotelBookingApp {

    public static void main(String[] args) {

        AddOnServiceManager manager = new AddOnServiceManager();

        List<Service> services1 = new ArrayList<>();
        services1.add(new Service("Breakfast", 500));
        services1.add(new Service("Spa", 1500));

        List<Service> services2 = new ArrayList<>();
        services2.add(new Service("Airport Pickup", 800));

        manager.addServices("R1", services1);
        manager.addServices("R2", services2);

        manager.displayServices("R1");
        manager.displayServices("R2");
    }
}

class Service {

    private String name;
    private double cost;

    public Service(String name, double cost) {
        this.name = name;
        this.cost = cost;
    }

    public String getName() {
        return name;
    }

    public double getCost() {
        return cost;
    }
}

class AddOnServiceManager {

    private Map<String, List<Service>> reservationServices;

    public AddOnServiceManager() {
        reservationServices = new HashMap<>();
    }

    public void addServices(String reservationId, List<Service> services) {
        reservationServices.putIfAbsent(reservationId, new ArrayList<>());
        reservationServices.get(reservationId).addAll(services);
    }

    public double calculateTotalCost(String reservationId) {
        double total = 0;
        List<Service> services = reservationServices.get(reservationId);

        if (services != null) {
            for (Service s : services) {
                total += s.getCost();
            }
        }
        return total;
    }

    public void displayServices(String reservationId) {

        System.out.println("Reservation ID: " + reservationId);

        List<Service> services = reservationServices.get(reservationId);

        if (services == null || services.isEmpty()) {
            System.out.println("No add-on services selected.\n");
            return;
        }

        for (Service s : services) {
            System.out.println("Service: " + s.getName() + " | Cost: ₹" + s.getCost());
        }

        System.out.println("Total Add-On Cost: ₹" + calculateTotalCost(reservationId) + "\n");
    }
}