package org.example;

public class Account {

    // ===== Constants =====
    private static final double MIN_BALANCE_SAVINGS = 500.0;
    private static final double MIN_BALANCE_CURRENT = 1000.0;

    private static final int MIN_AGE = 18;
    private static final int MIN_PIN = 1000;
    private static final int MAX_PIN = 9999;

    private static final String SAVINGS = "Savings";
    private static final String CURRENT = "Current";

    private static final String ACTIVE = "Active";
    private static final String INACTIVE = "Inactive";


    // ===== Fields =====
    private int accountNumber;
    private String name;
    private int age;
    private double balance;
    private String accountType;
    private String status;
    private Integer pin;


    // ===== Constructor =====
    public Account(int accountNumber, String name, int age,
                   double initialBalance, String accountType)
            throws IllegalArgumentException {

        if (age < MIN_AGE) {
            throw new IllegalArgumentException("Age must be at least 18");
        }

        if (!SAVINGS.equals(accountType) && !CURRENT.equals(accountType)) {
            throw new IllegalArgumentException("Invalid account type");
        }

        double minimumBalance = SAVINGS.equals(accountType)
                ? MIN_BALANCE_SAVINGS
                : MIN_BALANCE_CURRENT;

        if (initialBalance < minimumBalance) {
            throw new IllegalArgumentException(
                    "Initial balance must be at least " + minimumBalance
            );
        }

        this.accountNumber = accountNumber;
        this.name = name;
        this.age = age;
        this.balance = initialBalance;
        this.accountType = accountType;
        this.status = ACTIVE;
        this.pin = null;
    }


    // ===== Business Methods =====

    public void deposit(double amount)
            throws InvalidAmountException, InactiveAccountException {

        validateActive();

        if (amount <= 0) {
            throw new InvalidAmountException(
                    "Deposit amount must be greater than zero"
            );
        }

        balance += amount;
    }


    public void withdraw(double amount, int pin)
            throws InvalidAmountException,
            InsufficientBalanceException,
            MinimumBalanceViolationException,
            InactiveAccountException,
            InvalidPinException {

        validateActive();

        if (!hasPin() || !verifyPin(pin)) {
            throw new InvalidPinException("Invalid PIN");
        }

        if (amount <= 0) {
            throw new InvalidAmountException(
                    "Withdrawal amount must be greater than zero"
            );
        }

        if (amount > balance) {
            throw new InsufficientBalanceException(
                    "Insufficient balance"
            );
        }

        double remainingBalance = balance - amount;

        if (remainingBalance < getMinimumBalance()) {
            throw new MinimumBalanceViolationException(
                    "Withdrawal would violate minimum balance requirement"
            );
        }

        balance -= amount;
    }


    // ===== Account Status Management =====

    public void closeAccount() throws IllegalStateException {

        if (status.equals(INACTIVE)) {
            throw new IllegalStateException(
                    "Account is already inactive"
            );
        }

        status = INACTIVE;
    }


    public void reopenAccount() throws IllegalStateException {

        if (status.equals(ACTIVE)) {
            throw new IllegalStateException(
                    "Account is already active"
            );
        }

        status = ACTIVE;
    }


    // ===== PIN Management =====

    public void setPin(int pin) throws IllegalArgumentException {

        if (pin < MIN_PIN || pin > MAX_PIN) {
            throw new IllegalArgumentException(
                    "PIN must be a 4-digit number"
            );
        }

        this.pin = pin;
    }


    public boolean verifyPin(int pin) {
        return hasPin() && this.pin == pin;
    }


    public boolean hasPin() {
        return this.pin != null;
    }


    // ===== Helper Methods =====

    private double getMinimumBalance() {

        return SAVINGS.equals(accountType)
                ? MIN_BALANCE_SAVINGS
                : MIN_BALANCE_CURRENT;
    }


    private void validateActive()
            throws InactiveAccountException {

        if (status.equals(INACTIVE)) {
            throw new InactiveAccountException(
                    "Account is inactive"
            );
        }
    }


    // ===== Getters =====

    public int getAccountNumber() {
        return accountNumber;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public double getBalance() {
        return balance;
    }

    public String getAccountType() {
        return accountType;
    }

    public String getStatus() {
        return status;
    }
}