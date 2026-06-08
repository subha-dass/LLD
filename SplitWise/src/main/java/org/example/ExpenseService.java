package org.example;

public class ExpenseService {
    private BalanceSheet balanceSheet;
    private ExpenseNotifier notifier;

    public ExpenseService(
            BalanceSheet balanceSheet,
            ExpenseNotifier notifier) {

        this.balanceSheet = balanceSheet;
        this.notifier = notifier;
    }

    public void addExpense(Expense expense) {

        balanceSheet.updateBalance(
                expense.getPaidBy(),
                expense.getSplits());

        notifier.notifyObservers(
                "Expense Added: "
                        + expense.getAmount());
    }
}
