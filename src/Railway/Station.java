package Railway;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Station {

    private String name;
    private String code;
    private int passengersCount;

    boolean isEqual(Station station) {
        return this.name.equalsIgnoreCase(station.name)
                || this.code.equalsIgnoreCase(station.code);
    }

    boolean isEqual(String input) {
        return name.equalsIgnoreCase(input) || code.equalsIgnoreCase(input);
    }
}
