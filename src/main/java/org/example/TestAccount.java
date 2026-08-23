package org.example;

public class TestAccount {

    public static void displayAccount(Account account) {
        System.out.printf("#%d | %s (%d yrs) | %s | ₹%.1f | %s\n",
                account.getAccountNumber(),
                account.getName(),
                account.getAge(),
                account.getAccountType(),
                account.getBalance(),
                account.getStatus());
    }

    public static void main(String[] args) {

        System.out.println(">>> 1. Creating Account");

        Account account1 = new Account(1001, "John", 34, 1000, "Savings");

        System.out.println("Account Created");
        displayAccount(account1);

        System.out.println("\n>>> 2. Deposit Money");

        System.out.println("Depositing ₹500.0 : " + account1.deposit(500));
        System.out.println("New Balance : ₹" + account1.getBalance());

        System.out.println("Depositing ₹-100.0 : " + account1.deposit(-100));
        System.out.println("New Balance : ₹" + account1.getBalance());

        System.out.println("\n>>> 3. Setting PIN");

        System.out.println("Setting PIN 1234 : " + account1.setPin(1234));
        System.out.println("Has PIN : " + account1.hasPin());

        System.out.println("Setting invalid PIN 123 : " + account1.setPin(123));

        System.out.println("\n>>> 4. Withdraw Money");

        System.out.println("Withdrawing ₹200 with correct PIN : " +
                (account1.withdraw(200, 1234) ? "SUCCESS" : "FAILURE"));
        System.out.println("New Balance : ₹" + account1.getBalance());

        System.out.println("Withdrawing ₹200 with wrong PIN : " +
                (account1.withdraw(200, 9999) ? "SUCCESS" : "FAILURE"));
        System.out.println("Current Balance : ₹" + account1.getBalance());

        System.out.println("\n>>> 5. Minimum Balance Test");

        System.out.println("Withdrawing ₹300 : " +
                (account1.withdraw(300, 1234) ? "SUCCESS" : "FAILURE"));
        System.out.println("Current Balance : ₹" + account1.getBalance());

        System.out.println("Withdrawing ₹100 : " +
                (account1.withdraw(100, 1234) ? "SUCCESS" : "FAILURE"));
        System.out.println("Current Balance : ₹" + account1.getBalance());

        System.out.println("\n>>> 6. Creating Another Account");

        Account account2 = new Account(1002, "Jane", 30, 2000, "Current");

        displayAccount(account2);

        System.out.println("\n>>> 7. Account Status");

        System.out.println("Closing Account : " +
                (account1.closeAccount() ? "SUCCESS" : "FAILURE"));
        displayAccount(account1);

        System.out.println("Depositing ₹500 while inactive : " +
                (account1.deposit(500) ? "SUCCESS" : "FAILURE"));

        System.out.println("Withdrawing ₹100 while inactive : " +
                (account1.withdraw(100, 1234) ? "SUCCESS" : "FAILURE"));

        System.out.println("\n>>> 8. Reopening Account");

        System.out.println("Reopening Account : " +
                (account1.reopenAccount() ? "SUCCESS" : "FAILURE"));
        displayAccount(account1);

        System.out.println("Depositing ₹500 after reopening : " +
                (account1.deposit(500) ? "SUCCESS" : "FAILURE"));

        System.out.println("\n>>> 9. All Accounts");

        displayAccount(account1);
        displayAccount(account2);

        System.out.println("\n==================================================");
        System.out.println("TEST COMPLETED!");
        System.out.println("==================================================");
    }
}