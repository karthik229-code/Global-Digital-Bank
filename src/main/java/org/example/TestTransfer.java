package org.example;

public class TestTransfer {

    public static void main(String[] args) {

        System.out.println("============================================================");
        System.out.println(" ACTIVITY 15 — TRANSFER WITH DAILY LIMITS");
        System.out.println("============================================================");

        try {
            AbstractAccount acc1 = (AbstractAccount) AccountFactory.createAccount(
                    "SAVINGS",
                    1001,
                    "Rajesh Sharma",
                    30,
                    100000,
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

            System.out.println(
                    "[STEP 9] Account #1001 | Rajesh Sharma (30 yrs, Tenure: "
                            + acc1.getTenureYears()
                            + " yrs) | Savings | Rs. "
                            + acc1.getBalance()
                            + " | Active"
            );

            System.out.println(
                    "[STEP 9] Account #1002 | Priya Patel (28 yrs, Tenure: "
                            + acc2.getTenureYears()
                            + " yrs) | Savings | Rs. "
                            + acc2.getBalance()
                            + " | Active"
            );

            TransferService.transfer(acc1, acc2, 5000, 1234);

            System.out.println(
                    "[STEP 10] Transfer Rs. 5,000: SUCCESS | acc1 = Rs. "
                            + acc1.getBalance()
                            + " | acc2 = Rs. "
                            + acc2.getBalance()
            );

            try {
                TransferService.transfer(acc1, acc2, 100000, 1234);
            } catch (InsufficientBalanceException e) {
                System.out.println(
                        "[STEP 11] Caught InsufficientBalanceException: "
                                + e.getMessage()
                );
            }

            System.out.println(
                    "[STEP 12] Daily limit for acc1: Rs. "
                            + acc1.getDailyTransferLimit()
            );

            int transferNumber = 1;

            while (true) {
                try {
                    TransferService.transfer(
                            acc1,
                            acc2,
                            20000,
                            1234
                    );

                    System.out.println(
                            "  Transfer #" + transferNumber
                                    + " of Rs. 20,000: SUCCESS | used today = Rs. "
                                    + acc1.getDailyTransferTotal()
                    );

                    transferNumber++;

                } catch (AccountException e) {
                    System.out.println(
                            "[STEP 12] Caught AccountException: "
                                    + e.getMessage()
                    );
                    break;
                }
            }

            System.out.println(
                    "[STEP 13] Used today: Rs. "
                            + acc1.getDailyTransferTotal()
                            + " | Remaining: Rs. "
                            + acc1.getRemainingDailyTransferLimit()
            );

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