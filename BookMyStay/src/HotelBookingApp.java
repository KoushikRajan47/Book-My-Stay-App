import java.util.HashMap;
import java.util.Map;

public class HotelBookingApp {

    public static void main(String[] args) {

        RoomInventory inventory = new RoomInventory();

        inventory.displayInventory();

        inventory.updateAvailability("Single", 4);
        inventory.updateAvailability("Suite", 1);

        System.out.println("\nAfter Updates:\n");
        inventory.displayInventory();
    }
}

class RoomInventory {

    private Map<String, Integer> availability;

    public RoomInventory() {
        availability = new HashMap<>();
        availability.put("Single", 5);
        availability.put("Double", 3);
        availability.put("Suite", 2);
    }

    public int getAvailability(String roomType) {
        return availability.getOrDefault(roomType, 0);
    }

    public void updateAvailability(String roomType, int count) {
        availability.put(roomType, count);
    }

    public void displayInventory() {
        System.out.println("===== ROOM INVENTORY =====");
        for (Map.Entry<String, Integer> entry : availability.entrySet()) {
            System.out.println(entry.getKey() + " Rooms Available: " + entry.getValue());
        }
    }
}