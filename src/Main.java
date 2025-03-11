import Railway.IndianRailway;
import Railway.Station;
import Railway.Train;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Scanner;

public class Main {

    static IndianRailway railway = new IndianRailway();

    public static void main(String[] args) {

        Station station = new Station("Thanjavur", "THN");
        Station station1 = new Station("Pabanasam", "PBS");
        Station station2 = new Station("Kumbakonam", "KUM");
        Station station3 = new Station("Mayiladurai", "MYD");
        Station station4 = new Station("Sirgazhi", "SIR");
        Station station5 = new Station("Sidambaram", "SID");
        Station station6 = new Station("Chengalpattu", "CHEN");
        Station station7 = new Station("Tambaram", "TBM");
        Station station8 = new Station("Mambalam", "MBM");
        Station station9 = new Station("Egmore", "MS");

        ArrayList<Station> uzhavanExpressStations = new ArrayList<>();
        uzhavanExpressStations.add(station);
        uzhavanExpressStations.add(station1);
        uzhavanExpressStations.add(station2);
        uzhavanExpressStations.add(station3);
        uzhavanExpressStations.add(station4);
        uzhavanExpressStations.add(station5);
        uzhavanExpressStations.add(station6);
        uzhavanExpressStations.add(station7);
        uzhavanExpressStations.add(station8);
        uzhavanExpressStations.add(station9);

        LinkedHashMap<Integer, Boolean> availableSeats = generateSeats(1);

        Train uzhalavanExpress = new Train(
                1000,
                "Uzhavan Express",
                uzhavanExpressStations,
                station,
                station9,
                1,
                1,
                availableSeats
        );

        railway.addTrains(uzhalavanExpress);
        railway.setStations(uzhavanExpressStations);

        Scanner sc = new Scanner(System.in);

        System.out.println("..Train Ticket Booking..");

        int ch;
        System.out.println("1.Book Ticket");
        System.out.println("2.Cancel Ticket");
        System.out.println("3.View Availability");
        System.out.println("4.Exit..");
        ch = sc.nextInt();
        do {
            switch (ch) {
                case 1: {
                    railway.bookTicket();
                    break;
                }
                case 2: {
                    railway.cancelTicket();
                    System.out.println("Ticket cancelled");
                    break;
                }
                case 3: {
                    System.out.println("Ticket are not available!");
                    break;
                }
                case 4: {
                    break;
                }
                default:
                    break;
            }
            System.out.println("1.Book Ticket");
            System.out.println("2.Cancel Ticket");
            System.out.println("3.View Availability");
            System.out.println("4.Exit..");
            ch = sc.nextInt();
        } while (ch != 4);
    }

    private static LinkedHashMap<Integer, Boolean> generateSeats(int totalSeats) {
        LinkedHashMap<Integer, Boolean> generatedSeats = new LinkedHashMap<>();
        for (int i = 1; i <= totalSeats; i++) {
            generatedSeats.put(i, false);
        }
        return generatedSeats;
    }
}