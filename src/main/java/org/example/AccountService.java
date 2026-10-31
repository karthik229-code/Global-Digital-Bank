
package org.example;

import org.example.command.DepositCommand;
import org.example.command.TransactionCommand;
import org.example.command.TransferCommand;
import org.example.command.WithdrawCommand;
import org.example.repository.AccountRepository;
import org.example.repository.TransactionRepository;
import org.example.repository.RepositoryFactory;

import java.util.List;

public class AccountService {

    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;
    private final TransactionLogger logger;
    private final TransferService transferService;

    private int nextAccountNumber = 1001;

    public AccountService(
            TransactionLogger logger,
            TransferService transferService) {

        this.accountRepository =
                RepositoryFactory.createAccountRepository();

        this.transactionRepository =
                RepositoryFactory.createTransactionRepository();

        this.logger = logger;
        this.transferService = transferService;
    }

    public IAccount openAccount(
            String type,
            String name,
            int age,
            double initialBalance,
            int tenureYears) throws AccountException {

        int accountNumber = accountRepository.nextAccountNumber();

        IAccount account = AccountFactory.createAccount(
                type,
                accountNumber,
                name,
                age,
                initialBalance,
                tenureYears
        );

        accountRepository.save(account);
        nextAccountNumber = accountNumber + 1;

        return account;
    }

    public void closeAccount(int accountNumber, String pin)
            throws AccountException {

        AbstractAccount account =
                requireAbstractAccount(getAccount(accountNumber));

        account.validatePin(parsePin(pin));
        account.closeAccount();

        accountRepository.update(account);
    }

    public Transaction deposit(int accountNumber, double amount)
            throws Exception {

        AbstractAccount account =
                requireAbstractAccount(getAccount(accountNumber));

        TransactionCommand command =
                new DepositCommand(account, amount);

        command.execute();

        Transaction transaction = command.getTransaction();
        transactionRepository.save(transaction);

        logger.log(command);

        return transaction;
    }

    public Transaction withdraw(
            int accountNumber,
            double amount,
            String pin) throws Exception {

        AbstractAccount account =
                requireAbstractAccount(getAccount(accountNumber));

        TransactionCommand command =
                new WithdrawCommand(account, amount, parsePin(pin));

        command.execute();

        Transaction transaction = command.getTransaction();
        transactionRepository.save(transaction);

        logger.log(command);

        return transaction;
    }

    public Transaction transfer(
            int fromAccount,
            int toAccount,
            double amount,
            String pin) throws Exception {

        AbstractAccount source =
                requireAbstractAccount(getAccount(fromAccount));

        AbstractAccount destination =
                requireAbstractAccount(getAccount(toAccount));

        TransactionCommand command = new TransferCommand(
                source,
                destination,
                amount,
                parsePin(pin)
        );

        command.execute();

        Transaction transaction = command.getTransaction();
        transactionRepository.save(transaction);

        logger.log(command);

        return transaction;
    }

    public IAccount getAccount(int accountNumber)
            throws AccountException {

        IAccount account = accountRepository.findById(accountNumber);

        if (account == null) {
            throw new AccountException(
                    "Account not found: " + accountNumber);
        }

        return account;
    }

    public List<IAccount> getAllAccounts() {
        return accountRepository.findAll();
    }

    public List<Transaction> getTransactionHistory() {
        return transactionRepository.findAll();
    }

    public int getNextAccountNumber() {
        return nextAccountNumber;
    }

    private AbstractAccount requireAbstractAccount(IAccount account)
            throws AccountException {

        if (!(account instanceof AbstractAccount)) {
            throw new AccountException("Unsupported account type.");
        }

        return (AbstractAccount) account;
    }

    private int parsePin(String pin) throws InvalidPinException {
        try {
            return Integer.parseInt(pin);
        } catch (NumberFormatException exception) {
            throw new InvalidPinException("Invalid PIN");
        }
    }
}
