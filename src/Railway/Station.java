package Railway;

public class Station {

    public String name;
    public String code;
    public int passengersCount = 0;

    public Station(String name, String code) {
        this.name = name;
        this.code = code;
    }

    boolean isEqual(Station station) {
        return this.name.equalsIgnoreCase(station.name) || this.code.equalsIgnoreCase(station.code);
    }
}
