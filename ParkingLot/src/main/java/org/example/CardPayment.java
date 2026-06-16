package org.example;

public class CardPayment implements PaymentStrategy{
    public void pay(double amount){

        System.out.println(
                "Card paid "
                        +amount
        );
    }
}
