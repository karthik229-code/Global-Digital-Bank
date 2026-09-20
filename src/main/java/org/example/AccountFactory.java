package org.example;

public class AccountFactory {

    public static AbstractAccount createAccount(
            String accountType,
            int accountNumber,
            String name,
            int age,
            double initialBalance) {

        switch (accountType.toUpperCase()) {

            case "SAVINGS":
                return new SavingsAccount(
                        accountNumber,
                        name,
                        age,
                        initialBalance
                );

            case "CURRENT":
                return new CurrentAccount(
                        accountNumber,
                        name,
                        age,
                        initialBalance
                );

            case "FIXED_DEPOSIT":
            case "FD":
                return new FixedDepositAccount(
                        accountNumber,
                        name,
                        age,
                        initialBalance
                );

            case "SALARY":
                return new SalaryAccount(
                        accountNumber,
                        name,
                        age,
                        initialBalance
                );

            default:
                throw new IllegalArgumentException(
                        "Unknown account type: " + accountType
                );
        }
    }
}