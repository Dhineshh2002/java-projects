import Railway.IndianRailway;
import Railway.Station;
import Railway.Train;
import java.util.*;


public class Main {

    private static final IndianRailway railway = new IndianRailway();
    private static final Scanner sc = new Scanner(System.in);
    
    public static void main(String[] args) {
        addingBasicNeeds();
        startingApplication();
    }

    private static void addingBasicNeeds() {

        List<Station> uzhavanExpressStations = new ArrayList<>();
        uzhavanExpressStations.add(Station.builder().name("Thanjavur").code("THN").passengersCount(0).build());
        uzhavanExpressStations.add(Station.builder().name("Pabanasam").code("PBS").passengersCount(0).build());
        uzhavanExpressStations.add(Station.builder().name("Kumbakonam").code("KUM").passengersCount(0).build());
        uzhavanExpressStations.add(Station.builder().name("Mayiladurai").code("MYD").passengersCount(0).build());
        uzhavanExpressStations.add(Station.builder().name("Sirgazhi").code("SIR").passengersCount(0).build());
        uzhavanExpressStations.add(Station.builder().name("Sidambaram").code("SID").passengersCount(0).build());
        uzhavanExpressStations.add(Station.builder().name("Chengalpattu").code("CHEN").passengersCount(0).build());
        uzhavanExpressStations.add(Station.builder().name("Tambaram").code("TBM").passengersCount(0).build());
        uzhavanExpressStations.add(Station.builder().name("Mambalam").code("MBM").passengersCount(0).build());
        uzhavanExpressStations.add(Station.builder().name("Egmore").code("MS").passengersCount(0).build());


        Train uzhavanExpress = Train.builder()
                .number(1)
                .name("Uzhavan Express")
                .stations(uzhavanExpressStations)
                .startingPoint(uzhavanExpressStations.get(0))
                .destinationPoint(uzhavanExpressStations.get(uzhavanExpressStations.size()-1))
                .maxSeats(0)
                .maxWaitingList(1)
                .bookedTickets(new ArrayList<>())
                .waitingListTickets(new PriorityQueue<>(1))
                .build();

        List<Station> antyodayaStations = new ArrayList<>();
        antyodayaStations.add(Station.builder().name("Nagarcoil").code("NGL").passengersCount(0).build());
        antyodayaStations.add(Station.builder().name("Trichy").code("NGL").passengersCount(0).build());
        antyodayaStations.add(Station.builder().name("Thanjavur").code("THN").passengersCount(0).build());
        antyodayaStations.add(Station.builder().name("Pabanasam").code("PBS").passengersCount(0).build());
        antyodayaStations.add(Station.builder().name("Kumbakonam").code("KUM").passengersCount(0).build());
        antyodayaStations.add(Station.builder().name("Mayiladurai").code("MYD").passengersCount(0).build());
        antyodayaStations.add(Station.builder().name("Sirgazhi").code("SIR").passengersCount(0).build());
        antyodayaStations.add(Station.builder().name("Sidambaram").code("SID").passengersCount(0).build());
        antyodayaStations.add(Station.builder().name("Chengalpattu").code("CHEN").passengersCount(0).build());
        antyodayaStations.add(Station.builder().name("Tambaram").code("TBM").passengersCount(0).build());

        Train antyodaya = Train.builder()
                .number(2)
                .name("Antyodaya")
                .stations(antyodayaStations)
                .startingPoint(antyodayaStations.get(0))
                .destinationPoint(antyodayaStations.get(antyodayaStations.size()-1))
                .maxSeats(0)
                .maxWaitingList(1)
                .bookedTickets(new ArrayList<>())
                .waitingListTickets(new PriorityQueue<>(1))
                .build();

        railway.addTrains(uzhavanExpress);
        railway.addTrains(antyodaya);

        railway.addStations(uzhavanExpressStations);
        railway.addStations(antyodayaStations);
    }
    
    private static void startingApplication() {
        System.out.println("Train Ticket Booking Started =========================");
        int choice;
        do {
            choice = getChoice();
            switch (choice) {
                case 1 -> railway.bookTicket();
                case 2 -> railway.cancelTicket();
                case 3 -> railway.viewAvailability();
                case 4 -> System.out.println("Exiting Train Ticket Booking System...");
                default -> System.out.println("Invalid choice! Please enter a valid option.");
            }
        } while (choice != 4);
    }

    private static int getChoice() {
        int choice;
        System.out.println("1.Book Ticket");
        System.out.println("2.Cancel Ticket");
        System.out.println("3.View Availability");
        System.out.println("4.Exit..");
        choice = sc.nextInt();
        return choice;
    }
}