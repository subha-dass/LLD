package org.example;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");
        Inventory inv =
                new Inventory();



        inv.addProduct(
                ProductFactory.createFactory(1),
                1
        );



        VendingMachine machine =
                new VendingMachine(inv);



        machine.addObserver(
                new MaintenanceObserver()
        );



        machine.addObserver(
                new MaintenanceObserver()
        );



        machine.insertMoney(50);


        machine.selectItem(1);

        machine.dispanse();

    }
}