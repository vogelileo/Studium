import java.util.Objects;

public class Airplane {

  private final String departureAirport;
  private final long fuelQuantity;

  public Airplane(String departureAirport, long fuelQuantity) {
    this.departureAirport = departureAirport;
    this.fuelQuantity = fuelQuantity;
  }

  public String getDepartureAirport() {
    return departureAirport;
  }

  public long getFuelQuantity() {
    return fuelQuantity;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Airplane airplane = (Airplane) o;
    return fuelQuantity == airplane.fuelQuantity && Objects.equals(departureAirport, airplane.departureAirport);
  }

  @Override
  public int hashCode() {
    return Objects.hash(departureAirport, fuelQuantity);
  }
}
 
 
 
 
