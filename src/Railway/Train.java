package Railway;

import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
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

    public void checkAvailability(Station source) {
        Station s = this.stations.stream().filter(station -> station.isEqual(source))
                .findFirst().orElse(null);
        if (s != null) {
            int availableTickets = this.maxSeats - s.getPassengersCount();
            int availableWaitingList = (this.maxSeats + this.maxWaitingList) - s.getPassengersCount();

            System.out.println(name + "(" + number + ")");
            if (availableTickets > 0) {
                System.out.println(availableTickets + " tickets available.");
            } else if (availableWaitingList > 0) {
                System.out.println(availableWaitingList + " waiting list available.");
            } else {
                System.out.println("No tickets are available");
            }
            System.out.println("===================================================");
        } else {
            System.out.println("Sorry, some error occurred!!!");
        }
    }

    public void cancelTicket(Ticket ticket) {

        Status status = ticket.getStatus();
        ticket.setStatus(Status.CANCELLED);

        int sourceStationIndex = this.stations.indexOf(ticket.getBoarding());
        int destinationStationIndex = this.stations.indexOf(ticket.getDestination());
        updateStations(sourceStationIndex, destinationStationIndex, -1);

        if (status == Status.CONFIRMED) {
            this.bookedTickets.remove(ticket);
        } else if (status == Status.WAITING_LIST) {
            this.waitingListTickets.remove(ticket);
        }

    }

    public List<Ticket> bookTickets(List<Ticket> tickets) {

        for (Ticket ticket : tickets) {

            int sourceStationIndex = this.stations.indexOf(ticket.getBoarding());
            int destinationStationIndex = this.stations.indexOf(ticket.getDestination());
            Status bookingStatus = Status.NOT_CONFIRMED;

            // source & destination must be present in this train, as well as source should present before destination
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
        for (int i = sourceStationIndex; i <= destinationStationIndex; i++) {
            int passengersCount = this.stations.get(i).getPassengersCount();
            if (passengersCount < this.maxSeats) {
                // if seats available in all station, then we can confirm
                status = Status.CONFIRMED;
            } else if (passengersCount < this.maxSeats + this.maxWaitingList) {
                status = Status.WAITING_LIST;
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
}
