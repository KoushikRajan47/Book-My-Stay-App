import java.util.*;

public class HotelBookingApp {

    public static void main(String[] args) {

        BookingHistory history = new BookingHistory();

        history.addReservation(new Reservation("R1", "Koushik", "Single", "S1"));
        history.addReservation(new Reservation("R2", "Arun", "Double", "D1"));
        history.addReservation(new Reservation("R3", "Rahul", "Suite", "SU1"));

        BookingReportService reportService = new BookingReportService(history);

        reportService.displayAllBookings();
        reportService.generateSummaryReport();
    }
}

class Reservation {

    private String reservationId;
    private String guestName;
    private String roomType;
    private String roomId;

    public Reservation(String reservationId, String guestName, String roomType, String roomId) {
        this.reservationId = reservationId;
        this.guestName = guestName;
        this.roomType = roomType;
        this.roomId = roomId;
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

    public String getRoomId() {
        return roomId;
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

    public List<Reservation> getAllReservations() {
        return history;
    }
}

class BookingReportService {

    private BookingHistory history;

    public BookingReportService(BookingHistory history) {
        this.history = history;
    }

    public void displayAllBookings() {

        System.out.println("===== BOOKING HISTORY =====");

        for (Reservation r : history.getAllReservations()) {
            System.out.println("ID: " + r.getReservationId() +
                    ", Guest: " + r.getGuestName() +
                    ", Room Type: " + r.getRoomType() +
                    ", Room ID: " + r.getRoomId());
        }

        System.out.println();
    }

    public void generateSummaryReport() {

        Map<String, Integer> summary = new HashMap<>();

        for (Reservation r : history.getAllReservations()) {
            summary.put(r.getRoomType(),
                    summary.getOrDefault(r.getRoomType(), 0) + 1);
        }

        System.out.println("===== BOOKING SUMMARY =====");

        for (Map.Entry<String, Integer> entry : summary.entrySet()) {
            System.out.println(entry.getKey() + " Bookings: " + entry.getValue());
        }
    }
}