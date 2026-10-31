package org.example.db;

import org.example.DataAccessException;
import org.example.Transaction;
import org.example.command.TransactionCommand;
import org.example.FileLogDestination;
import org.example.TransactionLogger;
import org.example.repository.JdbcTransactionRepository;
import org.example.repository.RepositoryFactory;
import org.example.repository.TransactionRepository;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class DataMigrator {

    public static int migrateLegacyTransactions() {

        try {
            // Read transactions from the legacy serialized file
            TransactionLogger logger =
                    new TransactionLogger(new FileLogDestination());

            List<TransactionCommand> commands = logger.readAll();

            // Connect to SQLite
            TransactionRepository repository =
                    new JdbcTransactionRepository(
                            RepositoryFactory.getConnectionProvider()
                    );

            // Avoid migrating duplicate transactions
            Set<String> existingIds = new HashSet<>();

            for (Transaction transaction : repository.findAll()) {
                existingIds.add(transaction.getTransactionId());
            }

            int migratedCount = 0;

            for (TransactionCommand command : commands) {

                Transaction transaction = command.getTransaction();

                if (transaction == null) {
                    continue;
                }

                if (existingIds.contains(transaction.getTransactionId())) {
                    continue;
                }

                repository.save(transaction);
                existingIds.add(transaction.getTransactionId());
                migratedCount++;
            }

            System.out.println(
                    "Migration completed. Transactions migrated: "
                            + migratedCount
            );

            return migratedCount;

        } catch (Exception e) {
            throw new DataAccessException(
                    "Failed to migrate legacy transactions", e
            );
        }
    }
}