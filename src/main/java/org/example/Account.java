package org.example;

public class Account {

    private int accountNumber;
    private String name;
    private int age;
    private double balance;
    private String accountType;
    private String status;
    private Integer pin;

    private static final int MIN_AGE = 18;

    private static final double MIN_SAVING_BALANCE = 500;
    private static final double MIN_CURRENT_BALANCE = 1000;

    private static final int MIN_PIN = 1000;
    private static final int MAX_PIN = 9999;

    private static final String SAVINGS = "Savings";
    private static final String CURRENT = "Current";

    private static final String ACTIVE = "Active";
    private static final String INACTIVE = "Inactive";


    public Account(int accountNumber, String name, int age,
                   double initialBalance, String accountType) {

        this.accountNumber = accountNumber;
        this.name = name;
        this.age = (age >= MIN_AGE ? age : MIN_AGE);

        this.accountType = (SAVINGS.equals(accountType) || CURRENT.equals(accountType)
                ? accountType
                : SAVINGS);

        this.balance = (SAVINGS.equals(this.accountType)
                ? ((initialBalance < MIN_SAVING_BALANCE)
                   ? MIN_SAVING_BALANCE
                   : initialBalance)
                : ((initialBalance < MIN_CURRENT_BALANCE)
                   ? MIN_CURRENT_BALANCE
                   : initialBalance));

        this.status = ACTIVE;
    }


    public boolean deposit(double amount) {
        if (amount <= 0 || status.equals(INACTIVE)) {
            return false;
        }

        balance += amount;
        return true;
    }


    public boolean withdraw(double amount, Integer pin) {

        if (amount > balance || amount <= 0 || status.equals(INACTIVE)) {
            return false;
        }

        if (hasPin() && verifyPin(pin)) {

            if ((SAVINGS.equals(accountType) && (balance - amount) >= MIN_SAVING_BALANCE)
                    || (CURRENT.equals(accountType) && (balance - amount) >= MIN_CURRENT_BALANCE)) {

                balance -= amount;
                return true;
            }
        }

        return false;
    }


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


    public void setName(String name) {
        this.name = name;
    }

    public void setAge(int age) {
        this.age = age;
    }


    public boolean closeAccount() {
        if (status.equals(INACTIVE)) {
            return false;
        }

        status = INACTIVE;
        return true;
    }


    public boolean reopenAccount() {
        if (status.equals(ACTIVE)) {
            return false;
        }

        status = ACTIVE;
        return true;
    }


    public boolean setPin(int pin) {
        if (pin < MIN_PIN || pin > MAX_PIN) {
            return false;
        }

        this.pin = pin;
        return true;
    }


    public boolean verifyPin(int pin) {
        if (!hasPin()) {
            return false;
        }

        return pin == this.pin;
    }


    public boolean hasPin() {
        return this.pin != null;
    }
}