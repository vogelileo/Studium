import java.util.ArrayList;
import java.util.Objects;

public class Module {
    private String name;
    private ArrayList<String> prereqs;

    public String getName() {
        return this.name;
    }

    public ArrayList<String> getPrereqs() {
        return this.prereqs;
    }

    public void removereq(String req){
        ArrayList<String> newreq = new ArrayList<String>();
        for (String  ereq: this.prereqs){
            if(!Objects.equals(ereq, req)){
                newreq.add(ereq);
            }
        }
        this.prereqs = newreq;
    }

    public Module(String name, ArrayList<String> reqs){
        this.name = name;
        this.prereqs = reqs;
    }
}
