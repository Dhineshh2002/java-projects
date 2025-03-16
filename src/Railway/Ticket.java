package Railway;

import lombok.Builder;
import lombok.Data;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Data
@Builder
public class Ticket implements Comparable<Ticket>{

    private String pnr;
    private Passenger passenger;
    private Train train;
    private Station boarding;
    private Station destination;
    private Status status;
    private int seatNumber;

    public static void printTickets(List<Ticket> tickets) {
        for (Ticket ticket : tickets) {
            System.out.println("========== TICKET ==========");
            System.out.println("PNR: " + ticket.pnr);
            System.out.println("Passenger: " + (ticket.passenger != null ? ticket.passenger.getName() : "N/A"));
            System.out.println("Train: " + (ticket.train != null ? ticket.train.getName() : "N/A"));
            System.out.print((ticket.boarding != null ? ticket.boarding.getName()
                    + " (" + ticket.boarding.getCode() + ")" : "N/A"));
            System.out.println(" to " + (ticket.destination != null ? ticket.destination.getName()
                    + " (" + ticket.destination.getCode() + ")" : "N/A"));
            System.out.println("Status: " + (ticket.status != null ? ticket.status : "N/A"));
            System.out.println("==================================");
        }
    }

    public void generatePnr() {
        if(pnr == null) {
            SecureRandom random = new SecureRandom();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyMMddHHmm");
            pnr = this.boarding.getCode() + this.destination.getCode();
            pnr += LocalDateTime.now().format(formatter);
            pnr += 10000 + random.nextInt(90000);
        }
    }

    @Override
    public int compareTo(Ticket o) {
        return 0;
    }

}


enum Status {

    CONFIRMED,
    WAITING_LIST,
    CANCELLED,
    NOT_CONFIRMED

}
