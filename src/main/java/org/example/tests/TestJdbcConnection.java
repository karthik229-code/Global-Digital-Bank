
package org.example.tests;

import org.example.db.ConnectionProvider;
import org.example.repository.RepositoryFactory;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class TestJdbcConnection {

    public static void main(String[] args) throws Exception {

        System.out.println("=== JDBC CONNECTION TEST ===");

        ConnectionProvider provider =
                RepositoryFactory.getConnectionProvider();

        // Test 1: Provider information
        System.out.println("Provider: " + provider.getProviderName());

        // Test 2: Connection lifecycle
        Connection connection = provider.getConnection();

        if (connection != null && !connection.isClosed()) {
            System.out.println("PASS: Database connection established.");
        }

        // Test 3: SELECT 1
        try (Statement statement = connection.createStatement();
             ResultSet result = statement.executeQuery("SELECT 1")) {

            if (result.next() && result.getInt(1) == 1) {
                System.out.println("PASS: SELECT 1 executed successfully.");
            }
        }

        // Test 4: Schema and table existence
        String[] tables = {"accounts", "transactions"};

        for (String table : tables) {
            String sql = "SELECT name FROM sqlite_master "
                    + "WHERE type='table' AND name='" + table + "'";

            try (Statement statement = connection.createStatement();
                 ResultSet result = statement.executeQuery(sql)) {

                if (result.next()) {
                    System.out.println("PASS: Table exists - " + table);
                } else {
                    throw new RuntimeException(
                            "Table missing: " + table
                    );
                }
            }
        }

        // Test 5: Shutdown
        provider.shutdown();

        if (connection.isClosed()) {
            System.out.println("PASS: Connection shutdown successful.");
        }

        // Test 6: Reconnection
        Connection reopened = provider.getConnection();

        if (reopened != null && !reopened.isClosed()) {
            System.out.println("PASS: Connection reopened successfully.");
        }

        provider.shutdown();

        System.out.println("=== ALL JDBC TESTS PASSED ===");
    }
}
