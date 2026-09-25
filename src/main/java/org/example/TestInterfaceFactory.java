package org.example;

public class TestInterfaceFactory {

    public static void main(String[] args) {

        System.out.println(
                "=== Activity 12: Factory-Driven System Suite ==="
        );

        try {

            // Test 1: Savings Account Creation & Deposit
            IAccount savings = AccountFactory.createAccount(
                    "SAVINGS",
                    1001,
                    "Rajesh Sharma",
                    25,
                    5000
            );

            savings.deposit(2000);

            System.out.println(
                    "[Test 1] Savings Account Creation & Deposit: [PASS]"
            );

            // Test 2: Current Account Overdraft Withdrawal
            IAccount current = AccountFactory.createAccount(
                    "CURRENT",
                    1002,
                    "Priya Patel",
                    30,
                    5000
            );

            ((AbstractAccount) current).changePin(1234);

            current.withdraw(8000, "1234");

            System.out.println(
                    "[Test 2] Current Account Overdraft Withdrawal: [PASS]"
            );

            // Test 3: Fixed Deposit Premature Withdrawal Block
            IAccount fixedDeposit = AccountFactory.createAccount(
                    "FIXED_DEPOSIT",
                    1003,
                    "Amit Kumar",
                    35,
                    20000
            );

            ((AbstractAccount) fixedDeposit).changePin(1234);

            try {

                fixedDeposit.withdraw(1000, "1234");

                System.out.println(
                        "[Test 3] Fixed Deposit Premature Withdrawal Block: [FAIL]"
                );

            } catch (AccountException e) {

                System.out.println(
                        "[Test 3] Fixed Deposit Premature Withdrawal Block: [PASS]"
                );
            }

            // Test 4: Invalid Account Type
            try {

                AccountFactory.createAccount(
                        "UNKNOWN",
                        1004,
                        "Test User",
                        25,
                        1000
                );

                System.out.println(
                        "[Test 4] Invalid Type Rejection: [FAIL]"
                );

            } catch (IllegalArgumentException e) {

                System.out.println(
                        "[Test 4] Invalid Type Rejection: [PASS]"
                );
            }

            System.out.println(
                    "Factory-driven architecture successfully verified!"
            );

        } catch (Exception e) {

            System.out.println(
                    "Unexpected error: " + e.getMessage()
            );
        }
    }
}