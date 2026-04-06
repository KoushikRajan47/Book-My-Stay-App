import java.util.LinkedList;
import java.util.Queue;

public class HotelBookingApp {

    public static void main(String[] args) {

        BookingRequestQueue bookingQueue = new BookingRequestQueue();

        bookingQueue.addRequest(new Reservation("R1", "Koushik", "Single"));
        bookingQueue.addRequest(new Reservation("R2", "Arun", "Double"));
        bookingQueue.addRequest(new Reservation("R3", "Rahul", "Suite"));

        bookingQueue.displayQueue();
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

    public void displayQueue() {
        System.out.println("===== BOOKING REQUEST QUEUE =====");

        for (Reservation r : queue) {
            System.out.println("ID: " + r.getReservationId() +
                    ", Guest: " + r.getGuestName() +
                    ", Room: " + r.getRoomType());
        }
    }
}