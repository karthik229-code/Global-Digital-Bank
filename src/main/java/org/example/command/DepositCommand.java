package org.example.command;

import org.example.AbstractAccount;
import org.example.Transaction;

public class DepositCommand implements TransactionCommand {
    private static final long serialVersionUID = 1L;

    private final AbstractAccount account;
    private final double amount;
    private Transaction transaction;

    public DepositCommand(AbstractAccount account, double amount) {
        this.account = account;
        this.amount = amount;
    }

    @Override
    public void execute() throws Exception {
        transaction = account.depositWithTransaction(amount);
    }

    @Override
    public Transaction getTransaction() {
        return transaction;
    }
}