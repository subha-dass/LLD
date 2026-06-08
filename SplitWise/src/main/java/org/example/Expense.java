package org.example;

import java.util.List;

public class Expense {
    private String expenseId;
    private User paidBy;
    private double amount;
    private List<Split> splits;

    public Expense(String expenseId, User paidBy, double amount, List<Split> splits) {
        this.expenseId = expenseId;
        this.paidBy = paidBy;
        this.amount = amount;
        this.splits = splits;
    }

    public String getExpenseId() {
        return expenseId;
    }

    public void setExpenseId(String expenseId) {
        this.expenseId = expenseId;
    }

    public User getPaidBy() {
        return paidBy;
    }

    public void setPaidBy(User paidBy) {
        this.paidBy = paidBy;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public List<Split> getSplits() {
        return splits;
    }

    public void setSplits(List<Split> splits) {
        this.splits = splits;
    }
}
