package org.example;

public class TestInterfaceFactory {

    public static void main(String[] args) {

        System.out.println(
                "=== Activity 11: Interface & Factory Pattern Test ==="
        );

        try {

            IAccount savings = AccountFactory.createAccount(
                    "SAVINGS",
                    1001,
                    "Rajesh Sharma",
                    25,
                    10000
            );

            IAccount current = AccountFactory.createAccount(
                    "CURRENT",
                    1002,
                    "Priya Patel",
                    30,
                    15000
            );

            IAccount fixedDeposit = AccountFactory.createAccount(
                    "FIXED_DEPOSIT",
                    1003,
                    "Amit Kumar",
                    35,
                    20000
            );

            IAccount salary = AccountFactory.createAccount(
                    "SALARY",
                    1004,
                    "Sneha Verma",
                    28,
                    12000
            );

            System.out.println(
                    "Factory created: "
                            + savings.getAccountType()
                            + " account for "
                            + savings.getName()
            );

            System.out.println(
                    "Factory created: "
                            + current.getAccountType()
                            + " account for "
                            + current.getName()
            );

            System.out.println(
                    "Factory created: "
                            + fixedDeposit.getAccountType()
                            + " account for "
                            + fixedDeposit.getName()
            );

            System.out.println(
                    "Factory created: "
                            + salary.getAccountType()
                            + " account for "
                            + salary.getName()
            );

            System.out.println(
                    "All accounts successfully created through AccountFactory!"
            );

        } catch (Exception e) {

            System.out.println(
                    "Test failed: " + e.getMessage()
            );
        }
    }
}