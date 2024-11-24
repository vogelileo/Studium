import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Module {
    private String name;
    private Set<Module> futurereq;
    private Set<Module> prereqs;
    private Set<String> prereqsString;

    public String getName() {
        return this.name;
    }

    public Set<Module> getFuturereq() {
        return this.futurereq;
    }

    public Set<Module> getPrereqs(){
        return  this.prereqs;
    }
    public Set<String> getPrereqsstring(){
        return this.prereqsString;
    }

    public Module addreq(Module m){
        this.prereqs.add(m);
        return this;
    }

    public Module addFutureReq(Module m){
        this.futurereq.add(m);
        return this;
    }
    public Module removereq(Module m){
        this.prereqs.remove(m);
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

    public Module(String name, HashSet<Module> prereqs, HashSet<Module> reqs, Set<String> prereqsString){
        this.name = name;
        this.prereqs = prereqs;
        this.futurereq = reqs;
        this.prereqsString = prereqsString;
    }
}
