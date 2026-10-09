package org.example.tests;

import org.example.AbstractAccount;
import org.example.AccountFactory;
import org.example.Transaction;
import org.example.command.DepositCommand;
import org.example.command.TransactionCommand;
import org.example.command.TransferCommand;
import org.example.command.WithdrawCommand;
import org.example.logging.TransactionLog;

import java.util.List;

public class TestCommandLogging {
    public static void main(String[] args) throws Exception {
        System.out.println("============================================================");
        System.out.println(" ACTIVITY 17 - COMMAND PATTERN + FILE LOGGING");
        System.out.println("============================================================");

        TransactionLog log = new TransactionLog();
        log.clear();

        AbstractAccount acc1 =
                (AbstractAccount) AccountFactory.createAccount(
                        "SAVINGS", 1001, "Rajesh Sharma",
                        30, 15000, 0
                );

        AbstractAccount acc2 =
                (AbstractAccount) AccountFactory.createAccount(
                        "SAVINGS", 1002, "Priya Patel",
                        28, 10000, 0
                );

        acc1.setPin(1234);

        TransactionCommand deposit = new DepositCommand(acc1, 5000);
        deposit.execute();
        log.log(deposit);
        System.out.println("[STEP 11] Logged: " + deposit.getTransaction());

        TransactionCommand withdrawal =
                new WithdrawCommand(acc1, 2000, 1234);
        withdrawal.execute();
        log.log(withdrawal);
        System.out.println("[STEP 12] Logged: " + withdrawal.getTransaction());

        TransactionCommand transfer =
                new TransferCommand(acc1, acc2, 3000, 1234);
        transfer.execute();
        log.log(transfer);
        System.out.println("[STEP 13] Logged: " + transfer.getTransaction());

        List<TransactionCommand> history = log.readAll();

        System.out.println();
        System.out.println("[STEP 14] Read " + history.size()
                + " commands from transaction log:");

        for (int i = 0; i < history.size(); i++) {
            Transaction transaction = history.get(i).getTransaction();
            System.out.println("[" + (i + 1) + "] " + transaction);
        }

        TransactionLog freshLog = new TransactionLog();
        List<TransactionCommand> persisted = freshLog.readAll();

        if (persisted.size() != 3) {
            throw new AssertionError(
                    "Expected 3 persisted commands, got " + persisted.size()
            );
        }

        System.out.println();
        System.out.println(
                "[STEP 15] Fresh reader verified 3 persisted commands [PASS]"
        );
    }
}