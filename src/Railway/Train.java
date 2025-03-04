package Railway;

import java.util.ArrayList;

public class Train {

    int number;
    String name;
    ArrayList<Station> stations;
    Station startingPoint;
    Station destinationPoint;
    int totalSeats;
    int availableWaitingList;
    int totalWaitingList;
    ArrayList<Ticket> tickets;

    public Train(int number, String name, ArrayList<Station> stations, Station startingPoint,
                 Station destinationPoint, int totalSeats, int availableWaitingList) {
        this.number = number;
        this.name = name;
        this.stations = stations;
        this.startingPoint = startingPoint;
        this.destinationPoint = destinationPoint;
        this.totalSeats = totalSeats;
        this.availableWaitingList = availableWaitingList;
    }


    public ArrayList<Train> checkAvailability(String source, String destination) {
        int totalTickets = this.tickets.size();
        for (int i = 0; i < totalTickets; i++) {

        }
        return null;

    }

    public Ticket bookTicket(Ticket ticket) {
        ticket.status = Status.CONFIRMED;

        return ticket;
    }
}
