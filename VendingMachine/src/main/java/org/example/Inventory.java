package org.example;

import java.util.HashMap;
import java.util.Map;

public class Inventory {
    Map<Integer,Product> products =
            new HashMap<>();


    Map<Integer,Integer> quantity =
            new HashMap<>();



    public void addProduct(
            Product p,
            int count
    ){

        products.put(
                p.getId(),
                p
        );

        quantity.put(
                p.getId(),
                count
        );
    }




    public boolean available(int id){

        return quantity
                .getOrDefault(id,0)>0;
    }





    public Product getProduct(int id){

        return products.get(id);
    }





    public void remove(int id){

        quantity.put(
                id,
                quantity.get(id)-1
        );
    }




    public int getQuantity(int id){

        return quantity.getOrDefault(id,0);
    }
}
