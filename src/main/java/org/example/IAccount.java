package org.example;

public interface IAccount {

    int getAccountNumber();

    String getName();

    double getBalance();

    String getAccountType();

    String getStatus();

    void deposit(double amount)
            throws InvalidAmountException;

    void withdraw(double amount, String pin)
            throws AccountException;

    void displayAccountInfo();
}