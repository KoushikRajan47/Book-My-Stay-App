import java.util.HashMap;
import java.util.Map;

public class HotelBookingApp {

    public static void main(String[] args) {

        RoomInventory inventory = new RoomInventory();
        BookingService service = new BookingService(inventory);

        try {
            service.bookRoom("R1", "Koushik", "Single");
            service.bookRoom("R2", "Arun", "Suite");
            service.bookRoom("R3", "Rahul", "InvalidType");
            service.bookRoom("R4", "Vijay", "Suite");
        } catch (InvalidBookingException e) {
            System.out.println("Error: " + e.getMessage());
        }

        System.out.println("\nSystem continues running safely...");
    }
}

class BookingService {

    private RoomInventory inventory;

    public BookingService(RoomInventory inventory) {
        this.inventory = inventory;
    }

    public void bookRoom(String id, String guest, String type) throws InvalidBookingException {

        validate(type);

        if (inventory.getAvailability(type) <= 0) {
            throw new InvalidBookingException("No rooms available for type: " + type);
        }

        inventory.decrement(type);

        System.out.println("Booking Confirmed:");
        System.out.println("Guest: " + guest);
        System.out.println("Room Type: " + type);
        System.out.println("Reservation ID: " + id + "\n");
    }

    private void validate(String type) throws InvalidBookingException {
        if (!inventory.exists(type)) {
            throw new InvalidBookingException("Invalid room type: " + type);
        }
    }
}

class RoomInventory {

    private Map<String, Integer> availability;

    public RoomInventory() {
        availability = new HashMap<>();
        availability.put("Single", 1);
        availability.put("Double", 1);
        availability.put("Suite", 1);
    }

    public boolean exists(String type) {
        return availability.containsKey(type);
    }

    public int getAvailability(String type) {
        return availability.getOrDefault(type, 0);
    }

    public void decrement(String type) throws InvalidBookingException {
        int current = availability.get(type);
        if (current <= 0) {
            throw new InvalidBookingException("Inventory cannot go negative for: " + type);
        }
        availability.put(type, current - 1);
    }
}

class InvalidBookingException extends Exception {

    public InvalidBookingException(String message) {
        super(message);
    }
}