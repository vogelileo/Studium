import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Module {
    private String name;
    private Set<Module> prereqs;
    private Set<String> prereqsString;

    public String getName() {
        return this.name;
    }

    public Set<Module> getPrereqs() {
        return this.prereqs;
    }

    public Set<String> getPrereqsstring(){
        return this.prereqsString;
    }

    public void addreq(Module m){
        this.prereqs.add(m);
    }
    public void removereq(Module m){
        this.prereqs.remove(m);
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

    public Module(String name, HashSet<Module> reqs, Set<String> prereqsString){
        this.name = name;
        this.prereqs = reqs;
        this.prereqsString = prereqsString;
    }
}
