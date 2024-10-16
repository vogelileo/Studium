import java.util.Arrays;

public class BracketChecker {
    private String input;
    private char[] inputArr;

    public boolean validate (String s){
        this.input = s;
        this.inputArr = s.toCharArray();

        for(int i = 0; i< this.inputArr.length;i++){
            char search = '!';
            if(this.inputArr[i] == '('){
                search  = ')';
            }
            if(this.inputArr[i] == '<'){
                search  = '>';
            }
            if(this.inputArr[i] == '['){
                search  = ']';
            }
            if(this.inputArr[i] == '{'){
                search  = '}';
            }

            if(search == '!'){
                return true;
            }
            boolean found = false;
            for(int k = i; k < this.inputArr.length; i++){
                if(search == this.inputArr[k]){
                    found = true;
                }
            }
        }
    }

    public char[] getInputArr(){
        return this.inputArr;
    }

    BracketChecker(){
    }

    public static void main(String[] args) {
        BracketChecker b = new BracketChecker();
        b.validate("({)}");
        System.out.println(Arrays.toString(b.getInputArr()));
    }
}
