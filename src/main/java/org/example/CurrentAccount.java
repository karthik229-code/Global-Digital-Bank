package org.example;

public class CurrentAccount extends Account {

    private double overdraftLimit;
    private double overdraftUsed;

    public CurrentAccount(int accountNumber, String name, int age,
                          double initialBalance,
                          double overdraftLimit) {

        super(accountNumber, name, age, initialBalance, "CURRENT");

        this.overdraftLimit = overdraftLimit;
        this.overdraftUsed = 0;
    }

    public double getOverdraftLimit() {
        return overdraftLimit;
    }

    public void setOverdraftLimit(double overdraftLimit) {
        this.overdraftLimit = overdraftLimit;
    }

    public double getOverdraftUsed() {
        return overdraftUsed;
    }

    public double getAvailableOverdraft() {
        return overdraftLimit - overdraftUsed;
    }

    public boolean isUsingOverdraft() {
        return overdraftUsed > 0;
    }

    public void withdrawWithOverdraft(double amount, int pin)
            throws AccountException {

        if ("Inactive".equals(getStatus())) {
            throw new InactiveAccountException(
                    "Account is inactive"
            );
        }

        if (!hasPin() || !verifyPin(pin)) {
            throw new InvalidPinException(
                    "Invalid PIN"
            );
        }

        if (amount <= 0) {
            throw new InvalidAmountException(
                    "Withdrawal amount must be greater than zero"
            );
        }

        double availableFunds =
                getBalance() + getAvailableOverdraft();

        if (amount > availableFunds) {
            throw new InsufficientBalanceException(
                    "Insufficient funds. Available: ₹"
                            + availableFunds
                            + " (including ₹"
                            + overdraftLimit
                            + " overdraft), Requested: ₹"
                            + amount
            );
        }

        double newBalance = getBalance() - amount;

        if (newBalance < 1000.0) {
            overdraftUsed += 1000.0 - newBalance;
        }

        setBalance(newBalance);
    }

    public void repayOverdraft(double amount)
            throws AccountException {

        if (amount <= 0) {
            throw new InvalidAmountException(
                    "Repayment amount must be greater than zero"
            );
        }

        if (overdraftUsed == 0) {
            return;
        }

        double repayment =
                Math.min(amount, overdraftUsed);

        setBalance(getBalance() + repayment);

        overdraftUsed -= repayment;
    }
}