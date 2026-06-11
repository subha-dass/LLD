package org.example;

import java.util.List;

public class Cart {
    int id;
    List<CartItem> cartItemList;

    public Cart(int id, List<CartItem> cartItemList) {
        this.id = id;
        this.cartItemList = cartItemList;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<CartItem> getCartItemList() {
        return cartItemList;
    }

    public void setCartItemList(List<CartItem> cartItemList) {
        this.cartItemList = cartItemList;
    }
}
