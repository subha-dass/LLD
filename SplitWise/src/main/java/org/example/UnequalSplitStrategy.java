package org.example;

import java.util.ArrayList;
import java.util.List;

public class UnequalSplitStrategy implements SplitStrategy{
    @Override
    public List<Split> calculateSplit(User paidBy, double amount, List<User> users, double[] values) {
        List<Split> splits = new ArrayList<>();

        for(int i=0;i<users.size();i++) {
            splits.add(new Split(users.get(i), values[i]));
        }

        return splits;
    }
}
