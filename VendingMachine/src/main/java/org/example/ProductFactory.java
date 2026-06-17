package org.example;

public class ProductFactory {

    public static  Product createFactory(int id){
        if(id==1)
            return new Product(
                    1,
                    "Coke",
                    40
            );


        if(id==2)
            return new Product(
                    2,
                    "Chips",
                    30
            );


        throw new RuntimeException(
                "Invalid product");
    }

}
