package Railway;

import java.util.*;

public class Train {

    int number;
    String name;
    ArrayList<Station> stations;
    Station startingPoint;
    Station destinationPoint;
    int totalSeats;
    HashMap<Integer, Boolean> availableSeats;
    int totalWaitingList;
    int availableWaitingList;
    ArrayList<Ticket> bookedTickets;
    Queue<Ticket> waitingListTickets;

    public Train(int number, String name, ArrayList<Station> stations, Station startingPoint,
                 Station destinationPoint, int totalSeats, int availableWaitingList, HashMap<Integer, Boolean> availableSeats) {
        this.number = number;
        this.name = name;
        this.stations = stations;
        this.startingPoint = startingPoint;
        this.destinationPoint = destinationPoint;
        this.totalSeats = totalSeats;
        this.availableWaitingList = availableWaitingList;
        this.availableSeats = availableSeats;
    }


    public ArrayList<Train> checkAvailability(String source, String destination) {
        /*
         *  YET TO START..
         */
        return null;
    }

    public ArrayList<Ticket> bookTickets(ArrayList<Ticket> tickets) {
        for (Ticket ticket : tickets) {

            // total seats in train
            int totalSeatsInTrain = this.totalSeats;
            // total waitlist available in train
            int availableWaitingList = this.availableWaitingList;

            boolean sourceCrossed = false;
            boolean destinationCrossed = false;
            boolean canWeBookTicket = false;

            for (Station station : ticket.getTrain().stations) {
                if(!sourceCrossed && station.isEqual(ticket.getBoarding())) {
                    sourceCrossed = true;
                } else if (sourceCrossed && station.isEqual(ticket.getDestination())) {
                    break;
                }
                if(sourceCrossed) {
                    int passengersCount = station.passengersCount;
                    canWeBookTicket = passengersCount < totalSeatsInTrain;
                }
            }

            if(canWeBookTicket) {
                for (Map.Entry<Integer, Boolean> entry : availableSeats.entrySet()) {
                    Integer seatNumber = entry.getKey();
                    Boolean isBooked = entry.getValue();
                    if (!isBooked) {
                        ticket.generatePnr()
                                .setSeatNumber(seatNumber).setStatus(Status.CONFIRMED);
                        availableSeats.put(seatNumber, true);
                        break;
                    }
                }
                if(ticket.getStatus() != Status.CONFIRMED && waitingListTickets.size() < totalWaitingList) {
                    ticket.generatePnr()
                            .setSeatNumber(0).setStatus(Status.WAITING_LIST);
                    waitingListTickets.add(ticket);
                    break;
                }
                ticket.setStatus(Status.NOT_CONFIRMED);
            }
        }
        return tickets;
    }

}
