import java.util.Comparator;

public class LandingOrder {

    private final PriorityQueue<Long, Airplane> priorityQueue;

    public LandingOrder() {
        priorityQueue = new PriorityQueue<>(Comparator.comparingLong(o -> o));
    }

    public void addAirplane(Airplane airplane) {
        priorityQueue.insert(airplane.getFuelQuantity(), airplane);
    }

    public Airplane nextAirplane() {
        try{
            return priorityQueue.removeMin().getValue();
        } catch (EmptyPriorityQueueException e) {
            return null;
        }

    }

    public boolean isEmpty() {
        return priorityQueue.isEmpty();
    }

    public static void main(String[] args) {
        LandingOrder landingOrder = new LandingOrder();

        landingOrder.addAirplane(new Airplane("Basel", 20));
        landingOrder.addAirplane(new Airplane("Geneva", 100));
        landingOrder.addAirplane(new Airplane("New-York", 10));
        landingOrder.addAirplane(new Airplane("London", 5));
        landingOrder.addAirplane(new Airplane("Tel Aviv", 300));

        Airplane nextLanding;
        while ((nextLanding = landingOrder.nextAirplane()) != null) {
            System.out.println("Airplane from " + nextLanding.getDepartureAirport()
                    + " has landed");
        }

        System.out.println("All airplanes have landed.");
    }
}

 
 
 
 
