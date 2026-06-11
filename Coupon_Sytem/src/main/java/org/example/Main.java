package org.example;

import java.util.Arrays;

public class Main {
    public static void main(String[] args) {

        System.out.println("Hello world!");
        User user=new User(1,"sd");

        CartItem cartItem=new CartItem(1,30,ProductType.BRAVRAGES);
        CartItem cartItem1=new CartItem(2,500,ProductType.BRAVRAGES);

        Cart cart=new Cart(1,Arrays.asList(cartItem1,cartItem));

        Order order=new Order(user,cart,1);

        Rule rule=new ProductTypeRule(ProductType.BRAVRAGES);

        Coupon coupon=new Coupon(1,30,Arrays.asList(rule));


    }
}