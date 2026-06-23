package org.example;

public class ATM {

    private CashDispenser cashDispenser;
    public ATM(CashDispenser cashDispenser) {
        this.cashDispenser = cashDispenser;

    }

    public void executeTransaction(Transaction transaction) {
        transaction.executeTransaction();
    }
}
