package org.example;

public class AccountRulesEngine {

    private static final String SAVINGS_RULES_FILE =
            "config/rules/savings.properties";

    private static final AccountRulesPropertiesLoader SAVINGS_RULES =
            new AccountRulesPropertiesLoader(SAVINGS_RULES_FILE);

    private static String getTenureBucket(int tenureYears) {

        if (tenureYears >= 5) {
            return "privilege";
        } else if (tenureYears >= 3) {
            return "premium";
        } else if (tenureYears >= 1) {
            return "standard";
        } else {
            return "new";
        }
    }

    public static double getSavingsMinBalance(int tenureYears) {

        String bucket = getTenureBucket(tenureYears);

        return SAVINGS_RULES.getDouble(
                bucket + ".minBalance",
                0.0
        );
    }

    public static double getSavingsInterestRate(int tenureYears) {

        String bucket = getTenureBucket(tenureYears);

        return SAVINGS_RULES.getDouble(
                bucket + ".interestRate",
                0.0
        );
    }

    public static double getCurrentOverdraftLimit(
            double monthlyTurnover) {

        return Math.max(
                25000.0,
                monthlyTurnover * 2.5
        );
    }

    public static double getFDInterestRate(int months) {

        if (months >= 12) {
            return 6.5;
        } else if (months >= 6) {
            return 6.0;
        } else if (months >= 3) {
            return 5.5;
        } else {
            return 5.0;
        }
    }
}