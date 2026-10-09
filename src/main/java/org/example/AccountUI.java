
package org.example;

import java.util.List;
import java.util.Scanner;

public class AccountUI {
    private final AccountService service;
    private final Scanner scanner;

    public AccountUI(AccountService service) {
        this.service = service;
        this.scanner = new Scanner(System.in);
    }

    public void start() {
        System.out.println("Booting Global Digital Bank...");

        boolean running = true;

        while (running) {
            displayMainMenu();
            int choice = readInt("Enter your choice: ");

            try {
                switch (choice) {
                    case 1:
                        handleOpenAccount();
                        break;
                    case 2:
                        handleDeposit();
                        break;
                    case 3:
                        handleWithdraw();
                        break;
                    case 4:
                        handleTransfer();
                        break;
                    case 5:
                        handleCloseAccount();
                        break;
                    case 6:
                        handleViewAccount();
                        break;
                    case 7:
                        handleViewTransactions();
                        break;
                    case 8:
                        running = false;
                        System.out.println("Thank you! Goodbye.");
                        break;
                    default:
                        System.out.println("Invalid choice. Enter a number from 1 to 8.");
                }
            } catch (Exception exception) {
                System.out.println("ERROR: " + exception.getMessage());
            }

            if (running) {
                System.out.println();
            }
        }
    }

    private void displayMainMenu() {
        System.out.println("========================================");
        System.out.println("          GLOBAL DIGITAL BANK");
        System.out.println("========================================");
        System.out.println("1. Open Account");
        System.out.println("2. Deposit");
        System.out.println("3. Withdraw");
        System.out.println("4. Transfer");
        System.out.println("5. Close Account");
        System.out.println("6. View Account Details");
        System.out.println("7. View Transaction History");
        System.out.println("8. Exit");
        System.out.println("========================================");
    }

    private void handleOpenAccount() throws Exception {
        System.out.println("--- Open Account ---");

        String type = readString(
                "Account Type (Savings/Current/FixedDeposit/Salary): "
        ).trim().toUpperCase();

        if (type.equals("FIXEDDEPOSIT") || type.equals("FIXED DEPOSIT")) {
            type = "FIXED_DEPOSIT";
        }

        String name = readString("Name: ");
        int age = readInt("Age: ");
        double balance = readDouble("Initial Balance: ");

        int tenure = 0;
        if (type.equals("FIXED_DEPOSIT")) {
            tenure = readInt("Tenure in years: ");
        }

        IAccount account = service.openAccount(
                type, name, age, balance, tenure
        );

        System.out.println("SUCCESS: Account opened.");
        account.displayAccountInfo();

        if (account instanceof AbstractAccount) {
            int pin = readInt("Set 4-digit PIN: ");
            ((AbstractAccount) account).setPin(pin);
            System.out.println("PIN set successfully.");
        }
    }

    private void handleDeposit() throws Exception {
        System.out.println("--- Deposit ---");

        int accountNumber = readInt("Account Number: ");
        double amount = readDouble("Amount to deposit: ");

        Transaction transaction = service.deposit(accountNumber, amount);

        System.out.println("SUCCESS: Deposit completed.");
        System.out.println(transaction);
    }

    private void handleWithdraw() throws Exception {
        System.out.println("--- Withdraw ---");

        int accountNumber = readInt("Account Number: ");
        double amount = readDouble("Amount to withdraw: ");
        String pin = readString("PIN: ");

        Transaction transaction = service.withdraw(
                accountNumber, amount, pin
        );

        System.out.println("SUCCESS: Withdrawal completed.");
        System.out.println(transaction);
    }

    private void handleTransfer() throws Exception {
        System.out.println("--- Transfer ---");

        int source = readInt("Source Account Number: ");
        int destination = readInt("Destination Account Number: ");
        double amount = readDouble("Amount to transfer: ");
        String pin = readString("PIN: ");

        Transaction transaction = service.transfer(
                source, destination, amount, pin
        );

        System.out.println("SUCCESS: Transfer completed.");
        System.out.println(transaction);
    }

    private void handleCloseAccount() throws Exception {
        System.out.println("--- Close Account ---");

        int accountNumber = readInt("Account Number: ");
        String pin = readString("PIN: ");

        service.closeAccount(accountNumber, pin);

        System.out.println("SUCCESS: Account closed.");
    }

    private void handleViewAccount() throws Exception {
        System.out.println("--- Account Details ---");

        int accountNumber = readInt("Account Number: ");
        IAccount account = service.getAccount(accountNumber);

        account.displayAccountInfo();
    }

    private void handleViewTransactions() throws Exception {
        System.out.println("--- Transaction History ---");

        List<Transaction> transactions = service.getTransactionHistory();

        if (transactions.isEmpty()) {
            System.out.println("No transactions found.");
            return;
        }

        for (int i = 0; i < transactions.size(); i++) {
            System.out.println((i + 1) + ". " + transactions.get(i));
        }
    }

    private String readString(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }

    private int readInt(String prompt) {
        while (true) {
            try {
                return Integer.parseInt(readString(prompt));
            } catch (NumberFormatException exception) {
                System.out.println("Please enter a valid integer.");
            }
        }
    }

    private double readDouble(String prompt) {
        while (true) {
            try {
                return Double.parseDouble(readString(prompt));
            } catch (NumberFormatException exception) {
                System.out.println("Please enter a valid number.");
            }
        }
    }
}
