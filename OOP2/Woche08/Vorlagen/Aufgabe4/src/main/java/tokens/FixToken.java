package tokens;

public class FixToken implements Token{

    private final Tag token;

    public FixToken(Tag token) {
        this.token = token;
    }

    public Tag getToken() {
        return token;
    }
}
