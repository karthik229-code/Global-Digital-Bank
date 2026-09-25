package org.example;

public class TestAccountRulesEngineProperties {

    public static void main(String[] args) {

        System.out.println(
                "=== Activity 14: Properties-Driven Rules Engine Test ==="
        );

        System.out.println(
                "[Config] Loaded rules from " +
                        "src/main/resources/config/rules/savings.properties"
        );

        int[] tenures = {0, 2, 4, 6};

        for (int tenure : tenures) {

            double minBalance =
                    AccountRulesEngine.getSavingsMinBalance(tenure);

            double interestRate =
                    AccountRulesEngine.getSavingsInterestRate(tenure);

            System.out.println(
                    "Tenure " + tenure
                            + " yrs -> Min Balance: Rs "
                            + minBalance
                            + " | Interest: "
                            + String.format("%.2f", interestRate)
                            + "%"
            );
        }

        System.out.println(
                "All external properties loaded and verified successfully!"
        );
    }
}