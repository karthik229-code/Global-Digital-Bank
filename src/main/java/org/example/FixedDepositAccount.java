package org.example;

public class FixedDepositAccount extends Account {

    private int tenureMonths;
    private double interestRate;

    public FixedDepositAccount(int accountNumber, String name, int age,
                               double initialBalance,
                               int tenureMonths, double interestRate) {

        super(accountNumber, name, age, initialBalance, "FIXED_DEPOSIT");

        this.tenureMonths = tenureMonths;
        this.interestRate = interestRate;
    }

    public double calculateMaturityAmount() {

        double years = tenureMonths / 12.0;

        return getBalance() *
                Math.pow(1 + interestRate / 100, years);
    }

    public int getTenureMonths() {
        return tenureMonths;
    }

    public double getInterestRate() {
        return interestRate;
    }
}