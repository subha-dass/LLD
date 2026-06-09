package org.example;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BalanceSheet {

    private Map<User,
            Map<User, Double>> balances
            = new HashMap<>();

    public void updateBalance(
            User paidBy,
            List<Split> splits) {

        for(Split split : splits) {
        //System.out.println("Spliting with user::"+split.getUser());
            if(split.getUser().equals(paidBy))
                continue;

            balances
                    .computeIfAbsent(
                            split.getUser(),
                            k -> new HashMap<>())
                    .merge(
                            paidBy,
                            split.getAmount(),
                            Double::sum);
        }
    }

    public void showBalances() {
        //System.out.println("Balances size::" +balances.size());
        for(User debtor : balances.keySet()) {
        //System.out.println("Balances");

            for(Map.Entry<User, Double> entry :
                    balances.get(debtor).entrySet()) {

                System.out.println(
                        debtor.getName()
                                + " owes "
                                + entry.getKey().getName()
                                + " ₹"
                                + entry.getValue());
            }
        }}
}