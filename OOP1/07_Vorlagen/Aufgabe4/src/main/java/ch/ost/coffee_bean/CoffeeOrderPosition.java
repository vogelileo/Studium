package ch.ost.coffee_bean;

public class CoffeeOrderPosition {

    private final int amount;
    private final CoffeeType type;

    public CoffeeOrderPosition(int amount, CoffeeType type) {
        this.amount = amount;
        this.type = type;
    }

    public CoffeeType getType() {
        return type;
    }

    public int getAmount() {
        return amount;
    }

}
