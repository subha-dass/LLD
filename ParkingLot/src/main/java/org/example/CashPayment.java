package org.example;

public class CashPayment implements PaymentStrategy{
    @Override
    public void pay(double amount) {
        System.out.println(
                "Cash paid "
                        +amount
        );
    }
}
