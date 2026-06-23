package org.example;

public class Withdraw extends Transaction{
    private double amount;
    private AccountStrategy account;
    private CashDispenser cashDispenser;

    public Withdraw(AccountStrategy account, double amount, CashDispenser cashDispenser) {
        this.account = account;
        this.amount = amount;
        this.cashDispenser = cashDispenser;
    }

    @Override
    protected void verifyAccount() {
        System.out.println("Verifying account for withdrawal.");
    }

    @Override
    protected void process() {
        if (account.getAvailableBalance() >= amount) {
            account.withdraw(amount);
            cashDispenser.dispenseCash(amount);
        } else {
            System.out.println("Insufficient funds or ATM can't dispense cash.");
        }
    }

    @Override
    protected void updateBalance() {
        System.out.println("Balance updated after withdrawal.");
    }

    @Override
    protected void generateReceipt() {
        System.out.println("Receipt generated for withdrawal.");
    }
}
