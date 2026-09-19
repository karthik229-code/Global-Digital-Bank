package org.example;

public class FixedDepositAccount extends AbstractAccount {

    public FixedDepositAccount(int accountNumber, String name, int age,
                               double initialBalance) {

        super(accountNumber, name, age, initialBalance, "FixedDeposit");
    }

    @Override
    public void processDebit(double amount) throws AccountException {

        throw new AccountException(
                "Premature withdrawal is not allowed for Fixed Deposit"
        );
    }
}