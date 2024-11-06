package ch.ost.coffee_bean;

import static ch.ost.coffee_bean.CoffeeType.*;

public class MainSample {

    public static void main(String[] args) {
        var order = new CoffeeOrder();

        order.add(3, ESPRESSO);
        order.add(1, MOCHA);
        order.add(2, LATTE);

        order.print();
    }

}
