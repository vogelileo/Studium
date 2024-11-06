package ch.ost.coffee_bean;

import org.junit.jupiter.api.Test;

import static ch.ost.coffee_bean.CoffeeType.*;
import static org.junit.jupiter.api.Assertions.*;

class CoffeeTypeTest {

    @Test
    void testEspresso() {
        assertEquals("Mittel", ESPRESSO.getSize());
        assertEquals(2, ESPRESSO.getAmountOfShots());
        assertFalse(ESPRESSO.isDecaf());
    }

    @Test
    void testLatte() {
        assertEquals("Gross", LATTE.getSize());
        assertEquals(1, LATTE.getAmountOfShots());
        assertFalse(LATTE.isDecaf());
    }

    @Test
    void testAmericano() {
        assertEquals("Klein", AMERICANO.getSize());
        assertEquals(2, AMERICANO.getAmountOfShots());
        assertTrue(AMERICANO.isDecaf());
    }

    @Test
    void testMocha() {
        assertEquals("Mittel", MOCHA.getSize());
        assertEquals(1, MOCHA.getAmountOfShots());
        assertFalse(MOCHA.isDecaf());
    }

}
