package org.example;

public abstract class AbstractAccount {

    // ===== Fields =====

    private int accountNumber;
    private String name;
    private int age;
    private double balance;
    private String accountType;
    private String status;
    private Integer pin;


    // ===== Constructor =====

    public AbstractAccount(int accountNumber,
                           String name,
                           int age,
                           double initialBalance,
                           String accountType) {

        this.accountNumber = accountNumber;
        this.name = name;
        this.age = age;
        this.balance = initialBalance;
        this.accountType = accountType;
        this.status = "Active";
        this.pin = null;
    }


    // ===== Abstract Method =====

    public abstract void processDebit(double amount)
            throws AccountException;


    // ===== Common Methods =====

    public void deposit(double amount)
            throws InvalidAmountException,
            InactiveAccountException {

        if (!"Active".equals(status)) {
            throw new InactiveAccountException(
                    "Account is inactive"
            );
        }

        if (amount <= 0) {
            throw new InvalidAmountException(
                    "Deposit amount must be greater than zero"
            );
        }

        balance += amount;
    }

    public void withdraw(double amount, int pin)
            throws AccountException {

        validatePin(pin);

        if (!"Active".equals(status)) {
            throw new InactiveAccountException(
                    "Account is inactive"
            );
        }

        if (amount <= 0) {
            throw new InvalidAmountException(
                    "Withdrawal amount must be greater than zero"
            );
        }

        processDebit(amount);
    }

    public void validatePin(int pin)
            throws InvalidPinException {

        if (!hasPin() || !verifyPin(pin)) {
            throw new InvalidPinException(
                    "Invalid PIN"
            );
        }
    }


    public void changePin(int newPin)
            throws IllegalArgumentException {

        if (newPin < 1000 || newPin > 9999) {
            throw new IllegalArgumentException(
                    "PIN must be a 4-digit number"
            );
        }

        this.pin = newPin;
    }


    public void displayAccountInfo() {

        System.out.println(
                "Account #" + accountNumber
                        + " | " + name
                        + " | " + accountType
                        + " | Balance: ₹" + balance
                        + " | Status: " + status
        );
    }


    // ===== PIN Helpers =====

    public boolean verifyPin(int pin) {
        return hasPin() && this.pin == pin;
    }


    public boolean hasPin() {
        return this.pin != null;
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


    // ===== Protected Balance Setter =====

    protected void setBalance(double balance) {
        this.balance = balance;
    }
}