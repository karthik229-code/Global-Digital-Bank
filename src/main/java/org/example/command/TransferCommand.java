package org.example.command;

import org.example.AbstractAccount;
import org.example.Transaction;
import org.example.TransferService;

public class TransferCommand implements TransactionCommand {
    private static final long serialVersionUID = 1L;

    private final AbstractAccount from;
    private final AbstractAccount to;
    private final double amount;
    private final int pin;
    private Transaction transaction;

    public TransferCommand(
            AbstractAccount from,
            AbstractAccount to,
            double amount,
            int pin) {
        this.from = from;
        this.to = to;
        this.amount = amount;
        this.pin = pin;
    }

    @Override
    public void execute() throws Exception {
        transaction = TransferService.transferWithTransaction(
                from, to, amount, pin
        );
    }

    @Override
    public Transaction getTransaction() {
        return transaction;
    }
}