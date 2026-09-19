package org.example;

public class TestAbstractAccount {

    public static void main(String[] args) {

        System.out.println("=== Activity 9: Abstract Account & Template Pattern ===");
        // Saving Account
        try {
            SavingsAccount savings = new SavingsAccount(1001, "Alice", 25, 10000);

            savings.changePin(1234);

            savings.withdraw(2000, 1234);

            System.out.println(
                    "[Savings] Withdraw 2000: SUCCESS | Balance: Rs "
                            + savings.getBalance()
            );

            // Try withdrawal that goes below minimum balance
            try {
                savings.withdraw(7600, 1234);
                System.out.println("[Savings] Withdraw below min balance: FAILED");
            } catch (MinimumBalanceViolationException e) {
                System.out.println("[Savings] Withdraw below min balance: Caught "
                                + e.getClass().getSimpleName()
                                + " [PASS]");
            }

            // Current Account
            CurrentAccount current = new CurrentAccount(1002, "Bob", 30, 5000);

            current.changePin(1234);

            current.withdraw(8000, 1234);

            System.out.println("[Current] Overdraft debit: SUCCESS | Balance: Rs " + current.getBalance());

            // Fixed Deposit Account
            FixedDepositAccount fixedDeposit = new FixedDepositAccount(
                            1003,
                            "Charlie",
                            35,
                            20000);

            fixedDeposit.changePin(1234);
            try {
                fixedDeposit.withdraw(1000, 1234);

                System.out.println("[FixedDeposit] Premature debit: FAILED");

            } catch (AccountException e) {
                System.out.println("[FixedDeposit] Premature debit: Caught " + e.getClass().getSimpleName() + " [PASS]");
            }

            System.out.println("Template method pattern executed successfully!");
        } catch (Exception e) {
            System.out.println("Unexpected error: " + e.getMessage());
        }
    }
}