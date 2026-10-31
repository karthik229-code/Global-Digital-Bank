package org.example.repository;

import org.example.*;
import org.example.db.ConnectionProvider;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JdbcAccountRepository implements AccountRepository {

    private final ConnectionProvider connectionProvider;

    public JdbcAccountRepository(ConnectionProvider connectionProvider) {
        this.connectionProvider = connectionProvider;
    }

    // CREATE
    @Override
    public void save(IAccount account) {
        String sql = "INSERT INTO accounts "
                + "(account_number, name, age, balance, account_type, "
                + "status, pin, tenure_years) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement statement =
                     connectionProvider.getConnection().prepareStatement(sql)) {

            setAccountParameters(statement, account);
            statement.executeUpdate();

        } catch (SQLException e) {
            throw new DataAccessException("Failed to save account", e);
        }
    }

    // READ ONE
    @Override
    public IAccount findById(int accountNumber) {
        String sql = "SELECT * FROM accounts WHERE account_number = ?";

        try (PreparedStatement statement =
                     connectionProvider.getConnection().prepareStatement(sql)) {

            statement.setInt(1, accountNumber);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return mapAccount(resultSet);
                }
            }

            return null;

        } catch (SQLException e) {
            throw new DataAccessException("Failed to find account", e);
        }
    }

    // READ ALL
    @Override
    public List<IAccount> findAll() {
        String sql = "SELECT * FROM accounts";
        List<IAccount> accounts = new ArrayList<>();

        try (PreparedStatement statement =
                     connectionProvider.getConnection().prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                accounts.add(mapAccount(resultSet));
            }

            return accounts;

        } catch (SQLException e) {
            throw new DataAccessException("Failed to retrieve accounts", e);
        }
    }

    // UPDATE
    @Override
    public void update(IAccount account) {
        String sql = "UPDATE accounts SET "
                + "name = ?, age = ?, balance = ?, account_type = ?, "
                + "status = ?, pin = ?, tenure_years = ? "
                + "WHERE account_number = ?";

        try (PreparedStatement statement =
                     connectionProvider.getConnection().prepareStatement(sql)) {

            AbstractAccount abstractAccount = (AbstractAccount) account;

            statement.setString(1, account.getName());
            statement.setInt(2, abstractAccount.getAge());
            statement.setDouble(3, account.getBalance());
            statement.setString(4, account.getAccountType());
            statement.setString(5, account.getStatus());

            if (abstractAccount.getPin() == null) {
                statement.setNull(6, Types.INTEGER);
            } else {
                statement.setInt(6, abstractAccount.getPin());
            }

            statement.setInt(7, abstractAccount.getTenureYears());
            statement.setInt(8, account.getAccountNumber());

            int rows = statement.executeUpdate();

            if (rows == 0) {
                throw new DataAccessException("Account not found for update");
            }

        } catch (SQLException e) {
            throw new DataAccessException("Failed to update account", e);
        }
    }

    // DELETE
    @Override
    public void delete(int accountNumber) {
        String sql = "DELETE FROM accounts WHERE account_number = ?";

        try (PreparedStatement statement =
                     connectionProvider.getConnection().prepareStatement(sql)) {

            statement.setInt(1, accountNumber);
            statement.executeUpdate();

        } catch (SQLException e) {
            throw new DataAccessException("Failed to delete account", e);
        }
    }

    // CHECK EXISTENCE
    @Override
    public boolean exists(int accountNumber) {
        String sql = "SELECT 1 FROM accounts WHERE account_number = ?";

        try (PreparedStatement statement =
                     connectionProvider.getConnection().prepareStatement(sql)) {

            statement.setInt(1, accountNumber);

            try (ResultSet resultSet = statement.executeQuery()) {
                return resultSet.next();
            }

        } catch (SQLException e) {
            throw new DataAccessException("Failed to check account existence", e);
        }
    }

    // GENERATE NEXT ACCOUNT NUMBER
    @Override
    public int nextAccountNumber() {
        String sql = "SELECT COALESCE(MAX(account_number), 1000) + 1 "
                + "FROM accounts";

        try (PreparedStatement statement =
                     connectionProvider.getConnection().prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            return resultSet.next() ? resultSet.getInt(1) : 1001;

        } catch (SQLException e) {
            throw new DataAccessException("Failed to generate account number", e);
        }
    }

    // ATOMIC TRANSFER
    @Override
    public void transfer(IAccount sourceAccount, IAccount destinationAccount) {

        if (sourceAccount == null || destinationAccount == null) {
            throw new IllegalArgumentException(
                    "Source and destination accounts cannot be null"
            );
        }

        String sql = "UPDATE accounts SET balance = ? "
                + "WHERE account_number = ?";

        Connection connection = null;
        boolean originalAutoCommit = true;

        try {
            connection = connectionProvider.getConnection();

            originalAutoCommit = connection.getAutoCommit();
            connection.setAutoCommit(false);

            try (PreparedStatement statement =
                         connection.prepareStatement(sql)) {

                statement.setDouble(1, sourceAccount.getBalance());
                statement.setInt(2, sourceAccount.getAccountNumber());

                if (statement.executeUpdate() != 1) {
                    throw new SQLException("Source account update failed");
                }

                statement.setDouble(1, destinationAccount.getBalance());
                statement.setInt(2, destinationAccount.getAccountNumber());

                if (statement.executeUpdate() != 1) {
                    throw new SQLException("Destination account update failed");
                }
            }

            connection.commit();

        } catch (SQLException e) {

            if (connection != null) {
                try {
                    connection.rollback();
                } catch (SQLException rollbackException) {
                    e.addSuppressed(rollbackException);
                }
            }

            throw new DataAccessException(
                    "Atomic transfer failed. Changes rolled back.", e
            );

        } finally {
            if (connection != null) {
                try {
                    connection.setAutoCommit(originalAutoCommit);
                } catch (SQLException e) {
                    throw new DataAccessException(
                            "Failed to restore connection state", e
                    );
                }
            }
        }
    }

    // MAP RESULTSET TO ACCOUNT OBJECT
    private IAccount mapAccount(ResultSet resultSet) throws SQLException {

        try {
            String type = resultSet.getString("account_type");

            IAccount account = AccountFactory.createAccount(
                    type,
                    resultSet.getInt("account_number"),
                    resultSet.getString("name"),
                    resultSet.getInt("age"),
                    resultSet.getDouble("balance"),
                    resultSet.getInt("tenure_years")
            );

            AbstractAccount abstractAccount = (AbstractAccount) account;

            int pin = resultSet.getInt("pin");

            if (!resultSet.wasNull()) {
                abstractAccount.setPin(pin);
            }

            abstractAccount.setStatus(resultSet.getString("status"));

            return account;

        } catch (AccountException e) {
            throw new SQLException("Failed to map account", e);
        }
    }

    // SET INSERT PARAMETERS
    private void setAccountParameters(
            PreparedStatement statement,
            IAccount account
    ) throws SQLException {

        AbstractAccount abstractAccount = (AbstractAccount) account;

        statement.setInt(1, account.getAccountNumber());
        statement.setString(2, account.getName());
        statement.setInt(3, abstractAccount.getAge());
        statement.setDouble(4, account.getBalance());
        statement.setString(5, account.getAccountType());
        statement.setString(6, account.getStatus());

        if (abstractAccount.getPin() == null) {
            statement.setNull(7, Types.INTEGER);
        } else {
            statement.setInt(7, abstractAccount.getPin());
        }

        statement.setInt(8, abstractAccount.getTenureYears());
    }
}