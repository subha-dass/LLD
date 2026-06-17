package org.example;

public class DispenseState implements VendingState{
    @Override
    public void insertMoney(VendingMachine vendingMachine, int amount) {
        System.out.println("Ur in Dispense State not possible to insert Product");
    }

    @Override
    public void selectProduct(VendingMachine vendingMachine,int id) {
        System.out.println("Ur in Dispense State not possible to insert Product");

    }

    @Override
    public void dispense(VendingMachine vendingMachine) {

        System.out.println("Dispenseing the product");
        System.out.println("Product Dispensed");
        vendingMachine.setVendingState(new IdealState());
        System.out.println("Again setting up in Ideal Set");
    }
}
