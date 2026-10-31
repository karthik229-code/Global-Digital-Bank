
package org.example.repository;

import org.example.Transaction;

import java.util.List;

public interface TransactionRepository {

    void save(Transaction transaction);

    List<Transaction> findByAccount(int accountNumber);

    List<Transaction> findAll();

    void clear();
}
