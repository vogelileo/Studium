public class Main{
    public static void main(String[] args){
        //Aufgabe 2
        BankAccount a = new BankAccount(23);
        a.getAccountNumber() ;
        System.out.println(a.currentValue);
        System.out.println(a.deposit(-5));
        System.out.println(a.deposit(5));
        System.out.println(a.withdraw(10));
        System.out.println(a.withdraw(-5));
        System.out.println(a.withdraw(3));


        //Aufgabe 3
        BankManager bankManager1 = new BankManager("Kate Smith", 1);
        BankManager bankManager2 = new BankManager("Uwe Schnell", 2);

        BankCustomer bankCustomer1 = new BankCustomer("Klara", "Meier", "", 22);
        bankCustomer1.bankManger = bankManager1;
        BankCustomer bankCustomer2 = new BankCustomer("Petra", "Müller", "", 22);
        bankCustomer2.bankManger = bankManager1;
        BankCustomer bankCustomer3 = new BankCustomer("Hans", "Meier", "", 22);
        bankCustomer3.bankManger = bankManager2;
        BankCustomer bankCustomer4 = new BankCustomer("Stefan", "Schmid", "", 22);
        bankCustomer4.bankManger = bankManager2;

        BankAccount bankAccount1 = new BankAccount(1,bankCustomer1);
        bankAccount1.deposit(12000);
        BankAccount bankAccount2 = new BankAccount(2,bankCustomer1);
        bankAccount1.deposit(1000);
        BankAccount bankAccount3 = new BankAccount(3,bankCustomer2);
        bankAccount1.deposit(5000);
        BankAccount bankAccount4 = new BankAccount(4,bankCustomer3);
        BankAccount bankAccount5 = new BankAccount(5,bankCustomer4);
        bankAccount1.deposit(6000);


        //Aufgabe 4

        System.out.println(bankAccount1.sameCustomer(bankAccount2));
        System.out.println(bankAccount1.getBankManager());
        System.out.println(bankCustomer1.openNewAccount(7));

    }
}
