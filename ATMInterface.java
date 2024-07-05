package OasisInfobyte;

import java.util.ArrayList;
import java.util.Scanner;

    // Class to represent an individual account
    class Account {
        private String userId;
        private String pin;
        private double balance;
        private ArrayList<Transaction> transactions;

        public Account(String userId, String pin, double initialBalance) {
            this.userId = userId;
            this.pin = pin;
            this.balance = initialBalance;
            this.transactions = new ArrayList<>();
        }

        public String getUserId() {
            return userId;
        }

        public String getPin() {
            return pin;
        }

        public double getBalance() {
            return balance;
        }

        public void deposit(double amount) {
            balance += amount;
            transactions.add(new Transaction("Deposit", amount));
        }

        public void withdraw(double amount) {
            if (amount <= balance) {
                balance -= amount;
                transactions.add(new Transaction("Withdraw", amount));
            } else {
                System.out.println("Insufficient funds.");
            }
        }

        public void transfer(Account toAccount, double amount) {
            if (amount <= balance) {
                balance -= amount;
                toAccount.deposit(amount);
                transactions.add(new Transaction("Transfer to " + toAccount.getUserId(), amount));
            } else {
                System.out.println("Insufficient funds.");
            }
        }

        public void printTransactionHistory() {
            System.out.println("Transaction History:");
            for (Transaction transaction : transactions) {
                System.out.println(transaction);
            }
        }
    }

    // Class to represent an individual transaction
    class Transaction {
        private String type;
        private double amount;

        public Transaction(String type, double amount) {
            this.type = type;
            this.amount = amount;
        }

        @Override
        public String toString() {
            return type + ": $" + amount;
        }
    }

    // Class to handle the ATM operations
    class ATM {
        private Account currentAccount;
        private Scanner scanner;

        public ATM(Account account) {
            this.currentAccount = account;
            this.scanner = new Scanner(System.in);
        }

        public void start() {
            System.out.println("Welcome to the ATM!");
            while (true) {
                showMenu();
                int choice = scanner.nextInt();
                scanner.nextLine();  // consume newline
                switch (choice) {
                    case 1:
                        currentAccount.printTransactionHistory();
                        break;
                    case 2:
                        handleWithdraw();
                        break;
                    case 3:
                        handleDeposit();
                        break;
                    case 4:
                        handleTransfer();
                        break;
                    case 5:
                        System.out.println("Thank you for using the ATM. Goodbye!");
                        return;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            }
        }

        private void showMenu() {
            System.out.println("\nATM Menu:");
            System.out.println("1. Transaction History");
            System.out.println("2. Withdraw");
            System.out.println("3. Deposit");
            System.out.println("4. Transfer");
            System.out.println("5. Quit");
            System.out.print("Enter your choice: ");
        }

        private void handleWithdraw() {
            System.out.print("Enter amount to withdraw: ");
            double amount = scanner.nextDouble();
            currentAccount.withdraw(amount);
        }

        private void handleDeposit() {
            System.out.print("Enter amount to deposit: ");
            double amount = scanner.nextDouble();
            currentAccount.deposit(amount);
        }

        private void handleTransfer() {
            System.out.print("Enter recipient user ID: ");
            String recipientId = scanner.nextLine();
            System.out.print("Enter amount to transfer: ");
            double amount = scanner.nextDouble();
            scanner.nextLine();  // consume newline

            // In a real application, you would lookup the recipient account here
            Account recipientAccount = new Account(recipientId, "dummyPin", 0);  // Replace with actual account lookup
            currentAccount.transfer(recipientAccount, amount);
        }
    }

    // Main class to initialize the application
     public class ATMInterface{
        public static void main(String[] args) {
            Account account = new Account("user1", "1234", 1000.0);
            ATM atm = new ATM(account);
            atm.start();
        }
    }


