import java.util.List;
import java.util.function.Predicate;

public class Functions {
    public void search(List<Person> input, Predicate<Person> criteria){
        var it = input.iterator();
         while(it.hasNext()){
            if(!criteria.test(it.next())){
                it.remove();
            }
        }
    }
}
