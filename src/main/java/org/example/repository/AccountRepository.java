package org.example.repository;

import org.example.IAccount;

import java.util.List;

public interface AccountRepository {

    // Create
    void save(IAccount account);

    // Read
    IAccount findById(int accountNumber);

    List<IAccount> findAll();

    // Update
    void update(IAccount account);

    // Delete
    void delete(int accountNumber);

    // Utility operations
    boolean exists(int accountNumber);

    int nextAccountNumber();

    // Atomic transfer persistence
    void transfer(IAccount sourceAccount, IAccount destinationAccount);
}