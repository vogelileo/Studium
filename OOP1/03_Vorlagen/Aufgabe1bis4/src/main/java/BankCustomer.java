public class BankCustomer{
    public String firstName;
    public String lastName;
    public String address;
    public int age;
    public BankManager bankManger;

    public void print(){
        System.out.println(this);
    }

    public BankAccount openNewAccount(long number){
        BankAccount temp = new BankAccount(number);
        temp.bankCustomer = this;
        return temp;
    }

    public BankCustomer(String firstName, String lastName, String address, int age){
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.age = age;
    }
}
