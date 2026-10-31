
package org.example.repository;

import org.example.AccountRulesPropertiesLoader;

public class RepositoryFactory {

    private static final String CONFIG_FILE =
            "config/rules/persistence.properties";

    private static final AccountRulesPropertiesLoader CONFIG =
            new AccountRulesPropertiesLoader(CONFIG_FILE);

    private static final String MODE =
            CONFIG.getProperty("persistence.mode", "memory");

    public static AccountRepository createAccountRepository() {

        if ("memory".equalsIgnoreCase(MODE)) {
            return new InMemoryAccountRepository();
        }

        throw new IllegalArgumentException(
                "Unsupported persistence mode: " + MODE
        );
    }

    public static TransactionRepository createTransactionRepository() {

        if ("memory".equalsIgnoreCase(MODE)) {
            return new InMemoryTransactionRepository();
        }

        throw new IllegalArgumentException(
                "Unsupported persistence mode: " + MODE
        );
    }
}
