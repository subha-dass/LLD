package org.example;

public abstract class Transaction {
    protected int transactionId;
    protected  long creationTime;
    protected TransactionType transactionType;

    public final void executeTransaction(){
        verifyAccount();
        process();
        updateBalance();
        generateReceipt();
    }

    protected abstract void verifyAccount();
    protected abstract void process();
    protected abstract void updateBalance();
    protected abstract void generateReceipt();

}
