// OASIS INFOBYTE 
//Tak 3 of Java Development 
// Making the ATM machine Interface using java and also shows the working ...
import java.util.Scanner;

class BankAccount { // Created a class named BankAcoount
    private double balance;
    private String transactionHistory;

    public BankAccount(double initialBalance) {
        this.balance = initialBalance;// putting the given initial balance into the balance
        this.transactionHistory = "Account created with initial balance: " + initialBalance + "\n";
    }

    public double getBalance() {
        return balance;
    }

    public void deposit(double amount) {// using this function we can deposit the money
        if (amount > 0) {
            balance += amount;
            transactionHistory += "Deposited: " + amount + ", New balance: " + balance + "\n";
            System.out.println("Deposit successful. New balance is: " + balance);
        } else {
            System.out.println("Invalid amount for deposit.");
        }
    }

    public void withdraw(double amount) {// and by using it we can withdraw
        if (amount > 0 && amount <= balance) {
            balance -= amount; // it helps us to withdraw
            transactionHistory += "Withdrew: " + amount + ", New balance: " + balance + "\n";
            System.out.println("Withdrawal successful. New balance is: " + balance);
        } else if (amount > balance) {
            System.out.println("Insufficient funds for withdrawal.");// if amount will be less then tha balance then thi message will be shown
        } else {
            System.out.println("Invalid amount for withdrawal.");
        }
    }

    public void transfer(BankAccount recipient, double amount) {
        if (amount > 0 && amount <= balance) {
            this.balance -= amount;
            recipient.deposit(amount);
            transactionHistory += "Transferred: " + amount + " to another account. New balance: " + balance + "\n";
            System.out.println("Transfer successful. New balance is: " + balance);
        } else if (amount > balance) {
            System.out.println("Insufficient funds for transfer.");
        } else {
            System.out.println("Invalid amount for transfer.");
        }
    }

    public void printTransactionHistory() {
        System.out.println("Transaction History:");
        System.out.println(transactionHistory);
    }
}

class ATM {
    private BankAccount account;
    private BankAccount recipientAccount;
    private Scanner scanner;

    public ATM(BankAccount account, BankAccount recipientAccount) {
        this.account = account;
        this.recipientAccount = recipientAccount;
        this.scanner = new Scanner(System.in);
    }

    public void showMenu() {
        System.out.println("1. Transaction History");
        System.out.println("2. Withdraw");
        System.out.println("3. Deposit");
        System.out.println("4. Transfer");
        System.out.println("5. Quit");
    }

    public void run() {
        int choice;
        do {
            showMenu();
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    account.printTransactionHistory();
                    break;
                case 2:
                    Withdrawal();
                    break;
                case 3:
                    Deposit();
                    break;
                case 4:
                    Transfer();
                    break;
                case 5:
                    System.out.println("Thank you for using the ATM.");
                    break;
                default:
                    System.out.println("Invalid choice. Please select a valid option.");
            }
        } while (choice != 5);
    }

    private void Deposit() {
        System.out.print("Enter the amount to deposit: ");
        double amount = scanner.nextDouble();
        account.deposit(amount);
    }

    private void Withdrawal() {
        System.out.print("Enter the amount to withdraw: ");
        double amount = scanner.nextDouble();
        account.withdraw(amount);
    }

    private void Transfer() {
        System.out.print("Enter the amount to transfer: ");
        double amount = scanner.nextDouble();
        account.transfer(recipientAccount, amount);
    }
}

public class Task3 {
    public static void main(String[] args) {
        System.out.println("Welcome to the ATM!");
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter your four-digit pin: ");
        int pin = sc.nextInt();
        BankAccount userAccount = new BankAccount(100000.0);
        BankAccount recipientAccount = new BankAccount(50000.0);
        ATM atm = new ATM(userAccount, recipientAccount);
        atm.run();
    }
}
// ThankYou
