package org.example;

public class CheckingAccount implements AccountStrategy{
    private int accountNumber;
    private double totalBalance;
    private double availableBalance;
    private String debitCardNumber;
    @Override
    public double getAvailableBalance() {
        return availableBalance;
    }

    @Override
    public void deposit(double amount) {
        availableBalance += amount;
    }

    @Override
    public void withdraw(double amount) {
        availableBalance -= amount;
    }
}
