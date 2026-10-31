package org.example.repository;

import org.example.IAccount;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InMemoryAccountRepository implements AccountRepository {

    private final Map<Integer, IAccount> accounts = new HashMap<>();

    private int nextId = 1001;

    @Override
    public void save(IAccount account) {
        accounts.put(account.getAccountNumber(), account);

        if (account.getAccountNumber() >= nextId) {
            nextId = account.getAccountNumber() + 1;
        }
    }

    @Override
    public IAccount findById(int accountNumber) {
        return accounts.get(accountNumber);
    }

    @Override
    public List<IAccount> findAll() {
        return new ArrayList<>(accounts.values());
    }

    @Override
    public void update(IAccount account) {
        accounts.put(account.getAccountNumber(), account);
    }

    @Override
    public void delete(int accountNumber) {
        accounts.remove(accountNumber);
    }

    @Override
    public boolean exists(int accountNumber) {
        return accounts.containsKey(accountNumber);
    }

    @Override
    public int nextAccountNumber() {
        return nextId++;
    }
}
