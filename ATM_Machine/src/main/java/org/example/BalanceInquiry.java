package org.example;

public class BalanceInquiry extends Transaction {
    private int accountId;
    private AccountStrategy account;

    public BalanceInquiry(AccountStrategy account) {
        this.account = account;
    }

    @Override
    protected void verifyAccount() {
        System.out.println("Verifying account " + accountId);
    }

    @Override
    protected void process() {
        System.out.println("Balance: " + account.getAvailableBalance());
    }

    @Override
    protected void updateBalance() {
        // No balance update needed for balance inquiry
    }

    @Override
    protected void generateReceipt() {
        System.out.println("Receipt generated for Balance Inquiry");
    }
}
