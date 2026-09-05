package org.example;

public class TestAccountSubclasses {

    public static void main(String[] args) {

        System.out.println("=== Activity 7: Account Subclasses Test ===");


        // Savings Account
        SavingsAccount savingsAccount =
                new SavingsAccount(
                        1001,
                        "John Doe",
                        25,
                        10000,
                        1000,
                        4.0
                );

        System.out.println(
                "Savings Account Created: Balance Rs "
                        + savingsAccount.getBalance()
                        + " | Min Balance: Rs "
                        + savingsAccount.getMinBalance()
        );


        // Test Savings Interest
        System.out.println("\n--- Testing Savings Interest ---");

        double beforeInterest = savingsAccount.getBalance();

        savingsAccount.applyInterest();

        double afterInterest = savingsAccount.getBalance();

        System.out.println("Balance Before Interest: Rs " + beforeInterest);
        System.out.println("Balance After Interest: Rs " + afterInterest);


        // Current Account
        CurrentAccount currentAccount =
                new CurrentAccount(
                        1002,
                        "Alice Brown",
                        30,
                        15000,
                        25000
                );

        System.out.println(
                "\nCurrent Account Created: Overdraft Limit Rs "
                        + currentAccount.getOverdraftLimit()
        );


        // Test Current Account Setter
        currentAccount.setOverdraftLimit(30000);

        System.out.println(
                "Updated Overdraft Limit: Rs "
                        + currentAccount.getOverdraftLimit()
        );


        // Fixed Deposit Account
        FixedDepositAccount fixedDepositAccount =
                new FixedDepositAccount(
                        1003,
                        "Bob Wilson",
                        35,
                        20000,
                        12,
                        6.5
                );

        System.out.println(
                "\nFixed Deposit Created: Tenure "
                        + fixedDepositAccount.getTenureMonths()
                        + " months | Interest: "
                        + fixedDepositAccount.getInterestRate()
                        + "%"
        );


        // Test Fixed Deposit Maturity
        System.out.println("\n--- Testing Fixed Deposit Maturity ---");

        double maturityAmount =
                fixedDepositAccount.calculateMaturityAmount();

        System.out.println(
                "Maturity Amount: Rs " + maturityAmount
        );


        // Salary Account
        SalaryAccount salaryAccount =
                new SalaryAccount(
                        1004,
                        "Charlie Smith",
                        28,
                        10000,
                        "Infosys",
                        0
                );

        System.out.println(
                "\nSalary Account Created: Employer "
                        + salaryAccount.getEmployerName()
        );


        System.out.println("\nAll subclasses instantiated successfully!");
    }
}