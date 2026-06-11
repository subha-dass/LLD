package org.example;

import java.util.List;

public class Coupon {
    int id;
    int discount;
    private List<Rule> ruleList;

    public Coupon(int id, int discount, List<Rule> ruleList) {
        this.id = id;
        this.discount = discount;
        this.ruleList = ruleList;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }

    public List<Rule> getRuleList() {
        return ruleList;
    }

    public void setRuleList(List<Rule> ruleList) {
        this.ruleList = ruleList;
    }

    public synchronized  boolean  isApplicabale(Order order){
        for(Rule rule:ruleList){
            if(rule.check(order))return  true;
        }return false;
    }
}
