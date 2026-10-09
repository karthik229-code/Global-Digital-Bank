
package org.example;

import org.example.command.DepositCommand;
import org.example.command.TransactionCommand;
import org.example.command.TransferCommand;
import org.example.command.WithdrawCommand;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class AccountService {

    private final Map<Integer, IAccount> accounts;
    private final TransactionLogger logger;
    private final TransferService transferService;
    private int nextAccountNumber = 1001;

    public AccountService(
            TransactionLogger logger,
            TransferService transferService) {

        this.accounts = new LinkedHashMap<>();
        this.logger = logger;
        this.transferService = transferService;
    }

    public IAccount openAccount(
            String type,
            String name,
            int age,
            double initialBalance,
            int tenureYears) throws AccountException {

        int accountNumber = nextAccountNumber;

        IAccount account = AccountFactory.createAccount(
                type,
                accountNumber,
                name,
                age,
                initialBalance,
                tenureYears
        );

        accounts.put(accountNumber, account);
        nextAccountNumber++;

        return account;
    }

    public void closeAccount(int accountNumber, String pin)
            throws AccountException {

        AbstractAccount account =
                requireAbstractAccount(getAccount(accountNumber));

        int numericPin = parsePin(pin);
        account.validatePin(numericPin);

        throw new UnsupportedOperationException(
                "Account closure is not implemented in AbstractAccount"
        );
    }

    public Transaction deposit(int accountNumber, double amount)
            throws Exception {

        AbstractAccount account =
                requireAbstractAccount(getAccount(accountNumber));

        TransactionCommand command =
                new DepositCommand(account, amount);

        command.execute();
        logger.log(command);

        return command.getTransaction();
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
        logger.log(command);

        return command.getTransaction();
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
        logger.log(command);

        return command.getTransaction();
    }

    public IAccount getAccount(int accountNumber)
            throws AccountException {

        IAccount account = accounts.get(accountNumber);

        if (account == null) {
            throw new AccountException(
                    "Account not found: " + accountNumber
            );
        }

        return account;
    }

    public List<IAccount> getAllAccounts() {
        return new ArrayList<>(accounts.values());
    }

    public List<Transaction> getTransactionHistory() throws Exception {

        List<Transaction> transactions = new ArrayList<>();

        for (TransactionCommand command : logger.readAll()) {
            if (command.getTransaction() != null) {
                transactions.add(command.getTransaction());
            }
        }

        return transactions;
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
