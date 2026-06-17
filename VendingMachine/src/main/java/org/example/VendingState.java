package org.example;

public interface VendingState {
    public void insertMoney(VendingMachine vendingMachine,int amount);
    public void selectProduct(VendingMachine vendingMachine,int id);
    public void dispense(VendingMachine vendingMachine);
}
