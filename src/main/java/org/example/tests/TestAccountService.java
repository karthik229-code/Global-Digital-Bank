
package org.example.tests;

import org.example.*;

import java.util.List;

public class TestAccountService {

    public static void main(String[] args) throws Exception {

        System.out.println("==================================================");
        System.out.println("       ACTIVITY 19 - ACCOUNT SERVICE DEMO");
        System.out.println("==================================================");

        TransactionLogger logger =
                new TransactionLogger(new MemoryLogDestination());

        TransferService transferService = new TransferService();

        AccountService service =
                new AccountService(logger, transferService);

        IAccount john = service.openAccount(
                "SAVINGS", "John Doe", 25, 15000.0, 0
        );

        IAccount jane = service.openAccount(
                "SAVINGS", "Jane Smith", 30, 10000.0, 0
        );

        ((AbstractAccount) john).setPin(1234);
        ((AbstractAccount) jane).setPin(1234);

        int johnNumber = john.getAccountNumber();
        int janeNumber = jane.getAccountNumber();

        System.out.println("[STEP 13] Opened account: " + john);
        System.out.println("[STEP 13] Opened account: " + jane);

        service.deposit(johnNumber, 5000.0);
        service.withdraw(johnNumber, 2000.0, "1234");
        service.transfer(johnNumber, janeNumber, 1000.0, "1234");

        System.out.println("\n[STEP 18] Transaction History:");

        List<Transaction> history = service.getTransactionHistory();

        for (Transaction transaction : history) {
            System.out.println(transaction);
        }

        System.out.println("\n[STEP 17] Final Balances:");
        System.out.println("John balance: Rs. " + john.getBalance());
        System.out.println("Jane balance: Rs. " + jane.getBalance());

        System.out.println("\n[STEP 19] Error Handling Checks:");

        try {
            service.deposit(9999, 500.0);
            System.out.println("Deposit to missing account: FAIL");
        } catch (Exception exception) {
            System.out.println(
                    "Deposit to missing account caught: "
                            + exception.getMessage() + " [PASS]"
            );
        }

        try {
            service.withdraw(johnNumber, 500.0, "9999");
            System.out.println("Withdrawal with wrong PIN: FAIL");
        } catch (Exception exception) {
            System.out.println(
                    "Withdrawal with wrong PIN caught: "
                            + exception.getMessage() + " [PASS]"
            );
        }

        System.out.println("\nActivity 19 test completed.");
    }
}
