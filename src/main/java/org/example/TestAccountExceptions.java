package org.example;

public class TestAccountExceptions {

    public static void main(String[] args) {

        System.out.println("=".repeat(70));
        System.out.println("        ACCOUNT EXCEPTION TESTS");
        System.out.println("=".repeat(70));


        // Test 1: Valid Account Creation
        System.out.println("\n>>> Test 1: Valid Account Creation");

        try {
            Account account1 =
                    new Account(1001, "John Doe", 25, 1000, "Savings");

            System.out.println("PASS: Account created successfully");
            System.out.println("Account Number: " + account1.getAccountNumber());
            System.out.println("Name: " + account1.getName());
            System.out.println("Balance: ₹" + account1.getBalance());

        } catch (IllegalArgumentException e) {
            System.out.println("FAIL: " + e.getMessage());
        }


        // Test 2: Invalid Age
        System.out.println("\n>>> Test 2: Invalid Age");

        try {
            Account account2 =
                    new Account(1002, "Young Kid", 16, 1000, "Savings");

            System.out.println("FAIL: Account should not have been created");

        } catch (IllegalArgumentException e) {
            System.out.println("PASS: " + e.getMessage());
        }


        // Test 3: Invalid Account Type
        System.out.println("\n>>> Test 3: Invalid Account Type");

        try {
            Account account3 =
                    new Account(1003, "Test User", 25, 1000, "Invalid");

            System.out.println("FAIL: Account should not have been created");

        } catch (IllegalArgumentException e) {
            System.out.println("PASS: " + e.getMessage());
        }


        // Test 4: Minimum Balance on Creation
        System.out.println("\n>>> Test 4: Minimum Balance Validation");

        try {
            Account account4 =
                    new Account(1004, "Bob Wilson", 25, 300, "Savings");

            System.out.println("FAIL: Account should not have been created");

        } catch (IllegalArgumentException e) {
            System.out.println("PASS: " + e.getMessage());
        }


        // Test 5: Deposit
        System.out.println("\n>>> Test 5: Valid Deposit");

        Account account5 =
                new Account(1005, "Alice Brown", 30, 1000, "Savings");

        try {
            account5.deposit(500);

            System.out.println("PASS: Deposit successful");
            System.out.println("New Balance: ₹" + account5.getBalance());

        } catch (AccountException e) {
            System.out.println("FAIL: " + e.getMessage());
        }


        // Test 6: Invalid Deposit
        System.out.println("\n>>> Test 6: Invalid Deposit");

        try {
            account5.deposit(-100);

            System.out.println("FAIL: Invalid deposit was accepted");

        } catch (InvalidAmountException e) {
            System.out.println("PASS: " + e.getMessage());

        } catch (AccountException e) {
            System.out.println("FAIL: Unexpected exception: "
                    + e.getMessage());
        }


        // Test 7: PIN Protection
        System.out.println("\n>>> Test 7: PIN Protection");

        try {
            account5.setPin(1234);
            System.out.println("PASS: PIN set successfully");

        } catch (IllegalArgumentException e) {
            System.out.println("FAIL: " + e.getMessage());
        }

        try {
            account5.withdraw(200, 1234);

            System.out.println("PASS: Correct PIN withdrawal successful");
            System.out.println("New Balance: ₹" + account5.getBalance());

        } catch (AccountException e) {
            System.out.println("FAIL: " + e.getMessage());
        }


        // Test 8: Incorrect PIN
        System.out.println("\n>>> Test 8: Incorrect PIN");

        try {
            account5.withdraw(100, 9999);

            System.out.println("FAIL: Incorrect PIN was accepted");

        } catch (InvalidPinException e) {
            System.out.println("PASS: " + e.getMessage());

        } catch (AccountException e) {
            System.out.println("FAIL: Unexpected exception: "
                    + e.getMessage());
        }


        // Test 9: Insufficient Balance
        System.out.println("\n>>> Test 9: Insufficient Balance");

        try {
            account5.withdraw(5000, 1234);

            System.out.println("FAIL: Withdrawal should not succeed");

        } catch (InsufficientBalanceException e) {
            System.out.println("PASS: " + e.getMessage());

        } catch (AccountException e) {
            System.out.println("FAIL: Unexpected exception: "
                    + e.getMessage());
        }


        // Test 10: Minimum Balance Violation
        System.out.println("\n>>> Test 10: Minimum Balance Violation");

        try {
            account5.withdraw(900, 1234);

            System.out.println("FAIL: Minimum balance should be protected");

        } catch (MinimumBalanceViolationException e) {
            System.out.println("PASS: " + e.getMessage());

        } catch (AccountException e) {
            System.out.println("FAIL: Unexpected exception: "
                    + e.getMessage());
        }


        // Test 11: Close Account
        System.out.println("\n>>> Test 11: Close Account");

        try {
            account5.closeAccount();

            System.out.println("PASS: Account closed successfully");
            System.out.println("Status: " + account5.getStatus());

        } catch (IllegalStateException e) {
            System.out.println("FAIL: " + e.getMessage());
        }


        // Test 12: Operation on Inactive Account
        System.out.println("\n>>> Test 12: Inactive Account");

        try {
            account5.deposit(500);

            System.out.println("FAIL: Deposit should not be allowed");

        } catch (InactiveAccountException e) {
            System.out.println("PASS: " + e.getMessage());

        } catch (AccountException e) {
            System.out.println("FAIL: Unexpected exception: "
                    + e.getMessage());
        }


        // Test 13: Reopen Account
        System.out.println("\n>>> Test 13: Reopen Account");

        try {
            account5.reopenAccount();

            System.out.println("PASS: Account reopened successfully");
            System.out.println("Status: " + account5.getStatus());

        } catch (IllegalStateException e) {
            System.out.println("FAIL: " + e.getMessage());
        }


        // Test 14: Invalid PIN
        System.out.println("\n>>> Test 14: Invalid PIN");

        try {
            account5.setPin(123);

            System.out.println("FAIL: Invalid PIN was accepted");

        } catch (IllegalArgumentException e) {
            System.out.println("PASS: " + e.getMessage());
        }


        // Test 15: PIN Not Set
        System.out.println("\n>>> Test 15: Withdrawal Without PIN");

        try {
            Account account6 =
                    new Account(1006, "No Pin User", 25, 1000, "Savings");

            account6.withdraw(100, 1234);

            System.out.println("FAIL: Withdrawal should not be allowed");

        } catch (InvalidPinException e) {
            System.out.println("PASS: " + e.getMessage());

        } catch (AccountException e) {
            System.out.println("FAIL: Unexpected exception: "
                    + e.getMessage());
        }


        System.out.println("\n" + "=".repeat(70));
        System.out.println("        ALL EXCEPTION TESTS COMPLETED");
        System.out.println("=".repeat(70));
    }
}