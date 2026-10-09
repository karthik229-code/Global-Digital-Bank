
package org.example;

public class Main {

    public static void main(String[] args) {
        System.out.println("Starting Global Digital Bank...");

        try {
            FileLogDestination destination = new FileLogDestination();
            TransactionLogger logger = new TransactionLogger(destination);
            TransferService transferService = new TransferService();

            AccountService accountService = new AccountService(
                    logger,
                    transferService
            );

            AccountUI accountUI = new AccountUI(accountService);
            accountUI.start();

        } catch (Exception exception) {
            System.out.println(
                    "Unable to start the application: "
                            + exception.getMessage()
            );
        }
    }
}
