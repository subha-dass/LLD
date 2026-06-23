package org.example;

public class TransactionFactory {
    public static Transaction createTransaction(TransactionType type, AccountStrategy account, double amount, CashDispenser cashDispenser) {
        switch (type) {
            case BALANCE_INQUIRY:
                return new BalanceInquiry(account);
            case DEPOSIT_CASH:
                return new Deposit(account, amount);
            case WITHDRAW:
                return new Withdraw(account, amount, cashDispenser);
            case TRANSFER:
                // Assuming we have a destination account
                AccountStrategy destinationAccount = new CheckingAccount(); // for example
                return new Transfer(account, destinationAccount, amount);
            default:
                throw new IllegalArgumentException("Invalid transaction type");
        }
    }
}
