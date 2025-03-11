package Railway;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class IndianRailway {

    Scanner scanner = new Scanner(System.in);
    private static ArrayList<Train> trains = new ArrayList<>();
    private static ArrayList<Station> stations = new ArrayList<>();

    public void setStations(ArrayList<Station> stations) {
        this.stations = stations;
    }

    public void addTrains(Train train) {
        trains.add(train);
    }


    public ArrayList<Train> getAvailableTrains(String source, String destination) {
        ArrayList<Train> availableTrains = new ArrayList<>();
        for(Train train : trains) {
            boolean flag_source = false;
            for(Station station: train.stations) {
                if(station.name.equalsIgnoreCase(source) || station.code.equalsIgnoreCase(source)) {
                    flag_source = true;
                } else if(flag_source && (station.name.equalsIgnoreCase(destination) || station.code.equalsIgnoreCase(destination))) {
                    availableTrains.add(train);
                    break;
                }
            }
        }
        return availableTrains;
    }

    public void bookTicket() {

        String source;
        String destination;

        System.out.print("Enter boarding station : ");
        source = scanner.next();
        System.out.print("Enter destination station : ");
        destination = scanner.next();

        Station boardingStation = null;
        Station destinationStation = null;
        boolean bothStationsAreValid = false;

        for (Station station : stations){
            if(boardingStation == null && (station.name.equalsIgnoreCase(source) 
                    || station.code.equalsIgnoreCase(source))) {
                boardingStation = station;
            } else if (destinationStation == null && (station.name.equalsIgnoreCase(destination)
                    || station.code.equalsIgnoreCase(destination))) {
                destinationStation = station;
            }
            if (boardingStation != null && destinationStation != null) {
                bothStationsAreValid = true;
                break;
            }
        };

        if(bothStationsAreValid) {
            ArrayList<Train> availableTrains = getAvailableTrains(source, destination);

            if(!availableTrains.isEmpty()) {

                int trainNumber;
                printTrains(availableTrains);
                System.out.print("Choose train number : ");
                trainNumber = scanner.nextInt();

                Train train = availableTrains.stream()
                        .filter(item -> item.number == trainNumber)
                        .findFirst().orElse(null);

                if(train != null) {
                    ArrayList<Ticket> tickets = new ArrayList<>();
                    int addPassenger = 1;
                    do {
                        Passenger passenger = new Passenger();
                        System.out.print("Enter passenger name: ");
                        passenger.name = scanner.next();
                        System.out.print("Enter passenger age: ");
                        passenger.age = scanner.nextInt();
//                        System.out.print("Enter passenger dob: ");
//                        passenger.dob = scanner.next();
//                        System.out.print("Enter passenger phone: ");
//                        passenger.phone = scanner.next();

                        Ticket ticket = new Ticket(boardingStation, destinationStation, train, passenger);
                        tickets.add(ticket);

                        System.out.println("Press 1 to add passenger, other key to proceed to next step.");
                        addPassenger = scanner.nextInt();
                    } while (addPassenger == 1);
                    tickets = train.bookTickets(tickets);
                    Ticket.printTickets(tickets);
                } else {
                    System.out.println("Invalid train number - " + trainNumber);
                }
            } else {
                System.out.println("No trains are running in selected source to destination");
            }
        } else {
            System.out.println("Invalid station selected!!");
        }
    }

    public void printTrains(ArrayList<Train> trains) {
        for(Train train: trains) {
            System.out.println("---------------------------------------------------------------------------------");
            System.out.println("---number: " + train.number + "----name: " + train.name);
            System.out.println("Seats available: " + train.totalSeats);
            System.out.println("Waiting List available: " + (train.availableWaitingList - train.totalWaitingList));
            System.out.println("---------------------------------------------------------------------------------");
        }
    }

    public void cancelTicket() {
    }
}
