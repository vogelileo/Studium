public class BankAccount {
    private final long accountNumber;
    public double currentValue;
    public BankCustomer bankCustomer;

    public double getAccountNumber(){
        return this.accountNumber;
    }

    public boolean deposit(int amount){
        if(amount >= 0){
            this.currentValue += amount;
            return true;
        }
        return false;
    }

    public boolean withdraw(int amount){
        if(this.currentValue >= amount && amount >= 0){
            this.currentValue -= amount;
            return true;
        }
        return false;

    }

    public boolean sameCustomer(BankAccount other){
        return this.bankCustomer == other.bankCustomer;
    }

    public BankManager getBankManager(){
        return this.bankCustomer.bankManger;
    }

    public void print(){
        System.out.println(this);
    }

    public BankAccount(long accountNumber) {
       this(accountNumber, new BankCustomer("","","",0));
    }

    public BankAccount(long accountNumber, BankCustomer bankCustomer) {
        this.accountNumber = accountNumber;
        this.bankCustomer = bankCustomer;
        this.currentValue = 0;
    }

}


