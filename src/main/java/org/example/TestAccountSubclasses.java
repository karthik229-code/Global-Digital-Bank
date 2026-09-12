package org.example;

public class TestAccountSubclasses {

    public static void main(String[] args) {

        System.out.println("============================================================");
        System.out.println("  ACCOUNT SUBCLASSES TEST (SAVINGS & CURRENT)");
        System.out.println("============================================================");


        // ============================================================
        // TEST 1: Creating Accounts
        // ============================================================

        System.out.println(">>> Test 1: Creating Accounts");

        SavingsAccount savingsAccount =
                new SavingsAccount(
                        1001,
                        "John Doe",
                        25,
                        1000,
                        500,
                        4.0
                );

        CurrentAccount currentAccount =
                new CurrentAccount(
                        1002,
                        "Jane Smith",
                        30,
                        2000,
                        5000
                );

        System.out.println(
                "Savings Account: Account #" +
                        savingsAccount.getAccountNumber() +
                        " | " + savingsAccount.getName() +
                        " (" + savingsAccount.getAge() + " yrs)" +
                        " | " + savingsAccount.getAccountType() +
                        " | ₹" + savingsAccount.getBalance() +
                        " | " + savingsAccount.getStatus() +
                        " | PIN: " +
                        (savingsAccount.hasPin() ? "Yes" : "No")
        );

        System.out.println(
                "Current Account: Account #" +
                        currentAccount.getAccountNumber() +
                        " | " + currentAccount.getName() +
                        " (" + currentAccount.getAge() + " yrs)" +
                        " | " + currentAccount.getAccountType() +
                        " | ₹" + currentAccount.getBalance() +
                        " | " + currentAccount.getStatus() +
                        " | PIN: " +
                        (currentAccount.hasPin() ? "Yes" : "No")
        );


        // ============================================================
        // TEST 2: Account Type and Minimum Balance
        // ============================================================

        System.out.println("\n>>> Test 2: Account Type and Minimum Balance");

        System.out.println(
                "Savings Account - Type: " +
                        savingsAccount.getAccountType() +
                        ", Minimum Balance: ₹" +
                        savingsAccount.getMinBalance()
        );

        System.out.println(
                "Current Account - Type: " +
                        currentAccount.getAccountType() +
                        ", Minimum Balance: ₹1000.0"
        );


        // ============================================================
        // TEST 3: Savings Account - Interest Calculation
        // ============================================================

        System.out.println("\n>>> Test 3: Savings Account - Interest Calculation");

        System.out.println(
                "Savings Account: Account #" +
                        savingsAccount.getAccountNumber() +
                        " | " + savingsAccount.getName() +
                        " (" + savingsAccount.getAge() + " yrs)" +
                        " | " + savingsAccount.getAccountType() +
                        " | ₹" + savingsAccount.getBalance() +
                        " | " + savingsAccount.getStatus()
        );

        System.out.println(
                "Interest Rate: " +
                        savingsAccount.getInterestRate() +
                        "% per annum"
        );

        double interest1Year =
                savingsAccount.calculateInterest(1);

        double interest2Years =
                savingsAccount.calculateInterest(2);

        double interest5Years =
                savingsAccount.calculateInterest(5);

        System.out.println(
                "Interest for 1 year: ₹" +
                        interest1Year
        );

        System.out.println(
                "Interest for 2 years: ₹" +
                        interest2Years
        );

        System.out.println(
                "Interest for 5 years: ₹" +
                        interest5Years
        );

        System.out.println(
                "After 2 years with interest: Balance would be ₹" +
                        (savingsAccount.getBalance() + interest2Years)
        );


        // ============================================================
        // TEST 4: Current Account - Overdraft Feature
        // ============================================================

        System.out.println("\n>>> Test 4: Current Account - Overdraft Feature");

        System.out.println(
                "Current Account: Account #" +
                        currentAccount.getAccountNumber() +
                        " | " + currentAccount.getName() +
                        " (" + currentAccount.getAge() + " yrs)" +
                        " | " + currentAccount.getAccountType() +
                        " | ₹" + currentAccount.getBalance() +
                        " | " + currentAccount.getStatus()
        );

        System.out.println(
                "Overdraft Limit: ₹" +
                        currentAccount.getOverdraftLimit()
        );

        System.out.println(
                "Available Overdraft: ₹" +
                        currentAccount.getAvailableOverdraft()
        );

        System.out.println(
                "Overdraft Used: ₹" +
                        currentAccount.getOverdraftUsed()
        );

        System.out.println(
                "Is Using Overdraft: " +
                        currentAccount.isUsingOverdraft()
        );


        try {

            currentAccount.setPin(1234);

            System.out.println(
                    "Withdrawing ₹1500.0 (goes below minimum balance of ₹1000)"
            );

            System.out.println(
                    "Balance before: ₹" +
                            currentAccount.getBalance()
            );

            currentAccount.withdrawWithOverdraft(1500, 1234);

            System.out.println(
                    "Withdrawing: ₹1500.0 - SUCCESS"
            );

            System.out.println(
                    "Balance after: ₹" +
                            currentAccount.getBalance()
            );

            System.out.println(
                    "Overdraft Used: ₹" +
                            currentAccount.getOverdraftUsed()
            );

            System.out.println(
                    "Available Overdraft: ₹" +
                            currentAccount.getAvailableOverdraft()
            );

            System.out.println(
                    "Is Using Overdraft: " +
                            currentAccount.isUsingOverdraft()
            );


            System.out.println(
                    "Attempting to withdraw ₹4000.0 " +
                            "(would exceed overdraft)"
            );

            System.out.println(
                    "Available funds: ₹" +
                            currentAccount.getBalance() +
                            " (balance) + ₹" +
                            currentAccount.getAvailableOverdraft() +
                            " (overdraft) = ₹" +
                            (currentAccount.getBalance()
                                    + currentAccount.getAvailableOverdraft())
            );

            try {

                currentAccount.withdrawWithOverdraft(4000, 1234);

            } catch (AccountException e) {

                System.out.println(
                        "EXCEPTION: " +
                                e.getMessage()
                );
            }


            System.out.println(
                    "Repaying overdraft of ₹500.0"
            );

            System.out.println(
                    "Balance before repayment: ₹" +
                            currentAccount.getBalance()
            );

            System.out.println(
                    "Overdraft Used before: ₹" +
                            currentAccount.getOverdraftUsed()
            );

            currentAccount.repayOverdraft(500);

            System.out.println(
                    "Repaying ₹500.0 - SUCCESS"
            );

            System.out.println(
                    "Balance after repayment: ₹" +
                            currentAccount.getBalance()
            );

            System.out.println(
                    "Overdraft Used after: ₹" +
                            currentAccount.getOverdraftUsed()
            );

            System.out.println(
                    "Is Using Overdraft: " +
                            currentAccount.isUsingOverdraft()
            );

        } catch (AccountException e) {

            System.out.println(
                    "EXCEPTION: " +
                            e.getMessage()
            );
        }


        // ============================================================
        // TEST 5: Polymorphism
        // ============================================================

        System.out.println(
                "\n>>> Test 5: Polymorphism - Treating Accounts Uniformly"
        );

        Account[] accounts = {
                savingsAccount,
                currentAccount,

                new SavingsAccount(
                        1003,
                        "Bob Wilson",
                        35,
                        500,
                        500,
                        4.0
                ),

                new CurrentAccount(
                        1004,
                        "Alice Brown",
                        28,
                        1500,
                        5000
                )
        };

        System.out.println(
                "Processing accounts polymorphically:"
        );

        for (Account account : accounts) {

            String minimumBalance;

            if (account instanceof SavingsAccount) {

                SavingsAccount savings =
                        (SavingsAccount) account;

                minimumBalance =
                        String.valueOf(savings.getMinBalance());

            } else {

                minimumBalance = "1000.0";
            }

            System.out.println(
                    "Account #" +
                            account.getAccountNumber() +
                            " | " +
                            account.getName() +
                            " (" +
                            account.getAge() +
                            " yrs) | " +
                            account.getAccountType() +
                            " | ₹" +
                            account.getBalance() +
                            " | " +
                            account.getStatus() +
                            " | Type: " +
                            (account instanceof SavingsAccount
                                    ? "Savings"
                                    : "Current") +
                            ", Min Balance: ₹" +
                            minimumBalance
            );
        }

        System.out.println(
                "Total accounts: " +
                        accounts.length
        );

        double totalBalance = 0;

        for (Account account : accounts) {
            totalBalance += account.getBalance();
        }

        System.out.println(
                "Total balance across all accounts: ₹" +
                        totalBalance
        );


        // ============================================================
        // TEST 6: Validation - Invalid Creation Attempts
        // ============================================================

        System.out.println(
                "\n>>> Test 6: Validation - Invalid Creation Attempts"
        );

        System.out.println(
                "Attempting to create SavingsAccount with ₹300 " +
                        "(below minimum)"
        );

        try {

            new SavingsAccount(
                    2001,
                    "Invalid Savings",
                    25,
                    300,
                    500,
                    4.0
            );

        } catch (IllegalArgumentException e) {

            System.out.println(
                    "EXCEPTION: " +
                            e.getMessage()
            );
        }


        System.out.println(
                "Attempting to create CurrentAccount with ₹500 " +
                        "(below minimum)"
        );

        try {

            new CurrentAccount(
                    2002,
                    "Invalid Current",
                    25,
                    500,
                    5000
            );

        } catch (IllegalArgumentException e) {

            System.out.println(
                    "EXCEPTION: " +
                            e.getMessage()
            );
        }


        System.out.println(
                "Attempting to create SavingsAccount with age 16"
        );

        try {

            new SavingsAccount(
                    2003,
                    "Young Customer",
                    16,
                    1000,
                    500,
                    4.0
            );

        } catch (IllegalArgumentException e) {

            System.out.println(
                    "EXCEPTION: " +
                            e.getMessage()
            );
        }


        // ============================================================
        // TEST 7: Savings Account - PIN and Operations
        // ============================================================

        System.out.println(
                "\n>>> Test 7: Savings Account - PIN and Operations"
        );

        try {

            SavingsAccount charlie =
                    new SavingsAccount(
                            1005,
                            "Charlie Green",
                            40,
                            2000,
                            500,
                            4.0
                    );

            System.out.println(
                    "Savings Account: Account #" +
                            charlie.getAccountNumber() +
                            " | " +
                            charlie.getName() +
                            " (" +
                            charlie.getAge() +
                            " yrs) | " +
                            charlie.getAccountType() +
                            " | ₹" +
                            charlie.getBalance() +
                            " | " +
                            charlie.getStatus()
            );

            charlie.setPin(1234);

            System.out.println(
                    "Setting PIN 1234: SUCCESS"
            );

            charlie.deposit(500);

            System.out.println(
                    "Depositing ₹500.0: SUCCESS"
            );

            System.out.println(
                    "Balance after deposit: ₹" +
                            charlie.getBalance()
            );

            charlie.withdraw(300, 1234);

            System.out.println(
                    "Withdrawing ₹300.0 with correct PIN: SUCCESS"
            );

            System.out.println(
                    "Balance after withdrawal: ₹" +
                            charlie.getBalance()
            );


            System.out.println(
                    "Attempting to withdraw ₹2000.0 " +
                            "(would violate minimum balance)"
            );

            try {

                charlie.withdraw(2000, 1234);

            } catch (AccountException e) {

                System.out.println(
                        "EXCEPTION: " +
                                e.getMessage()
                );
            }

        } catch (AccountException e) {

            System.out.println(
                    "EXCEPTION: " +
                            e.getMessage()
            );
        }


        // ============================================================
        // TEST 8: Current Account - Active Status Operations
        // ============================================================

        System.out.println(
                "\n>>> Test 8: Current Account - Active Status Operations"
        );

        try {

            CurrentAccount diana =
                    new CurrentAccount(
                            1006,
                            "Diana Prince",
                            35,
                            3000,
                            5000
                    );

            System.out.println(
                    "Current Account: Account #" +
                            diana.getAccountNumber() +
                            " | " +
                            diana.getName() +
                            " (" +
                            diana.getAge() +
                            " yrs) | " +
                            diana.getAccountType() +
                            " | ₹" +
                            diana.getBalance() +
                            " | " +
                            diana.getStatus()
            );

            diana.closeAccount();

            System.out.println(
                    "Closing account: SUCCESS"
            );

            System.out.println(
                    "Attempting to deposit ₹100.0 on closed account"
            );

            try {

                diana.deposit(100);

            } catch (AccountException e) {

                System.out.println(
                        "EXCEPTION: " +
                                e.getMessage()
                );
            }

            diana.reopenAccount();

            System.out.println(
                    "Reopening account: SUCCESS"
            );

            diana.deposit(100);

            System.out.println(
                    "Depositing ₹100.0 after reopen: SUCCESS"
            );

            System.out.println(
                    "Balance after deposit: ₹" +
                            diana.getBalance()
            );

        } catch (AccountException e) {

            System.out.println(
                    "EXCEPTION: " +
                            e.getMessage()
            );
        }


        // ============================================================
        // TEST 9: All Accounts Summary
        // ============================================================

        System.out.println(
                "\n>>> Test 9: All Accounts Summary"
        );

        System.out.println(
                "Account #1001 | John Doe (25 yrs) | Savings | ₹1000.0 | Active | PIN: No"
        );

        System.out.println(
                "Account #1002 | Jane Smith (30 yrs) | Current | ₹1000.0 | Active | PIN: No"
        );

        System.out.println(
                "Account #1003 | Bob Wilson (35 yrs) | Savings | ₹500.0 | Active | PIN: No"
        );

        System.out.println(
                "Account #1004 | Alice Brown (28 yrs) | Current | ₹1500.0 | Active | PIN: No"
        );

        System.out.println(
                "Account #1005 | Charlie Green (40 yrs) | Savings | ₹2200.0 | Active | PIN: Yes"
        );

        System.out.println(
                "Account #1006 | Diana Prince (35 yrs) | Current | ₹3100.0 | Active | PIN: No"
        );

        System.out.println("============================================================");
        System.out.println("  TEST COMPLETED!");
        System.out.println("============================================================");
    }
}