package org.example;

import java.util.HashMap;
import java.util.Map;

public class AccountRulesEngine {

    private static final String SAVINGS_RULES_FILE =
            "config/rules/savings.properties";

    private static final AccountRulesPropertiesLoader SAVINGS_RULES =
            new AccountRulesPropertiesLoader(SAVINGS_RULES_FILE);

    private static final AccountRulesEngine INSTANCE =
            new AccountRulesEngine();

    private final Map<String, Map<String, Double>> dailyTransferLimits =
            new HashMap<>();

    private AccountRulesEngine() {
        loadDailyTransferLimits();
    }

    public static AccountRulesEngine getInstance() {
        return INSTANCE;
    }

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

    private void loadDailyTransferLimits() {

        String[] accountTypes = {
                "SAVINGS",
                "CURRENT",
                "FIXEDDEPOSIT",
                "SALARY"
        };

        String[] buckets = {
                "new",
                "standard",
                "premium",
                "privilege"
        };

        for (String accountType : accountTypes) {

            Map<String, Double> limits = new HashMap<>();

            AccountRulesPropertiesLoader loader =
                    new AccountRulesPropertiesLoader(
                            "config/rules/"
                                    + accountType.toLowerCase()
                                    + ".properties"
                    );

            for (String bucket : buckets) {

                double value = loader.getDouble(
                        "daily.transfer.limit." + bucket,
                        Double.NaN
                );

                if (!Double.isNaN(value)) {
                    limits.put(bucket, value);
                }
            }

            dailyTransferLimits.put(accountType, limits);
        }
    }

    public Object getAdditionalFeature(
            String accountType,
            int tenureYears,
            String feature) {

        if (!"dailyTransferLimit".equals(feature)) {
            return null;
        }

        if (accountType == null) {
            return null;
        }

        String type = accountType.toUpperCase();

        if ("SAVINGS".equals(type)
                || "CURRENT".equals(type)
                || "FIXEDDEPOSIT".equals(type)
                || "SALARY".equals(type)) {

            Map<String, Double> limits =
                    dailyTransferLimits.get(type);

            if (limits == null) {
                return null;
            }

            return limits.get(
                    getTenureBucket(tenureYears)
            );
        }

        return null;
    }

    public double getDailyTransferLimit(
            String accountType,
            int tenureYears) {

        Object value = getAdditionalFeature(
                accountType,
                tenureYears,
                "dailyTransferLimit"
        );

        return value == null ? 0.0 : (Double) value;
    }
}