import java.util.HashMap;
import java.util.Map;

public class HotelBookingApp {

    public static void main(String[] args) {

        RoomInventory inventory = new RoomInventory();

        Map<String, Room> rooms = new HashMap<>();
        rooms.put("Single", new SingleRoom());
        rooms.put("Double", new DoubleRoom());
        rooms.put("Suite", new SuiteRoom());

        SearchService searchService = new SearchService(inventory, rooms);

        searchService.searchAvailableRooms();
    }
}

class SearchService {

    private RoomInventory inventory;
    private Map<String, Room> rooms;

    public SearchService(RoomInventory inventory, Map<String, Room> rooms) {
        this.inventory = inventory;
        this.rooms = rooms;
    }

    public void searchAvailableRooms() {

        System.out.println("===== AVAILABLE ROOMS =====");

        for (String type : rooms.keySet()) {
            int available = inventory.getAvailability(type);

            if (available > 0) {
                Room room = rooms.get(type);
                room.displayDetails();
                System.out.println("Available: " + available + "\n");
            }
        }
    }
}

class RoomInventory {

    private Map<String, Integer> availability;

    public RoomInventory() {
        availability = new HashMap<>();
        availability.put("Single", 5);
        availability.put("Double", 0);
        availability.put("Suite", 2);
    }

    public int getAvailability(String roomType) {
        return availability.getOrDefault(roomType, 0);
    }
}

abstract class Room {

    protected int beds;
    protected double price;

    public Room(int beds, double price) {
        this.beds = beds;
        this.price = price;
    }

    public abstract void displayDetails();
}

class SingleRoom extends Room {

    public SingleRoom() {
        super(1, 2000);
    }

    public void displayDetails() {
        System.out.println("Room Type: Single Room");
        System.out.println("Beds: " + beds);
        System.out.println("Price: ₹" + price);
    }
}

class DoubleRoom extends Room {

    public DoubleRoom() {
        super(2, 3500);
    }

    public void displayDetails() {
        System.out.println("Room Type: Double Room");
        System.out.println("Beds: " + beds);
        System.out.println("Price: ₹" + price);
    }
}

class SuiteRoom extends Room {

    public SuiteRoom() {
        super(3, 7000);
    }

    public void displayDetails() {
        System.out.println("Room Type: Suite Room");
        System.out.println("Beds: " + beds);
        System.out.println("Price: ₹" + price);
    }
}