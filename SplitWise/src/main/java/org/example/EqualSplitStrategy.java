package org.example;

import java.util.ArrayList;
import java.util.List;

public class EqualSplitStrategy implements SplitStrategy {

    @Override
    public List<Split> calculateSplit(User paidBy, double amount, List<User> users, double[] values) {
        double share = amount / users.size();

        List<Split> splits = new ArrayList<>();

        for(User user : users) {
            splits.add(new Split(user, share));
        }

        return splits;
    }
}
