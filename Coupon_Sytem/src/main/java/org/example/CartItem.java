package org.example;

public class CartItem {
    int id;
    int price;
    ProductType productType;

    public CartItem(int id, int price, ProductType productType) {
        this.id = id;
        this.price = price;
        this.productType = productType;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public ProductType getProductType() {
        return productType;
    }

    public void setProductType(ProductType productType) {
        this.productType = productType;
    }
}
