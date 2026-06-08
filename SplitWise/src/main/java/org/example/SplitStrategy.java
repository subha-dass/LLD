package org.example;

import java.util.List;

public interface SplitStrategy {
    List<Split> calculateSplit(
            User paidBy,
            double amount,
            List<User> users,
            double[] values);
}
