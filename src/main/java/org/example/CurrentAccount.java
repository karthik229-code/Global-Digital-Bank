package org.example;

public class CurrentAccount extends AbstractAccount {

    private static final double OVERDRAFT_LIMIT = 5000.0;

    public CurrentAccount(int accountNumber, String name, int age,
                          double initialBalance) {

        super(accountNumber, name, age, initialBalance, "Current");
    }

    @Override
    public void processDebit(double amount) throws AccountException {

        double minimumAllowedBalance = -OVERDRAFT_LIMIT;

        double remainingBalance = getBalance() - amount;

        if (remainingBalance < minimumAllowedBalance) {
            throw new InsufficientBalanceException(
                    "Withdrawal exceeds overdraft limit"
            );
        }

        setBalance(remainingBalance);
    }
}