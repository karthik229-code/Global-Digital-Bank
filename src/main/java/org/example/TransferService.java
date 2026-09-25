package org.example;

public class TransferService {

    public static void transfer(
            AbstractAccount from,
            AbstractAccount to,
            double amount,
            int pin
    ) throws AccountException {

        if (from == null || to == null) {
            throw new AccountException(
                    "Source and destination accounts are required"
            );
        }

        if (!"Active".equals(from.getStatus())
                || !"Active".equals(to.getStatus())) {
            throw new InactiveAccountException(
                    "Both accounts must be active to transfer funds"
            );
        }

        if (!from.verifyPin(pin)) {
            throw new InvalidPinException("Incorrect PIN");
        }

        if (!from.canWithdraw(amount)) {
            throw new InsufficientBalanceException(
                    "Insufficient balance for transfer of Rs. " + amount
            );
        }

        from.resetDailyTransferIfNeeded();

        if (!from.canTransfer(amount)) {
            throw new AccountException(
                    "Daily transfer limit exceeded. Remaining today: Rs. "
                            + from.getRemainingDailyTransferLimit()
            );
        }

        from.withdraw(amount, pin);
        to.deposit(amount);
        from.updateDailyTransferTotal(amount);
    }
}