package org.example;

import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");
        // Singleton UserManager
        UserManager userManager = UserManager.getInstance();

        User alice = new User("U1", "Alice", "alice@gmail.com");
        User bob = new User("U2", "Bob", "bob@gmail.com");
        User charlie = new User("U3", "Charlie", "charlie@gmail.com");

        userManager.addUser(alice);
        userManager.addUser(bob);
        userManager.addUser(charlie);

        // Create Group
        Group group = new Group(
                "G1",
                "Goa Trip",
                Arrays.asList(alice, bob, charlie));

        // Observer Setup
        ExpenseNotifier notifier = new ExpenseNotifier();

        notifier.subscribe(new UserObserver(alice));
        notifier.subscribe(new UserObserver(bob));
        notifier.subscribe(new UserObserver(charlie));

        // Services
        BalanceSheet balanceSheet = new BalanceSheet();

        ExpenseService expenseService =
                new ExpenseService(
                        balanceSheet,
                        notifier);

        // Facade
        SplitwiseFacade facade =
                new SplitwiseFacade(
                        expenseService,
                        new CommandInvoker());

        // Strategy via Factory
        SplitStrategy strategy =
                SplitStrategyFactory.getStrategy(
                        ExpenseType.EQUAL);
        System.out.println("Group user::"+group.getUsers());
        List<Split> splits =
                strategy.calculateSplit(
                        alice,
                        3000,
                        group.getUsers(),
                        null);

        Expense expense = new Expense("EXP1", alice, 3000, splits);

        // Add Expense
        facade.addExpense(expense);

        System.out.println();
        System.out.println("----- BALANCES -----");

        balanceSheet.showBalances();
    }
}