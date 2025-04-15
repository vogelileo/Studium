package tokens;

public class NumberToken implements Token{

    private final int number;

    public NumberToken(int number) {
        this.number = number;
    }

    public int getNumber() {
        return number;
    }
}
