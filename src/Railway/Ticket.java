package Railway;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Random;

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

    public static void printTickets(ArrayList<Ticket> tickets) {
        for(Ticket ticket: tickets) {
            System.out.println("========== TRAIN TICKET ==========");
            System.out.println("PNR: " + ticket.pnr);
            System.out.println("Passenger: " + (ticket.passenger != null ? ticket.passenger.name : "N/A"));
            System.out.println("Train: " + (ticket.train != null ? ticket.train.name : "N/A"));
            System.out.println("Boarding: " + (ticket.boarding != null ? ticket.boarding.name + " (" + ticket.boarding.code + ")" : "N/A"));
            System.out.println("Destination: " + (ticket.destination != null ? ticket.destination.name + " (" + ticket.destination.code + ")" : "N/A"));
            System.out.println("Seat Number: " + ticket.seatNumber);
            System.out.println("Status: " + (ticket.status != null ? ticket.status : "N/A"));
            System.out.println("==================================");
        }
    }

    public Ticket generatePnr() {
        SecureRandom random = new SecureRandom();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyMMddHHmm");
        this.pnr = this.boarding.code + this.destination.code;
        this.pnr += LocalDateTime.now().format(formatter);
        this.pnr += 10000 + random.nextInt(90000);
        return this;
    }
}



enum Status {

    CONFIRMED,
    WAITING_LIST,
    CANCELLED,
    NOT_CONFIRMED

}
