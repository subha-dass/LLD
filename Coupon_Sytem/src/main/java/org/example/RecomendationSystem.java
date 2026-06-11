package org.example;

import java.util.List;

public interface RecomendationSystem {

    public List<Coupon> recomendedList(Order order);
}
