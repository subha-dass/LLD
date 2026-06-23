package org.example;

public interface AccountStrategy {
    double getAvailableBalance();
    void deposit(double amount);
    void withdraw(double amount);
}
