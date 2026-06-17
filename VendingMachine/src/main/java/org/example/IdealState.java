package org.example;

public class IdealState implements VendingState{
    @Override
    public void insertMoney(VendingMachine vendingMachine, int amount) {
        System.out.println("Please Inserrt the money");
        vendingMachine.setBalance(amount);
        vendingMachine.setVendingState(new HasMoneyState());
    }

    @Override
    public void selectProduct(VendingMachine vendingMachine,int id) {
        System.out.println("Ur in Ideal State not possiblr to select Product");
    }

    @Override
    public void dispense(VendingMachine vendingMachine) {
        System.out.println("Ur in Ideal State not possible to dispense Product");
    }
}
