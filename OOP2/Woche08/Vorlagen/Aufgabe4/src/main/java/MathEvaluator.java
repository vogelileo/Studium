import tokens.FixToken;
import tokens.NumberToken;
import tokens.Tag;
import tokens.Token;

public class MathEvaluator {

    public boolean evaluateTokenStack(Queue<Token> tokenQueue) throws Exception {
        //TODO implement
       if(tokenQueue.isEmpty()){
           throw new EmptyQueueException("Empty Queue");
       }
        Token lastToken = tokenQueue.dequeue();
       //first elem is just valid if it is a minus
       if(lastToken instanceof FixToken && ((FixToken) lastToken).getToken() != Tag.Minus ){
           return false;
       }

        while(!tokenQueue.isEmpty()){
            Token currentToken = tokenQueue.dequeue();
            System.out.println("-----");
            System.out.println(lastToken.getClass());
            System.out.println(currentToken.getClass());

            if(lastToken.getClass() == currentToken.getClass()){
               return false;
           }
            lastToken = currentToken;

        }
        //last Token can never be a FixToken
        if(lastToken instanceof FixToken){
            return false;
        }
        return true;
    }
}
