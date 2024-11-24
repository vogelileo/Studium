import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Module {
    private String name;
    private Set<Module> futurereq;
    private int preReqCount;
    private Set<String> prereqsString;

    public String getName() {
        return this.name;
    }

    public Set<Module> getFuturereq() {
        return this.futurereq;
    }

    public int getPreReqCount(){
        return  this.preReqCount;
    }
    public Set<String> getPrereqsstring(){
        return this.prereqsString;
    }
    public void setPreReqCount(int i){
        this.preReqCount = i;
    }

    public Module decreasePreReqCount(){
        this.preReqCount--;
        return this;
    }

    public Module addFutureReq(Module m){
        this.futurereq.add(m);
        return this;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Module module = (Module) o;
        return Objects.equals(name, module.name);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(name);
    }

    public Module(String name,int preReqCount, HashSet<Module> reqs, Set<String> prereqsString){
        this.name = name;
        this.preReqCount = preReqCount;
        this.futurereq = reqs;
        this.prereqsString = prereqsString;
    }
}
