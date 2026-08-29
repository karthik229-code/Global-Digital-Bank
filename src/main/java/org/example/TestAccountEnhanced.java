package org.example;

public class TestAccountEnhanced {

    public static void displayAccount(Account account) {
        System.out.printf("Account #%d | %s (%d yrs) | %s | ₹%.1f | %s | PIN: %s%n",
                account.getAccountNumber(),
                account.getName(),
                account.getAge(),
                account.getAccountType(),
                account.getBalance(),
                account.getStatus(),
                (account.hasPin() ? "Yes" : "No"));
    }

    public static void main(String[] args) {

        System.out.println("=".repeat(80));
        System.out.println("    ENHANCED ACCOUNT TEST (BOOLEAN RETURNS)");
        System.out.println("=".repeat(80));

        System.out.println(">>> Test 1: Valid Account Creation");

        Account account1 = new Account(1001, "John Doe", 25, 1000, "Savings");

        displayAccount(account1);

        System.out.println("\n>>> Test 2: Invalid Age (under 18)");
        System.out.println("Creating account with age 16");

        Account account2 = new Account(1002, "Young Kid", 16, 500, "Savings");

        System.out.println("Age auto-corrected to: " + account2.getAge());
        displayAccount(account2);

        System.out.println("\n>>> Test 3: Invalid Account Type");
        System.out.println("Creating account with type \"Invalid\"");

        Account account3 = new Account(1003, "Test User", 25, 500, "Invalid");

        System.out.println("Account type defaulted to: " + account3.getAccountType());
        displayAccount(account3);

        System.out.println("\n>>> Test 4: Minimum Balance Enforcement on Creation");
        System.out.println("Creating Savings account with ₹300 (below minimum)");

        Account account4 = new Account(1004, "Bob Wilson", 25, 300, "Savings");

        System.out.println("Balance auto-corrected to minimum: ₹" + account4.getBalance());
        displayAccount(account4);

        System.out.println("\n>>> Test 5: Withdrawal with Minimum Balance");

        Account account5 = new Account(1005, "Alice Brown", 30, 1000, "Current");
        account5.setPin(1111);

        System.out.print("Initial: ");
        displayAccount(account5);

        System.out.println("Withdrawing ₹200.0: "
                + (account5.withdraw(200, 1111) ? "SUCCESS" : "FAILED"));

        System.out.println("New Balance: ₹" + account5.getBalance());

        System.out.print("After Withdrawal: ");
        displayAccount(account5);

        System.out.println("Withdrawing ₹900.0 (minimum balance violation): "
                + (account5.withdraw(900, 1111) ? "SUCCESS" : "FAILED"));

        System.out.println("Current Balance: ₹" + account5.getBalance());

        System.out.println("\n>>> Test 6: Account Status Management");

        Account account6 = new Account(1006, "Charlie Green", 35, 2000, "Savings");

        System.out.print("Initial: ");
        displayAccount(account6);

        System.out.println("Closing Account: "
                + (account6.closeAccount() ? "SUCCESS" : "FAILED"));

        System.out.print("After Close: ");
        displayAccount(account6);

        System.out.println("Depositing ₹500.0 to closed account: "
                + (account6.deposit(500) ? "SUCCESS" : "FAILED (Account inactive)"));

        System.out.println("Reopening Account: "
                + (account6.reopenAccount() ? "SUCCESS" : "FAILED"));

        System.out.print("After Reopen: ");
        displayAccount(account6);

        System.out.println("\n>>> Test 7: PIN Protection");

        Account account7 = new Account(1007, "Diana Prince", 28, 1500, "Savings");

        System.out.println("Setting PIN 1234: "
                + (account7.setPin(1234) ? "SUCCESS" : "FAILED"));

        System.out.println("Withdrawing ₹200.0 with correct PIN (1234): "
                + (account7.withdraw(200, 1234) ? "SUCCESS" : "FAILED"));

        System.out.println("New Balance: ₹" + account7.getBalance());

        System.out.println("Withdrawing ₹100.0 with incorrect PIN (9999): "
                + (account7.withdraw(100, 9999) ? "SUCCESS" : "FAILED (Incorrect PIN)"));

        System.out.println("Withdrawing ₹100.0 with PIN not set: "
                + (account1.withdraw(100, 1223) ? "SUCCESS" : "FAILED (PIN not set)"));

        System.out.println("\n>>> Test 8: All Accounts Summary");

        displayAccount(account1);
        displayAccount(account2);
        displayAccount(account3);
        displayAccount(account4);
        displayAccount(account5);
        displayAccount(account6);
        displayAccount(account7);

        System.out.println();
        System.out.println("=".repeat(80));
        System.out.println("    ENHANCED TEST COMPLETED!");
        System.out.println("=".repeat(80));
    }
}