package org.example;

public class Main {

    public static void main(String[] args) {

        TransactionLogger logger =
                new TransactionLogger(new FileLogDestination());

        AccountService accountService =
                new AccountService(logger, null);

        AccountUI accountUI = new AccountUI(accountService);

        accountUI.start();
    }
}