package Railway;

import java.util.ArrayList;
import java.util.Scanner;

public class IndianRailway {

    Scanner scanner = new Scanner(System.in);
    private static ArrayList<Train> trains = new ArrayList<>();
    private static ArrayList<Station> stations = new ArrayList<>();

    public void addTrains(Train train) {

        trains.add(train);

    }


    public ArrayList<Train> getAvailableTrains(String source, String destination) {

        ArrayList<Train> availableTrains = new ArrayList<>();

        for(Train train : trains) {
            boolean flag_source = false,
                    flag_destination = false;
            for(Station station: train.stations) {
                if(station.name.equals(source) || station.code.equals(source)) {
                    flag_source = true;
                }
                if(flag_source && (station.name.equals(destination) || station.code.equals(destination))) {
                    flag_destination = true;
                }
            }
            if(flag_source && flag_destination) {
                availableTrains.add(train);
            }
        }
        return availableTrains;
    }

    public void bookTicket() {
        String source;
        String destination;

        System.out.print("Enter boarding station : ");
        source = scanner.next();
        System.out.print("Enter destination station: ");
        destination = scanner.next();

        Station boardinStation = stations.stream()
                .filter((station) -> (station.name.equals(source) || station.code.equals(source)))
                .findFirst()
                .orElse(null);

        Station destinationStation = stations.stream()
                .filter((station) -> (station.name.equals(destination) || station.code.equals(destination)))
                .findFirst()
                .orElse(null);

        Ticket ticket = new Ticket(boardinStation, destinationStation);
        ArrayList<Train> availableTrains = getAvailableTrains(source, destination);

        if(!availableTrains.isEmpty()) {

            int trainNumber;
            printTrains(availableTrains);
            System.out.print("Choose train number : ");
            trainNumber = scanner.nextInt();


            Train train = availableTrains.stream()
                    .filter(item -> item.number == trainNumber)
                    .findFirst().orElse(null);

            ticket = train.bookTicket(ticket);

            ticket.print();

        } else {
            System.out.println("No trains are running in selected source to destination");
        }
    }

    public void printTrains(ArrayList<Train> trains) {
        for(Train train: trains) {
            System.out.println("Train number: " + train.number);
            System.out.println("Train name: " + train.name);
            System.out.println("Seats available: " + train.totalSeats);
            System.out.println("Waiting List available: " + (train.availableWaitingList - train.totalWaitingList));
            System.out.println("---------------------------------------------------------------------------------");
        }
    }

}
