import java.io.*;
import java.util.*;

public class HotelBookingApp {

    public static void main(String[] args) {

        PersistenceService persistence = new PersistenceService();

        SystemState state = persistence.loadState();

        if (state == null) {
            state = new SystemState();
            state.inventory = new RoomInventory();
            state.history = new BookingHistory();

            state.inventory.setAvailability("Single", 2);
            state.inventory.setAvailability("Double", 1);

            state.history.addReservation(new Reservation("R1", "Koushik", "Single", "S1"));
        }

        System.out.println("=== Current State ===");
        state.inventory.display();
        state.history.display();

        persistence.saveState(state);

        System.out.println("\nState saved successfully.");
    }
}

class SystemState implements Serializable {
    RoomInventory inventory;
    BookingHistory history;
}

class Reservation implements Serializable {

    private String id;
    private String guest;
    private String type;
    private String roomId;

    public Reservation(String id, String guest, String type, String roomId) {
        this.id = id;
        this.guest = guest;
        this.type = type;
        this.roomId = roomId;
    }

    public String getId() {
        return id;
    }

    public String getGuest() {
        return guest;
    }

    public String getType() {
        return type;
    }

    public String getRoomId() {
        return roomId;
    }
}

class BookingHistory implements Serializable {

    private List<Reservation> history = new ArrayList<>();

    public void addReservation(Reservation r) {
        history.add(r);
    }

    public void display() {
        System.out.println("\nBookings:");
        for (Reservation r : history) {
            System.out.println(r.getId() + " - " + r.getGuest() + " - " + r.getType());
        }
    }
}

class RoomInventory implements Serializable {

    private Map<String, Integer> availability = new HashMap<>();

    public void setAvailability(String type, int count) {
        availability.put(type, count);
    }

    public void display() {
        System.out.println("Inventory:");
        for (Map.Entry<String, Integer> e : availability.entrySet()) {
            System.out.println(e.getKey() + ": " + e.getValue());
        }
    }
}

class PersistenceService {

    private static final String FILE_NAME = "hotel_state.dat";

    public void saveState(SystemState state) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(state);
        } catch (IOException e) {
            System.out.println("Error saving state.");
        }
    }

    public SystemState loadState() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            return (SystemState) ois.readObject();
        } catch (Exception e) {
            System.out.println("No previous state found. Starting fresh.");
            return null;
        }
    }
}