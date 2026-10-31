package org.example.tests;

import org.example.*;
import org.example.db.ConnectionProvider;
import org.example.repository.JdbcAccountRepository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.nio.file.Files;
import java.nio.file.Path;

public class TestJdbcAccountRepository {

    public static void main(String[] args) throws Exception {

        Path databaseFile = Files.createTempFile(
                "gdb-activity23-test", ".db"
        );

        TestConnectionProvider provider =
                new TestConnectionProvider(databaseFile);

        try {
            initializeSchema(provider.getConnection());

            JdbcAccountRepository repository =
                    new JdbcAccountRepository(provider);

            testCreateAndRead(repository);
            testUpdate(repository);
            testTransfer(repository);
            testRollback(repository);
            testDelete(repository);

            System.out.println(
                    "\nALL JDBC ACCOUNT REPOSITORY TESTS PASSED!"
            );

        } finally {
            provider.shutdown();
            Files.deleteIfExists(databaseFile);
        }
    }

    private static void initializeSchema(Connection connection)
            throws SQLException {

        String sql = """
                CREATE TABLE accounts (
                    account_number INTEGER PRIMARY KEY,
                    name TEXT NOT NULL,
                    age INTEGER NOT NULL,
                    balance REAL NOT NULL,
                    account_type TEXT NOT NULL,
                    status TEXT NOT NULL,
                    pin INTEGER,
                    tenure_years INTEGER NOT NULL DEFAULT 0
                )
                """;

        try (Statement statement = connection.createStatement()) {
            statement.execute(sql);
        }
    }

    private static void testCreateAndRead(
            JdbcAccountRepository repository
    ) throws Exception {

        IAccount account = createAccount(
                1001, "Test User", 1000
        );

        repository.save(account);

        IAccount retrieved = repository.findById(1001);

        check(retrieved != null, "Account creation");
        check(retrieved.getName().equals("Test User"), "Account name");
        check(retrieved.getBalance() == 1000, "Initial balance");
        check(repository.exists(1001), "Account existence");

        System.out.println("PASS: Create and Read");
    }

    private static void testUpdate(
            JdbcAccountRepository repository
    ) throws Exception {

        IAccount account = repository.findById(1001);

        account.deposit(500);
        repository.update(account);

        IAccount updated = repository.findById(1001);

        check(updated.getBalance() == 1500, "Account update");

        System.out.println("PASS: Update");
    }

    private static void testTransfer(
            JdbcAccountRepository repository
    ) throws Exception {

        IAccount source = repository.findById(1001);

        IAccount destination = createAccount(
                1002, "Destination User", 500
        );

        repository.save(destination);

        // Simulate a validated transfer of Rs. 300.
        ((AbstractAccount) source).withdraw(300, 1234);
        destination.deposit(300);

        repository.transfer(source, destination);

        IAccount updatedSource = repository.findById(1001);
        IAccount updatedDestination = repository.findById(1002);

        check(updatedSource.getBalance() == 1200,
                "Source transfer balance");

        check(updatedDestination.getBalance() == 800,
                "Destination transfer balance");

        System.out.println("PASS: Atomic Transfer");
    }

    private static void testRollback(
            JdbcAccountRepository repository
    ) throws Exception {

        IAccount source = repository.findById(1001);

        double originalBalance = source.getBalance();

        // Change the in-memory balance before attempting persistence.
        ((AbstractAccount) source).withdraw(500, 1234);

        IAccount missingDestination = createAccount(
                9999, "Missing User", 300
        );

        try {
            repository.transfer(source, missingDestination);
            throw new AssertionError("Expected transfer failure");

        } catch (DataAccessException expected) {
            // Expected: destination account does not exist.
        }

        IAccount unchangedSource = repository.findById(1001);

        check(
                unchangedSource.getBalance() == originalBalance,
                "Rollback should restore source balance"
        );

        System.out.println("PASS: Rollback");
    }

    private static void testDelete(
            JdbcAccountRepository repository
    ) {

        repository.delete(1002);

        check(!repository.exists(1002), "Account deletion");

        System.out.println("PASS: Delete");
    }

    private static IAccount createAccount(
            int number,
            String name,
            double balance
    ) throws AccountException {

        IAccount account = AccountFactory.createAccount(
                "CURRENT",
                number,
                name,
                25,
                balance,
                0
        );

        ((AbstractAccount) account).setPin(1234);

        return account;
    }

    private static void check(
            boolean condition,
            String message
    ) {

        if (!condition) {
            throw new AssertionError("FAILED: " + message);
        }
    }

    private static class TestConnectionProvider
            implements ConnectionProvider {

        private final Connection connection;

        TestConnectionProvider(Path databaseFile)
                throws SQLException {

            this.connection = DriverManager.getConnection(
                    "jdbc:sqlite:" + databaseFile.toAbsolutePath()
            );
        }

        @Override
        public Connection getConnection() throws SQLException {
            return connection;
        }

        @Override
        public void shutdown() throws SQLException {
            connection.close();
        }

        @Override
        public String getProviderName() {
            return "Test SQLite Provider";
        }
    }
}