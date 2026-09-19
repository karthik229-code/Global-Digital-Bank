package org.example;

public class SalaryAccount extends AbstractAccount {

    public SalaryAccount(int accountNumber, String name, int age,
                         double initialBalance) {

        super(accountNumber, name, age, initialBalance, "Salary");
    }

    @Override
    public void processDebit(double amount) throws AccountException {

        if (amount > getBalance()) {
            throw new InsufficientBalanceException(
                    "Insufficient balance"
            );
        }

        setBalance(getBalance() - amount);
    }
}