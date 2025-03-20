package Railway;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Station {

    private String name;
    private String code;
    private int passengersCount;

    boolean isEqual(String input) {
        return name.equalsIgnoreCase(input) || code.equalsIgnoreCase(input);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Station other = (Station) obj;
        return (this.name != null ? this.name.equalsIgnoreCase(other.name) : other.name == null)
                && (this.code != null ? this.code.equalsIgnoreCase(other.code) : other.code == null);
    }

    @Override
    public int hashCode() {
        int result = (name != null ? name.toLowerCase().hashCode() : 0);
        result = 31 * result + (code != null ? code.toLowerCase().hashCode() : 0);
        return result;
    }
}
