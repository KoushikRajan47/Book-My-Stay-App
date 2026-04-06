import java.util.*;

public class HotelBookingApp {

    public static void main(String[] args) {

        RoomInventory inventory = new RoomInventory();
        BookingRequestQueue queue = new BookingRequestQueue();

        queue.addRequest(new Reservation("R1", "Koushik", "Single"));
        queue.addRequest(new Reservation("R2", "Arun", "Double"));
        queue.addRequest(new Reservation("R3", "Rahul", "Suite"));
        queue.addRequest(new Reservation("R4", "Vijay", "Single"));

        BookingService bookingService = new BookingService(inventory);

        bookingService.processBookings(queue);
    }
}

class Reservation {

    private String reservationId;
    private String guestName;
    private String roomType;

    public Reservation(String reservationId, String guestName, String roomType) {
        this.reservationId = reservationId;
        this.guestName = guestName;
        this.roomType = roomType;
    }

    public String getReservationId() {
        return reservationId;
    }

    public String getGuestName() {
        return guestName;
    }

    public String getRoomType() {
        return roomType;
    }
}

class BookingRequestQueue {

    private Queue<Reservation> queue;

    public BookingRequestQueue() {
        queue = new LinkedList<>();
    }

    public void addRequest(Reservation reservation) {
        queue.add(reservation);
    }

    public Reservation getNextRequest() {
        return queue.poll();
    }

    public boolean isEmpty() {
        return queue.isEmpty();
    }
}

class RoomInventory {

    private Map<String, Integer> availability;

    public RoomInventory() {
        availability = new HashMap<>();
        availability.put("Single", 2);
        availability.put("Double", 1);
        availability.put("Suite", 1);
    }

    public int getAvailability(String roomType) {
        return availability.getOrDefault(roomType, 0);
    }

    public void decrement(String roomType) {
        availability.put(roomType, availability.get(roomType) - 1);
    }
}

class BookingService {

    private RoomInventory inventory;
    private Set<String> allocatedRoomIds;
    private Map<String, Set<String>> roomAllocations;
    private int roomCounter = 1;

    public BookingService(RoomInventory inventory) {
        this.inventory = inventory;
        this.allocatedRoomIds = new HashSet<>();
        this.roomAllocations = new HashMap<>();
    }

    public void processBookings(BookingRequestQueue queue) {

        while (!queue.isEmpty()) {

            Reservation request = queue.getNextRequest();
            String type = request.getRoomType();

            if (inventory.getAvailability(type) > 0) {

                String roomId = generateRoomId(type);

                allocatedRoomIds.add(roomId);

                roomAllocations.putIfAbsent(type, new HashSet<>());
                roomAllocations.get(type).add(roomId);

                inventory.decrement(type);

                System.out.println("Booking Confirmed:");
                System.out.println("Guest: " + request.getGuestName());
                System.out.println("Room Type: " + type);
                System.out.println("Room ID: " + roomId + "\n");

            } else {
                System.out.println("Booking Failed for " + request.getGuestName() + " (No availability)\n");
            }
        }
    }

    private String generateRoomId(String type) {
        String roomId;
        do {
            roomId = type.substring(0, 1).toUpperCase() + roomCounter++;
        } while (allocatedRoomIds.contains(roomId));
        return roomId;
    }
}