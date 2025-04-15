import tokens.FixToken;
import tokens.NumberToken;
import tokens.Tag;
import tokens.Token;

import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

public class Lexer {
    public Queue<Token> createTokenStack(String calculation) throws Exception {
        Queue<Token> tokenQueue = new QueueImpl<>();
        List<Character> charList = calculation.chars().mapToObj(c -> (char) c).collect(Collectors.toList());
        Iterator<Character> iterator = charList.iterator();
        if (!iterator.hasNext()) {
            throw new Exception("String is empty");
        }
        while (iterator.hasNext()) {
            char current = iterator.next();
            if (Character.isDigit(current)) {
                int value = 0;
                while (iterator.hasNext() && Character.isDigit(current)) {
                    value = extractDigit(current, value);
                    current = iterator.next();
                }
                if(!iterator.hasNext()) {
                    value = extractDigit(current, value);
                }
                tokenQueue.enqueue(new NumberToken(value));
            }

            switch (current) {
                case '+' -> tokenQueue.enqueue(new FixToken(Tag.Plus));
                case '-' -> tokenQueue.enqueue(new FixToken(Tag.Minus));
                case '*' -> tokenQueue.enqueue(new FixToken(Tag.Times));
                case '/' -> tokenQueue.enqueue(new FixToken(Tag.Divide));
                case '%' -> tokenQueue.enqueue(new FixToken(Tag.Modulo));
            }
        }
        return tokenQueue;
    }

    private int extractDigit(char current, int value) {
        int digit = current - '0';
        value = (value * 10) + digit;
        return value;
    }
}
