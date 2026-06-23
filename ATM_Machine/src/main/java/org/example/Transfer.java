package org.example;

public class Transfer extends Transaction{
    private AccountStrategy sourceAccount;
    private AccountStrategy destinationAccount;
    private double amount;

    public Transfer(AccountStrategy sourceAccount, AccountStrategy destinationAccount, double amount) {
        this.sourceAccount = sourceAccount;
        this.destinationAccount = destinationAccount;
        this.amount = amount;
    }

    @Override
    protected void verifyAccount() {
        System.out.println("Verifying accounts for transfer.");
    }

    @Override
    protected void process() {
        if (sourceAccount.getAvailableBalance() >= amount) {
            sourceAccount.withdraw(amount);
            destinationAccount.deposit(amount);
        } else {
            System.out.println("Insufficient funds for transfer.");
        }
    }

    @Override
    protected void updateBalance() {
        System.out.println("Balance updated after transfer.");
    }

    @Override
    protected void generateReceipt() {
        System.out.println("Receipt generated for transfer.");
    }
}
