package org.example;

public class SavingsAccount extends AbstractAccount {

    private int tenureYears;
    private double minBalance;
    private double interestRate;

    // Old constructor - keeps previous activities working
    public SavingsAccount(
            int accountNumber,
            String name,
            int age,
            double initialBalance) {

        this(accountNumber, name, age, initialBalance, 0);
    }

    // New constructor for Activity 13.2
    public SavingsAccount(
            int accountNumber,
            String name,
            int age,
            double initialBalance,
            int tenureYears) {

        super(
                accountNumber,
                name,
                age,
                initialBalance,
                "Savings"
        );

        this.tenureYears = tenureYears;

        this.minBalance =
                AccountRulesEngine.getSavingsMinBalance(tenureYears);

        this.interestRate =
                AccountRulesEngine.getSavingsInterestRate(tenureYears);
    }

    @Override
    public void processDebit(double amount)
            throws AccountException {

        double remainingBalance = getBalance() - amount;

        if (remainingBalance < minBalance) {
            throw new MinimumBalanceViolationException(
                    "Withdrawal would violate minimum balance requirement"
            );
        }

        setBalance(remainingBalance);
    }

    public int getTenureYears() {
        return tenureYears;
    }

    public double getMinBalance() {
        return minBalance;
    }

    public double getInterestRate() {
        return interestRate;
    }
}