package org.example;

public class Account {
    private int accountNumber;
    private String name;
    private int age;
    private double balance;
    private String accountType;
    private String status;
    private Integer pin;
    private final int minSaving = 500;
    private final int minCurrent = 1000;

    public Account(int accountNumber,String name,int age,double initialBalance,String accountType) {
        this.accountNumber = accountNumber;
        this.name = name;
        this.age = (age >= 18 ? age : 18);
        this.accountType = (accountType.equals("Savings") || accountType.equals("Current")
                ? accountType
                : "Savings");
        this.balance = (this.accountType.equals("Savings")
                ? ((initialBalance < minSaving) ? minSaving : initialBalance)
                : ((initialBalance < minCurrent) ? minCurrent : initialBalance));
        this.status = "Active";
    }

    public boolean deposit(double amount) {
        if(amount <= 0 || status.equals("Inactive")) return false;
        balance += amount;
        return true;
    }

    public boolean withdraw(double amount,int pin) {
        if(amount > balance || amount <= 0 || status.equals("Inactive")) return false;
        if(hasPin() && verifyPin(pin)) {
            if((accountType.equals("Savings") && (balance-amount) >= minSaving) ||
                    accountType.equals("Current") && (balance-amount) >= minCurrent) {
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
        if(status.equals("Inactive")) return false;
        status = "Inactive";
        return true;
    }

    public boolean reopenAccount() {
        if(status.equals("Active")) return false;
        status = "Active";
        return true;
    }

    public boolean setPin(int pin) {
        if(pin > 9999 || pin < 1000) return false;
        this.pin = pin;
        return true;
    }

    public boolean verifyPin(int pin) {
        if(!hasPin()) return false;
        return pin == this.pin;
    }

    public boolean hasPin() {
        return this.pin != null;
    }
}
