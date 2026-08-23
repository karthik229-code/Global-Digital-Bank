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

        System.out.println(">>> 2. Deposit Money");

        System.out.println("Depositing ₹500.0 : " + account1.deposit(500));
        System.out.println("New Balance : " + account1.getBalance());

        System.out.println("Depositing ₹-100.0 : " + account1.deposit(-100));
        System.out.println("New Balance : " + account1.getBalance());

        System.out.println(">>> 3. Withdraw Money");

        System.out.println("Withdrawing ₹200.0 : " +
                (account1.withdraw(200) ? "SUCCESS" : "FAILURE"));
        System.out.println("New Balance : " + account1.getBalance());

        System.out.println("Withdrawing ₹2000.0 : " +
                (account1.withdraw(2000) ? "SUCCESS" : "FAILURE"));
        System.out.println("Current Balance : ₹" + account1.getBalance());

        System.out.println(">>> 4. Creating Another Account");

        Account account2 = new Account(1002, "Jane", 30, 2000, "Current");
        displayAccount(account2);

        System.out.println(">>> 5. All Accounts");

        displayAccount(account1);
        displayAccount(account2);

        System.out.println("==================================================");
        System.out.println("TEST COMPLETED!");
        System.out.println("==================================================");
    }
}