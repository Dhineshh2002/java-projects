package Railway;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    HashMap<String, Integer> availableTickets = new HashMap<>();
    HashMap<Integer, Boolean> availableSeats = new HashMap<>();

    public Train(int number, String name, ArrayList<Station> stations, Station startingPoint,
                 Station destinationPoint, int totalSeats, int availableWaitingList,
                 HashMap<String, Integer> availableTickets, HashMap<Integer, Boolean> availableSeats) {
        this.number = number;
        this.name = name;
        this.stations = stations;
        this.startingPoint = startingPoint;
        this.destinationPoint = destinationPoint;
        this.totalSeats = totalSeats;
        this.availableWaitingList = availableWaitingList;
        this.availableTickets = availableTickets;
        this.availableSeats = availableSeats;
    }


    public ArrayList<Train> checkAvailability(String source, String destination) {
        /*
         *  YET TO START..
         */
        return null;
    }

    public ArrayList<Ticket> bookTicket(ArrayList<Ticket> tickets) {
        for(Ticket ticket: tickets) {

            // total seats in train
            int totalSeatsInTrain = this.totalSeats;
            // total waitlist available in train
            int availableWaitingList = this.availableWaitingList;

            // available tickets in each stations
            HashMap<String, Integer> availableTickets = this.availableTickets;

            boolean sourceCrossed = false;
            boolean destinationCrossed = false;
            Status bookingStatus = null;

            for (Map.Entry<String, Integer> entry : availableTickets.entrySet()) {
                String key = entry.getKey();
                int value = entry.getValue();

                if (!sourceCrossed && ticket.boarding.code.equalsIgnoreCase(key)) {
                    sourceCrossed = true;
                } else if (ticket.destination.code.equalsIgnoreCase(key)) {
                    bookingStatus = Status.NOT_CONFIRMED;
                    break;
                }
                boolean canWeBook = true;
                if (sourceCrossed && !destinationCrossed) {
                    canWeBook = value < totalSeatsInTrain;
                }
                if(canWeBook) {
                    bookingStatus = Status.CONFIRMED;
                    this.totalSeats -= 1;
                    availableTickets.put(key, value + 1);
                    assignSeatNumber(ticket);
                } else if (availableWaitingList > 0) {
                    bookingStatus = Status.WAITING_LIST;
                    this.availableWaitingList -= 1;
                    this.totalWaitingList += 1;
                } else {
                    bookingStatus = Status.NOT_CONFIRMED;
                }
            }
            ticket.generatePnr();
            ticket.train = this;
            ticket.status = bookingStatus;
        }
        return tickets;
    }

    private void assignSeatNumber(Ticket ticket) {
        HashMap<Integer, Boolean> availableSeats = ticket.train.availableSeats;

        for(Map.Entry<Integer, Boolean> entry : availableSeats.entrySet()){
            Integer seatNumber = entry.getKey();
            Boolean isBooked = entry.getValue();

            if(!isBooked) {
                ticket.seatNumber = seatNumber;
                return;
            }
        }
        ticket.seatNumber = 0;
    }

}
