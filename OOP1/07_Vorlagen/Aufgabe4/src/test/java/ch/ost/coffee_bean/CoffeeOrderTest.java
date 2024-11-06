package ch.ost.coffee_bean;

import org.junit.jupiter.api.Test;

import static ch.ost.coffee_bean.CoffeeType.ESPRESSO;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CoffeeOrderTest {

    @Test
    void orderIsInitiallyEmpty() {
        assertTrue(new CoffeeOrder().getPositions().isEmpty());
    }

    @Test
    void canAddOrderPosition() {
        var order = new CoffeeOrder();
        order.add(3, ESPRESSO);

        assertEquals(1, order.getPositions().size());
        var first = order.getPositions().iterator().next();
        assertEquals(3, first.getAmount());
        assertEquals(ESPRESSO, first.getType());
    }

}
