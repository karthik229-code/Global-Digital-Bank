package org.example.tests;

import org.example.Transaction;
import org.example.TransactionType;
import org.example.db.DataMigrator;
import org.example.repository.RepositoryFactory;
import org.example.repository.TransactionRepository;

import java.time.LocalDateTime;
import java.util.List;

public class TestJdbcTransactionRepository {

    private static TransactionRepository repository;

    public static void main(String[] args) {

        repository = RepositoryFactory.createTransactionRepository();

        testSaveAndFindAll();
        testFindByAccount();
        testClear();
        testMigration();

        System.out.println("\nAll tests completed.");
    }

    private static Transaction createTransaction(
            String id,
            int accountNumber,
            TransactionType type,
            double amount,
            double balance) {

        return new Transaction(
                id,
                LocalDateTime.now(),
                accountNumber,
                type,
                amount,
                balance,
                "SUCCESS",
                "Test transaction",
                accountNumber,
                0
        );
    }

    private static void testSaveAndFindAll() {

        repository.clear();

        Transaction transaction = createTransaction(
                "TEST-001",
                1001,
                TransactionType.DEPOSIT,
                1000,
                5000
        );

        repository.save(transaction);

        List<Transaction> transactions = repository.findAll();

        if (transactions.size() != 1) {
            throw new AssertionError("Save and findAll failed");
        }

        System.out.println("PASS: Save and findAll");
    }

    private static void testFindByAccount() {

        repository.clear();

        repository.save(createTransaction(
                "TEST-002", 1001,
                TransactionType.DEPOSIT, 500, 5500
        ));

        repository.save(createTransaction(
                "TEST-003", 1002,
                TransactionType.WITHDRAW, 200, 4800
        ));

        List<Transaction> transactions =
                repository.findByAccount(1001);

        if (transactions.size() != 1) {
            throw new AssertionError("findByAccount failed");
        }

        System.out.println("PASS: findByAccount");
    }

    private static void testClear() {

        repository.clear();

        if (!repository.findAll().isEmpty()) {
            throw new AssertionError("Clear failed");
        }

        System.out.println("PASS: Clear");
    }

    private static void testMigration() {

        int migrated = DataMigrator.migrateLegacyTransactions();

        System.out.println(
                "PASS: Migration executed. Migrated: " + migrated
        );
    }
}