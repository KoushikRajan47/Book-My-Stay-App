import java.util.*;

public class HotelBookingApp {

    public static void main(String[] args) {

        RoomInventory inventory = new RoomInventory();
        BookingHistory history = new BookingHistory();
        CancellationService cancellationService = new CancellationService(inventory, history);

        Reservation r1 = new Reservation("R1", "Koushik", "Single", "S1");
        Reservation r2 = new Reservation("R2", "Arun", "Double", "D1");

        history.addReservation(r1);
        history.addReservation(r2);

        cancellationService.cancelBooking("R2");
        cancellationService.cancelBooking("R3");
    }
}

class Reservation {

    private String reservationId;
    private String guestName;
    private String roomType;
    private String roomId;
    private boolean cancelled;

    public Reservation(String reservationId, String guestName, String roomType, String roomId) {
        this.reservationId = reservationId;
        this.guestName = guestName;
        this.roomType = roomType;
        this.roomId = roomId;
        this.cancelled = false;
    }

    public String getReservationId() {
        return reservationId;
    }

    public String getRoomType() {
        return roomType;
    }

    public String getRoomId() {
        return roomId;
    }

    public boolean isCancelled() {
        return cancelled;
    }

    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }
}

class BookingHistory {

    private List<Reservation> history;

    public BookingHistory() {
        history = new ArrayList<>();
    }

    public void addReservation(Reservation reservation) {
        history.add(reservation);
    }

    public Reservation findReservation(String id) {
        for (Reservation r : history) {
            if (r.getReservationId().equals(id)) {
                return r;
            }
        }
        return null;
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

    public void increment(String type) {
        availability.put(type, availability.getOrDefault(type, 0) + 1);
    }
}

class CancellationService {

    private RoomInventory inventory;
    private BookingHistory history;
    private Stack<String> rollbackStack;

    public CancellationService(RoomInventory inventory, BookingHistory history) {
        this.inventory = inventory;
        this.history = history;
        this.rollbackStack = new Stack<>();
    }

    public void cancelBooking(String reservationId) {

        Reservation reservation = history.findReservation(reservationId);

        if (reservation == null) {
            System.out.println("Cancellation Failed: Reservation not found\n");
            return;
        }

        if (reservation.isCancelled()) {
            System.out.println("Cancellation Failed: Already cancelled\n");
            return;
        }

        rollbackStack.push(reservation.getRoomId());

        inventory.increment(reservation.getRoomType());

        reservation.setCancelled(true);

        System.out.println("Cancellation Successful:");
        System.out.println("Reservation ID: " + reservationId);
        System.out.println("Room Released: " + rollbackStack.pop() + "\n");
    }
}