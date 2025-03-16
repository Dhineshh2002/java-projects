package Railway;

import lombok.Data;

import java.util.*;

@Data
public class IndianRailway {

    private static Scanner scanner = new Scanner(System.in);
    private static List<Train> trains = new ArrayList<>();
    private static Set<Station> stations = new HashSet<>();

    public void addStations(List<Station> stations) {
        IndianRailway.stations.addAll(stations);
    }

    public void addTrains(Train train) {
        IndianRailway.trains.add(train);
    }


    private List<Train> getAvailableTrains(Station source, Station destination) {
        List<Train> availableTrains = new ArrayList<>();

        for (Train train : trains) {
            List<Station> stations = train.getStations();
            int sourceIndex = -1;

            for (int i = 0; i < stations.size(); i++) {
                Station station = stations.get(i);

                if (station.isEqual(source)) {
                    sourceIndex = i;
                    continue;
                }

                if (sourceIndex != -1 && station.isEqual(destination)) {
                    availableTrains.add(train);
                    break;
                }
            }
        }
        return availableTrains;
    }

    public void bookTicket() {

        HashMap<String, Station> stationHashMap = getBoardingAndDestination();
        Station sourceStation = stationHashMap.get("source");
        Station destinationStation = stationHashMap.get("destination");

        if (sourceStation != null && destinationStation != null) {
            List<Train> availableTrains = getAvailableTrains(sourceStation, destinationStation);
            if (!availableTrains.isEmpty()) {
                Train train = selectTrain(availableTrains);
                if (train != null) {
                    List<Ticket> tickets = getTickets(sourceStation, destinationStation, train);
                    Ticket.printTickets(train.bookTickets(tickets));
                } else {
                    System.out.println("Invalid train has chosen!");
                }
            } else {
                System.out.println("No trains are available in selected route!");
            }
        } else {
            System.out.println("Invalid stations selected!");
        }
    }

    private List<Ticket> getTickets(Station source, Station destination, Train train) {
        List<Ticket> tickets = new ArrayList<>();
        System.out.println("Enter passengers details...");
        int add;
        do {
            String name;
            int age;
            System.out.print("Enter name: ");
            name = scanner.next();
            System.out.print("Enter age: ");
            age = scanner.nextInt();
            Passenger passenger = Passenger.builder()
                    .name(name)
                    .age(age)
                    .build();
            Ticket ticket = Ticket.builder()
                    .passenger(passenger)
                    .boarding(source)
                    .destination(destination)
                    .train(train)
                    .build();
            System.out.print("Press 1 to add another passenger: ");
            add = scanner.nextInt();
            tickets.add(ticket);
        } while (add == 1);

        return tickets;
    }

    private Train selectTrain(List<Train> availableTrains) {
        printTrains(availableTrains);
        System.out.print("Choose train by number: ");
        int trainNumber = scanner.nextInt();
        return availableTrains.stream()
                .filter(train -> train.getNumber() == trainNumber)
                .findFirst()
                .orElse(null);
    }

    private void printTrains(List<Train> trains) {
        for (Train train : trains) {
            System.out.println("---------------------------------------------------------------------------------");
            System.out.println("Number: " + train.getNumber() + ", Name: " + train.getName());
            System.out.println("---------------------------------------------------------------------------------");
        }
    }

    public void cancelTicket() {

        String pnr;
        System.out.print("Enter your pnr: ");
        pnr = scanner.next();
        System.out.println("Are you sure want to cancel? Y/N");
        String isHeSure;
        isHeSure = scanner.next();

        if (isHeSure.equalsIgnoreCase("Y") || isHeSure.equalsIgnoreCase("YES")) {
            Ticket ticket = findTicket(pnr);
            if (ticket != null) {
                ticket.getTrain().cancelTicket(ticket);
            } else {
                System.out.println("Invalid pnr number!");
            }
        } else {
            System.out.println("Ticket cancellation process is cancelled..");
        }

    }

    private Ticket findTicket(String pnr) {
        for (Train train : trains) {
            System.out.println(train.getBookedTickets());
            return train.getBookedTickets().stream()
                    .filter(ticket -> ticket.getPnr().equalsIgnoreCase(pnr))
                    .findFirst().orElseGet(() -> train.getWaitingListTickets().stream()
                            .filter(ticket -> ticket.getPnr().equalsIgnoreCase(pnr))
                            .findFirst()
                            .orElse(null));
        }
        return null;
    }

    public void viewAvailability() {
        HashMap<String, Station> stationHashMap = getBoardingAndDestination();
        Station sourceStation = stationHashMap.get("source");
        Station destinationStation = stationHashMap.get("destination");
        if(sourceStation != null && destinationStation != null) {
            List<Train> availableTrains = getAvailableTrains(sourceStation, destinationStation);
            printTrains(availableTrains);
            availableTrains.forEach(train -> train.checkAvailability(sourceStation));
        }
    }

    private HashMap<String, Station> getBoardingAndDestination() {
        String source;
        String destination;

        System.out.print("Enter boarding station : ");
        source = scanner.next();
        System.out.print("Enter destination station : ");
        destination = scanner.next();

        Station sourceStation = null;
        Station destinationStation = null;


        for (Station station : stations) {
            if (sourceStation == null && station.isEqual(source)) {
                sourceStation = station;
            } else if (destinationStation == null && station.isEqual(destination)) {
                destinationStation = station;
            }
            if (sourceStation != null && destinationStation != null) break;
        }

        HashMap<String, Station> stationMap = new HashMap<>();
        stationMap.put("source", sourceStation);
        stationMap.put("destination", destinationStation);
        return stationMap;
    }
}
