package org.example;

public class CreditCard implements PaymentStrategy{
    @Override
    public boolean pay(double amount) {
        System.out.println(
                "Card payment "+amount
        );return  true;
    }
}
