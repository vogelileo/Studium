package ch.ost.coffee_bean;

public enum CoffeeType {
    ESPRESSO, LATTE, AMERICANO, MOCHA;

    public String getSize(){
        return switch (this) {
            case ESPRESSO -> "Mittel";
            case LATTE -> "Gross";
            case AMERICANO -> "Klein";
            case MOCHA -> "Mittel";

        };
    }

    public int getAmountOfShots(){
        return switch (this) {
            case ESPRESSO -> 2;
            case LATTE -> 1;
            case AMERICANO -> 2;
            case MOCHA -> 1;
        };
    }

    public boolean isDecaf(){
        return switch (this) {
            case ESPRESSO -> false;
            case LATTE -> false;
            case AMERICANO -> true;
            case MOCHA -> false;
        };
    }
}
