public class BankManager {
    public String name;
    public int managerId;

    public void print(){
        System.out.println(this);
    }
    public BankManager(String name, int managerId){
        this.name = name;
        this.managerId = managerId;
    }
}
