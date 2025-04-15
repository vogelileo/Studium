import tokens.Token;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class EvaluatorTests {

    @Test
    public void testSimplePlus() throws Exception {
        Lexer lexer = new Lexer();
        Queue<Token> queue = lexer.createTokenStack("100 + 1");
        MathEvaluator evaluator = new MathEvaluator();
        Assertions.assertTrue(evaluator.evaluateTokenStack(queue));
    }

    @Test
    public void testSimpleMinus() throws Exception {
        Lexer lexer = new Lexer();
        Queue<Token> queue = lexer.createTokenStack("100 - 1");
        MathEvaluator evaluator = new MathEvaluator();
        Assertions.assertTrue(evaluator.evaluateTokenStack(queue));
    }

    @Test
    public void testSimpleTimes() throws Exception {
        Lexer lexer = new Lexer();
        Queue<Token> queue = lexer.createTokenStack("100 * 1");
        MathEvaluator evaluator = new MathEvaluator();
        Assertions.assertTrue(evaluator.evaluateTokenStack(queue));
    }

    @Test
    public void testSimpleDivide() throws Exception {
        Lexer lexer = new Lexer();
        Queue<Token> queue = lexer.createTokenStack("100 / 1");
        MathEvaluator evaluator = new MathEvaluator();
        Assertions.assertTrue(evaluator.evaluateTokenStack(queue));
    }

    @Test
    public void testSimpleModulo() throws Exception {
        Lexer lexer = new Lexer();
        Queue<Token> queue = lexer.createTokenStack("100 % 1");
        MathEvaluator evaluator = new MathEvaluator();
        Assertions.assertTrue(evaluator.evaluateTokenStack(queue));
    }

    @Test
    public void testNegativeNumber() throws Exception {
        Lexer lexer = new Lexer();
        Queue<Token> queue = lexer.createTokenStack("-100 % 1");
        MathEvaluator evaluator = new MathEvaluator();
        Assertions.assertTrue(evaluator.evaluateTokenStack(queue));
    }

    @Test
    public void testLong() throws Exception {
        Lexer lexer = new Lexer();
        Queue<Token> queue = lexer.createTokenStack("100 * 1 + 30 + 33 - 13");
        MathEvaluator evaluator = new MathEvaluator();
        Assertions.assertTrue(evaluator.evaluateTokenStack(queue));
    }

    @Test
    public void testError1() throws Exception {
        Lexer lexer = new Lexer();
        Queue<Token> queue = lexer.createTokenStack("100 * + 1");
        MathEvaluator evaluator = new MathEvaluator();
        Assertions.assertFalse(evaluator.evaluateTokenStack(queue));
    }

    @Test
    public void testError2() throws Exception {
        Lexer lexer = new Lexer();
        Queue<Token> queue = lexer.createTokenStack("100 1");
        MathEvaluator evaluator = new MathEvaluator();
        Assertions.assertFalse(evaluator.evaluateTokenStack(queue));
    }

    @Test
    public void testError3() throws Exception {
        Lexer lexer = new Lexer();
        Queue<Token> queue = lexer.createTokenStack("+100 + 1");
        MathEvaluator evaluator = new MathEvaluator();
        Assertions.assertFalse(evaluator.evaluateTokenStack(queue));
    }
}
