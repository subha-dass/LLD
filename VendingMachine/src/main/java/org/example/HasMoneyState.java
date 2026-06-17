package org.example;

public class HasMoneyState implements VendingState{
    @Override
    public void insertMoney(VendingMachine vendingMachine, int amount) {
        System.out.println("Ur in HasMoney State not possible to insert Product");
    }

    @Override
    public void selectProduct(VendingMachine vendingMachine,int id) {
        Inventory inventory=vendingMachine.getInventory();
        Product product=vendingMachine.getInventory().getProduct(id);
        System.out.println("Selected Product::"+product.getName());
        inventory.remove(id);
        vendingMachine.setVendingState(new DispenseState());
    }

    @Override
    public void dispense(VendingMachine vendingMachine) {
        System.out.println("Ur in HasMoney State not possible to dispanse Product");
    }
}
