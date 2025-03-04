package Railway;

public class Ticket {

    String pnr;
    Passenger passenger;
    Train train;
    Station boarding;
    Station destination;
    Status status;
    int seatNumber;


    public Ticket(Station destination, Station boarding) {
        this.destination = destination;
        this.boarding = boarding;
    }

    public void print() {
        System.out.println(this.status);
    }
}



enum Status {
    CONFIRMED,
    WAITING_LIST,
    CANCELLED
}
