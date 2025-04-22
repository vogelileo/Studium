import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class TestLandingOrder {

    @Test
    public void testNextAirplane() {
        LandingOrder landingOrder = new LandingOrder();

        Airplane basel = new Airplane("Basel", 20);
        Airplane geneva = new Airplane("Geneva", 100);
        Airplane newYork = new Airplane("New-York", 10);
        Airplane london = new Airplane("London", 5);
        Airplane telAviv = new Airplane("Tel Aviv", 300);

        List<Airplane> list = new ArrayList<>();
        list.add(london);
        list.add(newYork);
        list.add(basel);
        list.add(geneva);
        list.add(telAviv);

        landingOrder.addAirplane(basel);
        landingOrder.addAirplane(geneva);
        landingOrder.addAirplane(newYork);
        landingOrder.addAirplane(london);
        landingOrder.addAirplane(telAviv);

        Airplane nextLanding;
        int count = 0;
        while ((nextLanding = landingOrder.nextAirplane()) != null) {
            Assertions.assertEquals(list.get(count), nextLanding);
            count++;
        }
    }
}
