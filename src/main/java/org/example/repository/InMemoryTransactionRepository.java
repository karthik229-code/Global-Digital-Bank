
package org.example.repository;

import org.example.Transaction;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class InMemoryTransactionRepository
        implements TransactionRepository {

    private final List<Transaction> transactions = new ArrayList<>();

    @Override
    public void save(Transaction transaction) {
        transactions.add(transaction);
    }

    @Override
    public List<Transaction> findByAccount(int accountNumber) {
        return transactions.stream()
                .filter(transaction ->
                        transaction.getAccountNumber() == accountNumber)
                .collect(Collectors.toList());
    }

    @Override
    public List<Transaction> findAll() {
        return new ArrayList<>(transactions);
    }

    @Override
    public void clear() {
        transactions.clear();
    }
}
