package org.example;

public class SavingsAccount extends AbstractAccount {

    private static final double MINIMUM_BALANCE = 500.0;

    public SavingsAccount(int accountNumber, String name, int age,
                          double initialBalance) {

        super(accountNumber, name, age, initialBalance, "Savings");
    }

    @Override
    public void processDebit(double amount) throws AccountException {

        double remainingBalance = getBalance() - amount;

        if (remainingBalance < MINIMUM_BALANCE) {
            throw new MinimumBalanceViolationException(
                    "Withdrawal would violate minimum balance requirement"
            );
        }

        setBalance(remainingBalance);
    }
}