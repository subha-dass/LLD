package org.example;

import java.util.ArrayList;
import java.util.List;

public class VendingMachine {
    VendingState vendingState;
    PaymentStrategy paymentStrategy;
    Inventory inventory;

    int balance;

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public List<Observer> getObservers() {
        return observers;
    }

    public void setObservers(List<Observer> observers) {
        this.observers = observers;
    }

    List<Observer> observers=new ArrayList<>();

    public VendingMachine(Inventory inventory) {
        this.inventory = inventory;
        this.vendingState=new IdealState();
    }

    public void addObserver(Observer observer){
        observers.add(observer);
    }

    public VendingState getVendingState() {
        return vendingState;
    }

    public void setVendingState(VendingState vendingState) {
        this.vendingState = vendingState;
    }

    public PaymentStrategy getPaymentStrategy() {
        return paymentStrategy;
    }

    public void setPaymentStrategy(PaymentStrategy paymentStrategy) {
        this.paymentStrategy = paymentStrategy;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }

    public void insertMoney(int amount){
        vendingState.insertMoney(this,amount);
    }

    public void selectItem(int id){
        vendingState.selectProduct(this,id);
    }public void dispanse(){
        vendingState.dispense(this);
    }
}
