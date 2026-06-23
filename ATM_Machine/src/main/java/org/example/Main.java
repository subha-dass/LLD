package org.example;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");
        AccountStrategy checkingAccount = new CheckingAccount();
        CashDispenser cashDispenser = new CashDispenser();
        ATM atm = new ATM(cashDispenser);

        // Create transaction using Factory Method
        Transaction transaction = TransactionFactory.createTransaction(TransactionType.WITHDRAW, checkingAccount, 100.0, cashDispenser);

        // Execute the transaction
        atm.executeTransaction(transaction);
    }
}