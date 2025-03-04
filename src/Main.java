import Railway.IndianRailway;
import Railway.Station;
import Railway.Train;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        IndianRailway railway = new IndianRailway();
        Station station = new Station("Thanjavur", "THN");
        Station station1 = new Station("Kumbakonam", "KUM");
        Station station2 = new Station("Egmore", "MS");
        ArrayList<Station> stations = new ArrayList<>();
        stations.add(station);
        stations.add(station1);
        stations.add(station2);
        Train train = new Train(
                1932,
                "Uzhavan Express",
                stations,
                station,
                station2,
                10,
                3
        );
        railway.addTrains(train);

        Scanner sc = new Scanner(System.in);
        System.out.println("..Train Ticket Booking..");
        int ch;
        System.out.println("1.Booking");
        System.out.println("2.Cancel");

        ch = sc.nextInt();

        switch (ch) {
            case 1: {
                railway.bookTicket();
                break;
            }
            case 2: {
                System.out.println("Ticket cancelled");
                break;
            }
            default:
                break;
        }
    }
}