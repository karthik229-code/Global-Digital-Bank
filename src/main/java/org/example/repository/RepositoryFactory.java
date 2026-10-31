package org.example.repository;

import org.example.DataAccessException;
import org.example.db.ConnectionProvider;
import org.example.db.DatabaseConfig;
import org.example.db.JdbcConnectionProvider;
import org.example.db.SchemaInitializer;

import java.sql.Connection;

public class RepositoryFactory {

    private static final String MODE =
            DatabaseConfig.getPersistenceMode();

    private static ConnectionProvider connectionProvider;

    private static boolean schemaInitialized = false;

    public static AccountRepository createAccountRepository() {

        if ("memory".equalsIgnoreCase(MODE)) {
            return new InMemoryAccountRepository();
        }

        initializeJdbc();

        return new JdbcAccountRepository(connectionProvider);
    }

    public static TransactionRepository createTransactionRepository() {

        if ("memory".equalsIgnoreCase(MODE)) {
            return new InMemoryTransactionRepository();
        }

        initializeJdbc();

        return new JdbcTransactionRepository(connectionProvider);
    }

    public static ConnectionProvider getConnectionProvider() {
        initializeJdbc();
        return connectionProvider;
    }

    private static synchronized void initializeJdbc() {

        if (!"jdbc".equalsIgnoreCase(MODE)) {
            throw new IllegalArgumentException(
                    "Unsupported persistence mode: " + MODE
            );
        }

        try {
            if (connectionProvider == null) {
                connectionProvider = new JdbcConnectionProvider();
            }

            if (!schemaInitialized) {
                Connection connection = connectionProvider.getConnection();

                SchemaInitializer.initialize(connection);

                schemaInitialized = true;
            }

        } catch (Exception e) {
            throw new DataAccessException(
                    "Failed to initialize JDBC persistence", e
            );
        }
    }
}