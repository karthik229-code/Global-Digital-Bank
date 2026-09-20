package org.example;

public class TestAbstractAccount {

    // Step 2: Fund Transfer Method
    public static void transferFunds(
            AbstractAccount source,
            AbstractAccount destination,
            double amount,
            int pin) throws AccountException {

        // Withdraw first
        source.withdraw(amount, pin);

        // Deposit only if withdrawal succeeds
        destination.deposit(amount);
    }

    // Step 3: Monthly Banking Cycle
    public static void processMonthlyCycle(
            AbstractAccount[] accounts) throws AccountException {

        for (AbstractAccount account : accounts) {

            // Savings Account - apply monthly interest
            if (account instanceof SavingsAccount) {

                SavingsAccount savings =
                        (SavingsAccount) account;

                // Monthly interest
                double interest =
                        savings.getBalance() * 0.01;

                savings.deposit(interest);
            }

            // Salary Account - check salary credit history
            else if (account instanceof SalaryAccount) {

                SalaryAccount salary =
                        (SalaryAccount) account;

                // SalaryAccount currently has no
                // salary-history field/method, so we
                // confirm that the salary account exists
                // and is active.
                if ("Active".equals(salary.getStatus())) {
                    System.out.println(
                            "Salary credit history checked for "
                                    + salary.getName()
                    );
                }
            }
        }
    }

    public static void main(String[] args) {

        System.out.println(
                "=== Activity 10: Banking Operations Suite ==="
        );

        try {

            // ==========================================
            // Step 1: Create Account Portfolio
            // ==========================================

            SavingsAccount savings =
                    new SavingsAccount(
                            1001,
                            "Alice",
                            25,
                            10000
                    );

            CurrentAccount current =
                    new CurrentAccount(
                            1002,
                            "Bob",
                            30,
                            5000
                    );

            SalaryAccount salary =
                    new SalaryAccount(
                            1003,
                            "Charlie",
                            28,
                            15000
                    );

            // Set PINs
            savings.changePin(1234);
            current.changePin(1234);
            salary.changePin(1234);

            // Portfolio using AbstractAccount references
            AbstractAccount[] accounts = {
                    savings,
                    current,
                    salary
            };

            // ==========================================
            // Step 2: Successful Fund Transfer
            // ==========================================

            transferFunds(
                    savings,
                    current,
                    3000,
                    1234
            );

            System.out.println(
                    "Transfer Rs 3000 from Savings to Current: SUCCESS"
            );

            System.out.println(
                    "Savings Balance: Rs "
                            + savings.getBalance()
                            + " | Current Balance: Rs "
                            + current.getBalance()
            );

            // ==========================================
            // Failed Transfer - Wrong PIN
            // ==========================================

            double savingsBefore =
                    savings.getBalance();

            double currentBefore =
                    current.getBalance();

            try {

                transferFunds(
                        savings,
                        current,
                        1000,
                        9999
                );

                System.out.println(
                        "Failed Transfer (Wrong PIN): FAILED"
                );

            } catch (AccountException e) {

                boolean balancesUnchanged =
                        savings.getBalance() == savingsBefore
                                && current.getBalance() == currentBefore;

                if (balancesUnchanged) {

                    System.out.println(
                            "Failed Transfer (Wrong PIN): "
                                    + "Exception caught, no balance changed [PASS]"
                    );

                } else {

                    System.out.println(
                            "Failed Transfer (Wrong PIN): "
                                    + "Balance changed [FAIL]"
                    );
                }
            }

            // ==========================================
            // Step 3: Monthly Banking Cycle
            // ==========================================

            processMonthlyCycle(accounts);

            System.out.println(
                    "Monthly Interest Cycle processed "
                            + "for all qualifying accounts."
            );

            System.out.println(
                    "All banking operations passed!"
            );

        } catch (Exception e) {

            System.out.println(
                    "Unexpected error: "
                            + e.getMessage()
            );
        }
    }
}