package org.example;

public class SalaryAccount extends Account {

    private String employerName;
    private int inactiveMonths;

    public SalaryAccount(int accountNumber, String name, int age,
                         double initialBalance,
                         String employerName, int inactiveMonths) {

        super(accountNumber, name, age, initialBalance, "SALARY");

        this.employerName = employerName;
        this.inactiveMonths = inactiveMonths;
    }

    public String getEmployerName() {
        return employerName;
    }

    public int getInactiveMonths() {
        return inactiveMonths;
    }
}