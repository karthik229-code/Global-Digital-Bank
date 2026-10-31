
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

    @Override
    public void save(IAccount account) {
        String sql = """
                INSERT INTO accounts
                (account_number, name, age, balance, account_type,
                 status, pin, tenure_years)
                VALUES (?, ?, ?, ?, ?, ?, ?, ?)
                """;

        try (PreparedStatement ps =
                     connectionProvider.getConnection().prepareStatement(sql)) {

            setAccountParameters(ps, (AbstractAccount) account);
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Failed to save account", e);
        }
    }

    @Override
    public IAccount findById(int accountNumber) {
        String sql = "SELECT * FROM accounts WHERE account_number = ?";

        try (PreparedStatement ps =
                     connectionProvider.getConnection().prepareStatement(sql)) {

            ps.setInt(1, accountNumber);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapAccount(rs);
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Failed to find account", e);
        }

        return null;
    }

    @Override
    public List<IAccount> findAll() {
        List<IAccount> accounts = new ArrayList<>();

        String sql = "SELECT * FROM accounts";

        try (PreparedStatement ps =
                     connectionProvider.getConnection().prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                accounts.add(mapAccount(rs));
            }

        } catch (SQLException e) {
            throw new RuntimeException("Failed to retrieve accounts", e);
        }

        return accounts;
    }

    @Override
    public void update(IAccount account) {
        String sql = """
                UPDATE accounts SET
                name = ?, age = ?, balance = ?, account_type = ?,
                status = ?, pin = ?, tenure_years = ?
                WHERE account_number = ?
                """;

        AbstractAccount acc = (AbstractAccount) account;

        try (PreparedStatement ps =
                     connectionProvider.getConnection().prepareStatement(sql)) {

            ps.setString(1, acc.getName());
            ps.setInt(2, acc.getAge());
            ps.setDouble(3, acc.getBalance());
            ps.setString(4, acc.getAccountType());
            ps.setString(5, acc.getStatus());

            if (acc.getPin() == null) {
                ps.setNull(6, Types.INTEGER);
            } else {
                ps.setInt(6, acc.getPin());
            }

            ps.setInt(7, acc.getTenureYears());
            ps.setInt(8, acc.getAccountNumber());

            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Failed to update account", e);
        }
    }

    @Override
    public void delete(int accountNumber) {
        String sql = "DELETE FROM accounts WHERE account_number = ?";

        try (PreparedStatement ps =
                     connectionProvider.getConnection().prepareStatement(sql)) {

            ps.setInt(1, accountNumber);
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Failed to delete account", e);
        }
    }

    @Override
    public boolean exists(int accountNumber) {
        String sql =
                "SELECT 1 FROM accounts WHERE account_number = ?";

        try (PreparedStatement ps =
                     connectionProvider.getConnection().prepareStatement(sql)) {

            ps.setInt(1, accountNumber);

            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }

        } catch (SQLException e) {
            throw new RuntimeException("Failed to check account", e);
        }
    }

    @Override
    public int nextAccountNumber() {
        String sql =
                "SELECT COALESCE(MAX(account_number), 1000) + 1 FROM accounts";

        try (Statement statement =
                     connectionProvider.getConnection().createStatement();
             ResultSet rs = statement.executeQuery(sql)) {

            return rs.next() ? rs.getInt(1) : 1001;

        } catch (SQLException e) {
            throw new RuntimeException("Failed to generate account number", e);
        }
    }

    private void setAccountParameters(
            PreparedStatement ps,
            AbstractAccount account
    ) throws SQLException {

        ps.setInt(1, account.getAccountNumber());
        ps.setString(2, account.getName());
        ps.setInt(3, account.getAge());
        ps.setDouble(4, account.getBalance());
        ps.setString(5, account.getAccountType());
        ps.setString(6, account.getStatus());

        if (account.getPin() == null) {
            ps.setNull(7, Types.INTEGER);
        } else {
            ps.setInt(7, account.getPin());
        }

        ps.setInt(8, account.getTenureYears());
    }

    private IAccount mapAccount(ResultSet rs) throws SQLException {
        try {
            AbstractAccount account = (AbstractAccount)
                    AccountFactory.createAccount(
                            rs.getString("account_type"),
                            rs.getInt("account_number"),
                            rs.getString("name"),
                            rs.getInt("age"),
                            rs.getDouble("balance"),
                            rs.getInt("tenure_years")
                    );

            int pin = rs.getInt("pin");

            if (!rs.wasNull()) {
                account.setPin(pin);
            }

            account.setStatus(rs.getString("status"));

            return account;

        } catch (AccountException e) {
            throw new SQLException("Failed to reconstruct account", e);
        }
    }
}
