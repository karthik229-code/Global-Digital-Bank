package org.example;

public class TestAccountEnhanced {

    public static void displayAccount(Account account) {
        System.out.printf(
                "Account#%d | %s (%d yrs) | %s | ₹%.1f | %s | PIN: %s%n",
                account.getAccountNumber(),
                account.getName(),
                account.getAge(),
                account.getAccountType(),
                account.getBalance(),
                account.getStatus(),
                account.hasPin() ? "Yes" : "No"
        );
    }

    public static void main(String[] args) {

        System.out.println("=".repeat(80));
        System.out.println("        ENHANCED ACCOUNT TEST");
        System.out.println("=".repeat(80));


        // Test 1: Valid Account Creation
        System.out.println("\n>>> Test 1: Valid Account Creation");

        try {
            Account account1 =
                    new Account(1001, "John Doe", 25, 1000, "Savings");

            System.out.println("Account created successfully");
            displayAccount(account1);

        } catch (IllegalArgumentException e) {
            System.out.println("FAILED: " + e.getMessage());
        }


        // Test 2: Invalid Age
        System.out.println("\n>>> Test 2: Invalid Age");

        try {
            Account account2 =
                    new Account(1002, "Young Kid", 16, 500, "Savings");

            System.out.println("FAILED: Account should not have been created");

        } catch (IllegalArgumentException e) {
            System.out.println("PASS: " + e.getMessage());
        }


        // Test 3: Invalid Account Type
        System.out.println("\n>>> Test 3: Invalid Account Type");

        try {
            Account account3 =
                    new Account(1003, "Test User", 25, 1000, "Invalid");

            System.out.println("FAILED: Account should not have been created");

        } catch (IllegalArgumentException e) {
            System.out.println("PASS: " + e.getMessage());
        }


        // Test 4: Minimum Balance on Creation
        System.out.println("\n>>> Test 4: Minimum Balance Validation");

        try {
            Account account4 =
                    new Account(1004, "Bob Wilson", 25, 300, "Savings");

            System.out.println("FAILED: Account should not have been created");

        } catch (IllegalArgumentException e) {
            System.out.println("PASS: " + e.getMessage());
        }


        // Test 5: Deposit
        System.out.println("\n>>> Test 5: Deposit");

        Account account5 =
                new Account(1005, "Alice Brown", 30, 1000, "Savings");

        displayAccount(account5);

        try {
            account5.deposit(500);

            System.out.println("Deposit ₹500: SUCCESS");
            System.out.println("New Balance: ₹" + account5.getBalance());

        } catch (AccountException e) {
            System.out.println("FAILED: " + e.getMessage());
        }


        // Test 6: Invalid Deposit
        System.out.println("\n>>> Test 6: Invalid Deposit");

        try {
            account5.deposit(-100);

            System.out.println("FAILED: Invalid deposit was accepted");

        } catch (InvalidAmountException e) {
            System.out.println("PASS: " + e.getMessage());
        } catch (AccountException e) {
            System.out.println("FAILED: Unexpected exception: "
                    + e.getMessage());
        }


        // Test 7: PIN Protection
        System.out.println("\n>>> Test 7: PIN Protection");

        try {
            account5.setPin(1234);
            System.out.println("PIN set successfully");

        } catch (IllegalArgumentException e) {
            System.out.println("FAILED: " + e.getMessage());
        }

        try {
            account5.withdraw(200, 1234);

            System.out.println("Correct PIN withdrawal: SUCCESS");
            System.out.println("New Balance: ₹" + account5.getBalance());

        } catch (AccountException e) {
            System.out.println("FAILED: " + e.getMessage());
        }


        // Test 8: Incorrect PIN
        System.out.println("\n>>> Test 8: Incorrect PIN");

        try {
            account5.withdraw(100, 9999);

            System.out.println("FAILED: Incorrect PIN accepted");

        } catch (InvalidPinException e) {
            System.out.println("PASS: " + e.getMessage());

        } catch (AccountException e) {
            System.out.println("FAILED: Unexpected exception: "
                    + e.getMessage());
        }


        // Test 9: Insufficient Balance
        System.out.println("\n>>> Test 9: Insufficient Balance");

        try {
            account5.withdraw(5000, 1234);

            System.out.println("FAILED: Withdrawal should not succeed");

        } catch (InsufficientBalanceException e) {
            System.out.println("PASS: " + e.getMessage());

        } catch (AccountException e) {
            System.out.println("FAILED: Unexpected exception: "
                    + e.getMessage());
        }


        // Test 10: Minimum Balance Violation
        System.out.println("\n>>> Test 10: Minimum Balance Violation");

        try {
            account5.withdraw(700, 1234);

            System.out.println("FAILED: Minimum balance should be protected");

        } catch (MinimumBalanceViolationException e) {
            System.out.println("PASS: " + e.getMessage());

        } catch (AccountException e) {
            System.out.println("FAILED: Unexpected exception: "
                    + e.getMessage());
        }


        // Test 11: Close Account
        System.out.println("\n>>> Test 11: Account Status Management");

        try {
            account5.closeAccount();

            System.out.println("Account closed successfully");
            displayAccount(account5);

        } catch (IllegalStateException e) {
            System.out.println("FAILED: " + e.getMessage());
        }


        // Test 12: Operation on Inactive Account
        System.out.println("\n>>> Test 12: Inactive Account");

        try {
            account5.deposit(500);

            System.out.println("FAILED: Deposit should not be allowed");

        } catch (InactiveAccountException e) {
            System.out.println("PASS: " + e.getMessage());

        } catch (AccountException e) {
            System.out.println("FAILED: Unexpected exception: "
                    + e.getMessage());
        }


        // Test 13: Reopen Account
        System.out.println("\n>>> Test 13: Reopen Account");

        try {
            account5.reopenAccount();

            System.out.println("Account reopened successfully");
            displayAccount(account5);

        } catch (IllegalStateException e) {
            System.out.println("FAILED: " + e.getMessage());
        }


        // Test 14: Invalid PIN
        System.out.println("\n>>> Test 14: Invalid PIN");

        try {
            account5.setPin(123);

            System.out.println("FAILED: Invalid PIN was accepted");

        } catch (IllegalArgumentException e) {
            System.out.println("PASS: " + e.getMessage());
        }


        System.out.println("\n" + "=".repeat(80));
        System.out.println("        ENHANCED TEST COMPLETED");
        System.out.println("=".repeat(80));
    }
}