import tokens.FixToken;
import tokens.NumberToken;
import tokens.Tag;
import tokens.Token;

public class MathEvaluator {

    public static void main(String[] args) throws Exception {
        Lexer lexer = new Lexer();
        Queue<Token> stack = lexer.createTokenStack("-110 + 100 -");
        MathEvaluator evaluator = new MathEvaluator();
        System.out.println(evaluator.evaluateTokenStack(stack));
    }

    public boolean evaluateTokenStack(Queue<Token> tokenQueue) throws Exception {
        boolean correctStatement = true;
        while (!tokenQueue.isEmpty()) {
            if (!correctToken(tokenQueue)) {
                return false;
            }
        }
        return correctStatement;
    }

    private boolean correctToken(Queue<Token> tokenQueue) {
        Token current = tokenQueue.dequeue();
        if (current instanceof FixToken) {
            FixToken token = (FixToken) current;
            if (token.getToken() != Tag.Minus) {
                return false;
            }
            current = tokenQueue.dequeue();
        }
        if (!(current instanceof NumberToken)) {
            return false;
        }
        if (tokenQueue.isEmpty()) {
            return true;
        }
        current = tokenQueue.dequeue();
        if (current instanceof FixToken && tokenQueue.isEmpty()) {
            return false;
        }
        return current instanceof FixToken;
    }
}
