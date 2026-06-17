package org.example;

public class MaintenanceObserver implements Observer{
    @Override
    public void update(String msg) {
        System.out.println(
                "MAINTENANCE : "+msg
        );
    }
}
