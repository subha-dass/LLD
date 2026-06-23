package org.example;

public class Deposit extends Transaction{
    protected double amount;
    protected AccountStrategy account;

    public Deposit(AccountStrategy account, double amount) {
        this.account = account;
        this.amount = amount;
    }

    @Override
    protected void verifyAccount() {
        System.out.println("Verifying account for deposit.");
    }

    @Override
    protected void process() {
        account.deposit(amount);
    }

    @Override
    protected void updateBalance() {
        System.out.println("Balance updated after deposit.");
    }

    @Override
    protected void generateReceipt() {
        System.out.println("Receipt generated for deposit.");
    }
}
