package org.example;

public class SplitwiseFacade {
    private ExpenseService expenseService;
    private CommandInvoker invoker;

    public SplitwiseFacade(
            ExpenseService expenseService,
            CommandInvoker invoker) {

        this.expenseService = expenseService;
        this.invoker = invoker;
    }

    public void addExpense(
            Expense expense) {

        Command command =
                new AddExpenseCommand(
                        expenseService,
                        expense);

        invoker.invoke(command);
    }
}
