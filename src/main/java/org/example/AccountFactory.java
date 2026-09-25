package org.example;

public class AccountFactory {

    public static IAccount createAccount(
            String accountType,
            int accountNumber,
            String name,
            int age,
            double initialBalance,
            int tenureYears) throws AccountException {

        switch (accountType.toUpperCase()) {

            case "SAVINGS":
                return new SavingsAccount(
                        accountNumber,
                        name,
                        age,
                        initialBalance,
                        tenureYears
                );

            case "CURRENT":
                return new CurrentAccount(
                        accountNumber,
                        name,
                        age,
                        initialBalance
                );

            case "FIXEDDEPOSIT":
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