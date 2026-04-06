import java.util.*;

public class HotelBookingApp {

    public static void main(String[] args) {

        BookingQueue queue = new BookingQueue();
        RoomInventory inventory = new RoomInventory();

        queue.addRequest(new Reservation("R1", "Koushik", "Single"));
        queue.addRequest(new Reservation("R2", "Arun", "Single"));
        queue.addRequest(new Reservation("R3", "Rahul", "Single"));

        Runnable task = new ConcurrentBookingProcessor(queue, inventory);

        Thread t1 = new Thread(task);
        Thread t2 = new Thread(task);
        Thread t3 = new Thread(task);

        t1.start();
        t2.start();
        t3.start();
    }
}

class Reservation {

    private String id;
    private String guest;
    private String type;

    public Reservation(String id, String guest, String type) {
        this.id = id;
        this.guest = guest;
        this.type = type;
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
}

class BookingQueue {

    private Queue<Reservation> queue = new LinkedList<>();

    public synchronized void addRequest(Reservation r) {
        queue.add(r);
    }

    public synchronized Reservation getNext() {
        return queue.poll();
    }

    public synchronized boolean isEmpty() {
        return queue.isEmpty();
    }
}

class RoomInventory {

    private Map<String, Integer> availability;

    public RoomInventory() {
        availability = new HashMap<>();
        availability.put("Single", 2);
    }

    public synchronized boolean allocate(String type) {
        int count = availability.getOrDefault(type, 0);
        if (count > 0) {
            availability.put(type, count - 1);
            return true;
        }
        return false;
    }
}

class ConcurrentBookingProcessor implements Runnable {

    private BookingQueue queue;
    private RoomInventory inventory;

    public ConcurrentBookingProcessor(BookingQueue queue, RoomInventory inventory) {
        this.queue = queue;
        this.inventory = inventory;
    }

    public void run() {

        while (true) {

            Reservation r;

            synchronized (queue) {
                if (queue.isEmpty()) break;
                r = queue.getNext();
            }

            if (r != null) {
                boolean success;

                synchronized (inventory) {
                    success = inventory.allocate(r.getType());
                }

                if (success) {
                    System.out.println(Thread.currentThread().getName() +
                            " booked room for " + r.getGuest() +
                            " (" + r.getType() + ")");
                } else {
                    System.out.println(Thread.currentThread().getName() +
                            " failed booking for " + r.getGuest());
                }
            }
        }
    }
}