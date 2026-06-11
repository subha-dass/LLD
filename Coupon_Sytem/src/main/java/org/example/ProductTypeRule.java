package org.example;

public class ProductTypeRule implements Rule{
    private ProductType productType;

    public ProductTypeRule(ProductType productType) {
        this.productType = productType;
    }

    @Override
    public boolean check(Order order) {
        for(CartItem cartItem:order.getCart().getCartItemList()){
            if(cartItem.getProductType()!=productType)return false;
        }return true;
    }
}
