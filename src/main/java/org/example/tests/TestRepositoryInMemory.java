
package org.example.tests;

import org.example.*;
import org.example.repository.*;

public class TestRepositoryInMemory {

    public static void main(String[] args) throws Exception {

        System.out.println("=== REPOSITORY TESTS ===");

        // 1. Test Account Repository
        AccountRepository accountRepo =
                new InMemoryAccountRepository();

        IAccount account = AccountFactory.createAccount(
                "SALARY", 1001, "Karthik", 20, 10000, 0
        );

        accountRepo.save(account);

        if (!accountRepo.exists(1001)) {
            throw new AssertionError("Account save failed");
        }

        System.out.println("Account save: PASSED");

        if (accountRepo.findById(1001) == null) {
            throw new AssertionError("Account retrieval failed");
        }

        System.out.println("Account retrieval: PASSED");

        accountRepo.update(account);

        if (accountRepo.findAll().size() != 1) {
            throw new AssertionError("Account findAll failed");
        }

        System.out.println("Account update/findAll: PASSED");

        int nextId = accountRepo.nextAccountNumber();

        if (nextId != 1002) {
            throw new AssertionError("Account ID generation failed");
        }

        System.out.println("Account ID generation: PASSED");

        accountRepo.delete(1001);

        if (accountRepo.exists(1001)) {
            throw new AssertionError("Account deletion failed");
        }

        System.out.println("Account deletion: PASSED");


        // 2. Test Transaction Repository
        TransactionRepository transactionRepo =
                new InMemoryTransactionRepository();

        Transaction transaction = new Transaction(
                Transaction.generateId(),
                java.time.LocalDateTime.now(),
                1001,
                TransactionType.DEPOSIT,
                500,
                10500,
                "SUCCESS",
                "Test deposit",
                0,
                1001
        );

        transactionRepo.save(transaction);

        if (transactionRepo.findAll().size() != 1) {
            throw new AssertionError("Transaction save failed");
        }

        System.out.println("Transaction save: PASSED");

        if (transactionRepo.findByAccount(1001).size() != 1) {
            throw new AssertionError("Transaction filtering failed");
        }

        System.out.println("Transaction filtering: PASSED");

        transactionRepo.clear();

        if (!transactionRepo.findAll().isEmpty()) {
            throw new AssertionError("Transaction clear failed");
        }

        System.out.println("Transaction clear: PASSED");


        // 3. Test AccountService Integration
        TransactionLogger logger =
                new TransactionLogger(new FileLogDestination());

        TransferService transferService = new TransferService();

        AccountService service =
                new AccountService(logger, transferService);

        IAccount serviceAccount = service.openAccount(
                "SALARY", "Test User", 20, 10000, 0
        );

        service.deposit(serviceAccount.getAccountNumber(), 500);

        if (service.getAllAccounts().size() != 1) {
            throw new AssertionError("Service account integration failed");
        }

        if (service.getTransactionHistory().size() != 1) {
            throw new AssertionError("Service transaction integration failed");
        }

        System.out.println("AccountService integration: PASSED");

        System.out.println("\nALL REPOSITORY TESTS PASSED!");
    }
}
