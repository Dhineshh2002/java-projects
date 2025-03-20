package Railway;

import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.Queue;

@Data
@Builder
public class Train {

    private int number;
    private String name;
    private List<Station> stations;
    private Station startingPoint;
    private Station destinationPoint;
    private int maxSeats;
    private int maxWaitingList;
    private List<Ticket> bookedTickets;
    private Queue<Ticket> waitingListTickets;

    public void checkAvailability(Station source, Station destination) {
        int maxPassengerBtwRoute = getMaxPassengerBtwRoute(
                stations.indexOf(source), stations.indexOf(destination));
        int availableTickets = this.maxSeats - maxPassengerBtwRoute;
        int availableWaitingList = (this.maxSeats + this.maxWaitingList) - maxPassengerBtwRoute;
        System.out.println("Train: " + this.name + "(" + this.number + ")");
        if (availableTickets > 0)
            System.out.println(availableTickets + " tickets available.");
        else if (availableWaitingList > 0)
            System.out.println(availableWaitingList + " waiting list available.");
        else
            System.out.println("No tickets are available");
        System.out.println("===================================================");
    }

    public void cancelTicket(Ticket ticket){

        Status status = ticket.getStatus();
        ticket.setStatus(Status.CANCELLED);

        int sourceStationIndex = this.stations.indexOf(ticket.getBoarding());
        int destinationStationIndex = this.stations.indexOf(ticket.getDestination());
        updateStations(sourceStationIndex, destinationStationIndex, -1);

        if (status == Status.CONFIRMED) {
            this.bookedTickets.remove(ticket);
            Ticket waitingListTicket = this.waitingListTickets.poll();
            if(waitingListTicket != null) {
                waitingListTicket.setStatus(Status.CONFIRMED);
                this.bookedTickets.add(waitingListTicket);
            }
        } else if (status == Status.WAITING_LIST) {
            this.waitingListTickets.remove(ticket);
        }

    }

    public List<Ticket> bookTickets(List<Ticket> tickets) {

        for (Ticket ticket : tickets) {

            int sourceStationIndex = this.stations.indexOf(ticket.getBoarding());
            int destinationStationIndex = this.stations.indexOf(ticket.getDestination());
            Status bookingStatus = Status.NOT_CONFIRMED;

            // source & destination must be present in this train,
            // as well as source should present before destination
            if (sourceStationIndex == -1 || destinationStationIndex == -1
                    || destinationStationIndex < sourceStationIndex) {
                ticket.setStatus(bookingStatus);
                continue;
            }
            // booking status is determined
            bookingStatus = determineBookingStatus(sourceStationIndex, destinationStationIndex);
            if (bookingStatus != Status.NOT_CONFIRMED) {

                ticket.generatePnr();
                // if ticket is CONFIRMED or WAITING_LIST,
                // then we are incrementing one passenger count in source - destination this.stations
                updateStations(sourceStationIndex, destinationStationIndex, 1);

                if (bookingStatus == Status.CONFIRMED) {
                    // stored in booked ticket LIST.
                    this.bookedTickets.add(ticket);
                } else if (bookingStatus == Status.WAITING_LIST) {
                    // stored in waiting list QUEUE
                    this.waitingListTickets.add(ticket);
                }
            }
            ticket.setStatus(bookingStatus);
        }
        return tickets;
    }

    private Status determineBookingStatus(int sourceStationIndex, int destinationStationIndex) {
        Status status = Status.NOT_CONFIRMED;
        for (int i = sourceStationIndex; i < destinationStationIndex; i++) {
            int passengersCount = this.stations.get(i).getPassengersCount();
            if (passengersCount < this.maxSeats) {
                // if seats available in all station, then we can confirm
                status = Status.CONFIRMED;
            } else if (passengersCount < this.maxSeats + this.maxWaitingList) {
                return Status.WAITING_LIST;
            } else {
                return Status.NOT_CONFIRMED;
            }
        }
        return status;
    }

    private void updateStations(int sourceIndex, int destinationIndex, int action) {
        for (int i = sourceIndex; i < destinationIndex; i++) {
            Station station = this.stations.get(i);
            station.setPassengersCount(station.getPassengersCount() + action);
        }
    }

    private int getMaxPassengerBtwRoute(int source, int destination) {
        int maxPassengerBtwRoute = Integer.MIN_VALUE;
        for(int i = source; i<destination; i++) {
            Station station = this.stations.get(i);
            maxPassengerBtwRoute = Integer.max(station.getPassengersCount(), maxPassengerBtwRoute);
        }
        return maxPassengerBtwRoute;
    }
}
