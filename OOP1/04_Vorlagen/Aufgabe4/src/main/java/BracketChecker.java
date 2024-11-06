import java.util.Arrays;

public class BracketChecker {
    private String input;
    private char[] inputArr;

    public boolean validate (String s){
        this.input = s;
        this.inputArr = s.toCharArray();
        int indexFound = -1;
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
            for(int k = i; k < this.inputArr.length; k++){
                if(search == this.inputArr[k]){
                    indexFound = k;
                }
            }

            //check if string between
            //recurse with string between
        }
        if(indexFound < 0 ){
            return false;
        }

        if(indexFound == 1){
            return true;
        }

        return this.validate(String.valueOf(Arrays.copyOfRange(this.inputArr, 1, indexFound -1)));

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
