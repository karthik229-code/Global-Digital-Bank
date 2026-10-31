package org.example;

import org.example.command.DepositCommand;
import org.example.command.WithdrawCommand;
import org.example.command.TransferCommand;
import org.example.repository.AccountRepository;
import org.example.repository.TransactionRepository;
import org.example.repository.RepositoryFactory;

import java.util.List;

public class AccountService {

    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;
    private final TransactionLogger logger;

    public AccountService(
            TransactionLogger logger,
            TransferService transferService
    ) {
        this.logger = logger;

        this.accountRepository =
                RepositoryFactory.createAccountRepository();

        this.transactionRepository =
                RepositoryFactory.createTransactionRepository();
    }

    public IAccount openAccount(
            String accountType,
            String name,
            int age,
            double initialBalance,
            int tenureYears
    ) throws Exception {

        int accountNumber = accountRepository.nextAccountNumber();

        IAccount account = AccountFactory.createAccount(
                accountType,
                accountNumber,
                name,
                age,
                initialBalance,
                tenureYears
        );

        accountRepository.save(account);

        return account;
    }

    public void updateAccount(IAccount account) {
        accountRepository.update(account);
    }

    public void closeAccount(
            int accountNumber,
            String pin
    ) throws Exception {

        AbstractAccount account = getAbstractAccount(accountNumber);

        int numericPin = parsePin(pin);
        account.validatePin(numericPin);

        account.closeAccount();
        accountRepository.update(account);
    }

    public Transaction deposit(
            int accountNumber,
            double amount
    ) throws Exception {

        AbstractAccount account = getAbstractAccount(accountNumber);

        DepositCommand command =
                new DepositCommand(account, amount);

        command.execute();

        Transaction transaction = command.getTransaction();

        accountRepository.update(account);
        transactionRepository.save(transaction);
        logger.log(command);

        return transaction;
    }

    public Transaction withdraw(
            int accountNumber,
            double amount,
            String pin
    ) throws Exception {

        AbstractAccount account = getAbstractAccount(accountNumber);

        int numericPin = parsePin(pin);

        WithdrawCommand command =
                new WithdrawCommand(account, amount, numericPin);

        command.execute();

        Transaction transaction = command.getTransaction();

        accountRepository.update(account);
        transactionRepository.save(transaction);
        logger.log(command);

        return transaction;
    }

    public Transaction transfer(
            int sourceAccountNumber,
            int destinationAccountNumber,
            double amount,
            String pin
    ) throws Exception {

        AbstractAccount sourceAccount =
                getAbstractAccount(sourceAccountNumber);

        AbstractAccount destinationAccount =
                getAbstractAccount(destinationAccountNumber);

        int numericPin = parsePin(pin);

        TransferCommand command = new TransferCommand(
                sourceAccount,
                destinationAccount,
                amount,
                numericPin
        );

        // Validate and perform the transfer in the account objects.
        command.execute();

        Transaction transaction = command.getTransaction();

        // Persist both account balances atomically.
        accountRepository.transfer(sourceAccount, destinationAccount);

        // Save transaction history after the balance transfer succeeds.
        transactionRepository.save(transaction);

        logger.log(command);

        return transaction;
    }

    public IAccount getAccount(int accountNumber) {
        return accountRepository.findById(accountNumber);
    }

    public List<IAccount> getAllAccounts() {
        return accountRepository.findAll();
    }

    public List<Transaction> getTransactionHistory() {
        return transactionRepository.findAll();
    }

    public List<Transaction> getTransactionHistory(int accountNumber) {
        return transactionRepository.findByAccount(accountNumber);
    }

    public int getNextAccountNumber() {
        return accountRepository.nextAccountNumber();
    }

    private AbstractAccount getAbstractAccount(int accountNumber)
            throws Exception {

        IAccount account = accountRepository.findById(accountNumber);

        if (account == null) {
            throw new Exception("Account not found.");
        }

        if (!(account instanceof AbstractAccount)) {
            throw new Exception("Invalid account type.");
        }

        return (AbstractAccount) account;
    }

    private int parsePin(String pin) throws Exception {
        try {
            return Integer.parseInt(pin);
        } catch (NumberFormatException exception) {
            throw new Exception("Invalid PIN.");
        }
    }
}