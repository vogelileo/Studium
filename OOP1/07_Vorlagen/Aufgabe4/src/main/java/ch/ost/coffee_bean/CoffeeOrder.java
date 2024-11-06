package ch.ost.coffee_bean;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class CoffeeOrder {

    private final ArrayList<CoffeeOrderPosition> positions = new ArrayList<>();

    public void add(int amount, CoffeeType type) {
        this.positions.add(new CoffeeOrderPosition(amount, type));
    }

    public Collection<CoffeeOrderPosition> getPositions() {
        return Collections.unmodifiableCollection(positions);
    }

    public void print() {
        System.out.println("You ordered:");
        for (CoffeeOrderPosition position : positions) {
            System.out.println(position.getAmount() + " times " + position.getType());
        }
    }
}
