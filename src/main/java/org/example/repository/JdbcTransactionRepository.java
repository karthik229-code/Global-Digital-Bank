
package org.example.repository;

import org.example.Transaction;
import org.example.TransactionType;
import org.example.db.ConnectionProvider;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class JdbcTransactionRepository implements TransactionRepository {

    private final ConnectionProvider connectionProvider;

    public JdbcTransactionRepository(ConnectionProvider connectionProvider) {
        this.connectionProvider = connectionProvider;
    }

    @Override
    public void save(Transaction transaction) {
        String sql = """
                INSERT INTO transactions (
                    transaction_id, timestamp, account_number,
                    transaction_type, amount, balance_after,
                    status, description, source_account, destination_account
                )
                VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
                """;

        try (PreparedStatement ps =
                     connectionProvider.getConnection().prepareStatement(sql)) {

            ps.setString(1, transaction.getTransactionId());
            ps.setString(2, transaction.getTimestamp().toString());
            ps.setInt(3, transaction.getAccountNumber());
            ps.setString(4, transaction.getType().name());
            ps.setDouble(5, transaction.getAmount());
            ps.setDouble(6, transaction.getBalanceAfter());
            ps.setString(7, transaction.getStatus());
            ps.setString(8, transaction.getDescription());
            ps.setInt(9, transaction.getFromAccount());
            ps.setInt(10, transaction.getToAccount());

            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Failed to save transaction", e);
        }
    }

    @Override
    public List<Transaction> findByAccount(int accountNumber) {
        List<Transaction> transactions = new ArrayList<>();

        String sql = """
                SELECT * FROM transactions
                WHERE account_number = ?
                ORDER BY timestamp
                """;

        try (PreparedStatement ps =
                     connectionProvider.getConnection().prepareStatement(sql)) {

            ps.setInt(1, accountNumber);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    transactions.add(mapTransaction(rs));
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Failed to find transactions", e);
        }

        return transactions;
    }

    @Override
    public List<Transaction> findAll() {
        List<Transaction> transactions = new ArrayList<>();

        String sql = "SELECT * FROM transactions ORDER BY timestamp";

        try (Statement statement =
                     connectionProvider.getConnection().createStatement();
             ResultSet rs = statement.executeQuery(sql)) {

            while (rs.next()) {
                transactions.add(mapTransaction(rs));
            }

        } catch (SQLException e) {
            throw new RuntimeException("Failed to retrieve transactions", e);
        }

        return transactions;
    }

    @Override
    public void clear() {
        String sql = "DELETE FROM transactions";

        try (Statement statement =
                     connectionProvider.getConnection().createStatement()) {

            statement.executeUpdate(sql);

        } catch (SQLException e) {
            throw new RuntimeException("Failed to clear transactions", e);
        }
    }

    private Transaction mapTransaction(ResultSet rs) throws SQLException {
        return new Transaction(
                rs.getString("transaction_id"),
                LocalDateTime.parse(rs.getString("timestamp")),
                rs.getInt("account_number"),
                TransactionType.valueOf(rs.getString("transaction_type")),
                rs.getDouble("amount"),
                rs.getDouble("balance_after"),
                rs.getString("status"),
                rs.getString("description"),
                rs.getInt("source_account"),
                rs.getInt("destination_account")
        );
    }
}
