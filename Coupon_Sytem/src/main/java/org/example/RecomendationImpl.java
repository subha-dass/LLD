package org.example;

import java.util.ArrayList;
import java.util.List;

public class RecomendationImpl implements RecomendationSystem{
    private List<Coupon> availableCoupon;

    public RecomendationImpl(List<Coupon> availableCoupon) {
        this.availableCoupon = availableCoupon;
    }

    @Override
    public List<Coupon> recomendedList(Order order) {
        List<Coupon> coupons=new ArrayList<>();
        for(Coupon coupon:availableCoupon){
            if(coupon.isApplicabale(order)){
                coupons.add(coupon);
            }
        }return coupons;
    }
}
