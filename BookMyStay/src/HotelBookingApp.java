public class HotelBookingApp {

    public static void main(String[] args) {

        Room single = new SingleRoom();
        Room doubleRoom = new DoubleRoom();
        Room suite = new SuiteRoom();

        int singleAvailable = 5;
        int doubleAvailable = 3;
        int suiteAvailable = 2;

        System.out.println("===== HOTEL ROOM DETAILS =====\n");

        single.displayDetails();
        System.out.println("Available Rooms: " + singleAvailable + "\n");

        doubleRoom.displayDetails();
        System.out.println("Available Rooms: " + doubleAvailable + "\n");

        suite.displayDetails();
        System.out.println("Available Rooms: " + suiteAvailable + "\n");
    }
}

abstract class Room {

    protected int beds;
    protected double size;
    protected double price;

    public Room(int beds, double size, double price) {
        this.beds = beds;
        this.size = size;
        this.price = price;
    }

    public abstract void displayDetails();
}

class SingleRoom extends Room {

    public SingleRoom() {
        super(1, 150.0, 2000.0);
    }

    public void displayDetails() {
        System.out.println("Room Type: Single Room");
        System.out.println("Beds: " + beds);
        System.out.println("Size: " + size + " sq ft");
        System.out.println("Price: ₹" + price);
    }
}

class DoubleRoom extends Room {

    public DoubleRoom() {
        super(2, 250.0, 3500.0);
    }

    public void displayDetails() {
        System.out.println("Room Type: Double Room");
        System.out.println("Beds: " + beds);
        System.out.println("Size: " + size + " sq ft");
        System.out.println("Price: ₹" + price);
    }
}

class SuiteRoom extends Room {

    public SuiteRoom() {
        super(3, 400.0, 7000.0);
    }

    public void displayDetails() {
        System.out.println("Room Type: Suite Room");
        System.out.println("Beds: " + beds);
        System.out.println("Size: " + size + " sq ft");
        System.out.println("Price: ₹" + price);
    }
}