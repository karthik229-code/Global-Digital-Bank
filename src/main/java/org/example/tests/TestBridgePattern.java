package org.example.tests;

import org.example.AbstractAccount;
import org.example.AccountFactory;
import org.example.DatabaseLogDestination;
import org.example.FileLogDestination;
import org.example.LogDestination;
import org.example.MemoryLogDestination;
import org.example.SimulatedDatabase;
import org.example.TransactionLogger;
import org.example.command.DepositCommand;
import org.example.command.TransactionCommand;

public class TestBridgePattern {

    public static void main(String[] args) throws Exception {

        AbstractAccount account = (AbstractAccount)
                AccountFactory.createAccount(
                        "SAVINGS",
                        101,
                        "Karthik",
                        20,
                        15000.0,
                        1
                );

        TransactionCommand command =
                new DepositCommand(account, 5000.0);

        command.execute();

        LogDestination fileDestination = new FileLogDestination();
        LogDestination memoryDestination = new MemoryLogDestination();
        LogDestination databaseDestination =
                new DatabaseLogDestination(new SimulatedDatabase());

        TransactionLogger logger =
                new TransactionLogger(fileDestination);

        logger.clear();
        logger.log(command);

        System.out.println(
                logger.getDestinationName()
                        + " log count: "
                        + logger.readAll().size()
        );

        logger.setDestination(memoryDestination);
        logger.log(command);

        System.out.println(
                logger.getDestinationName()
                        + " log count: "
                        + logger.readAll().size()
        );

        logger.setDestination(databaseDestination);
        logger.log(command);

        System.out.println(
                logger.getDestinationName()
                        + " log count: "
                        + logger.readAll().size()
        );

        System.out.println("Bridge Pattern test completed.");
    }
}