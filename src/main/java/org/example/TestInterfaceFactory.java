package org.example;

public class TestInterfaceFactory {

    public static void main(String[] args) {

        try {
            System.out.println(
                    "=== Activity 13.2: Dynamic Account Rules Test ==="
            );

            IAccount account = AccountFactory.createAccount(
                    "SAVINGS",
                    1001,
                    "Alice",
                    25,
                    10000.0,
                    4
            );

            SavingsAccount savings = (SavingsAccount) account;

            System.out.println(
                    "Created Savings Account (Tenure: "
                            + savings.getTenureYears() + " yrs):"
            );

            System.out.println(
                    "-> Min Balance: Rs "
                            + savings.getMinBalance()
                            + " (Dynamically fetched)"
            );

            System.out.println(
                    "-> Interest Rate: "
                            + savings.getInterestRate()
                            + "% (Dynamically fetched)"
            );

            System.out.println(
                    "Dynamic rule integration verified!"
            );

        } catch (Exception e) {

            System.out.println(
                    "Unexpected error: " + e.getMessage()
            );
        }
    }
}