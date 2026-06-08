package org.example;

public class AddExpenseCommand implements Command{
    private ExpenseService expenseService;
    private Expense expense;

    public AddExpenseCommand(ExpenseService expenseService, Expense expense) {
        this.expenseService = expenseService;
        this.expense = expense;
    }

    @Override
    public void execute() {
        expenseService.addExpense(expense);
    }
}
