package org.example;

public class TestTransactionModel {

    public static void main(String[] args) {

        System.out.println("============================================================");
        System.out.println(" ACTIVITY 16 — TRANSACTION MODEL TEST");
        System.out.println("============================================================");

        try {
            AbstractAccount acc1 = (AbstractAccount) AccountFactory.createAccount(
                    "SAVINGS",
                    1001,
                    "Rajesh Sharma",
                    30,
                    50000,
                    0
            );

            AbstractAccount acc2 = (AbstractAccount) AccountFactory.createAccount(
                    "SAVINGS",
                    1002,
                    "Priya Patel",
                    28,
                    20000,
                    0
            );

            acc1.setPin(1234);

            Transaction depositTransaction =
                    acc1.depositWithTransaction(5000);

            System.out.println(
                    "[STEP 10] Deposit Transaction: "
                            + depositTransaction
            );

            Transaction withdrawalTransaction =
                    acc1.withdrawWithTransaction(2000, 1234);

            System.out.println(
                    "[STEP 11] Withdrawal Transaction: "
                            + withdrawalTransaction
            );

            Transaction transferTransaction =
                    TransferService.transferWithTransaction(
                            acc1,
                            acc2,
                            1000,
                            1234
                    );

            System.out.println(
                    "[STEP 12] Transfer Transaction: "
                            + transferTransaction
            );

            acc1.deposit(1000);

            System.out.println(
                    "[STEP 13] Legacy Deposit +1000: "
            );

            acc1.displayAccountInfo();

        } catch (Exception e) {
            System.out.println(
                    "Unexpected error: "
                            + e.getClass().getSimpleName()
                            + ": "
                            + e.getMessage()
            );
        }
    }
}