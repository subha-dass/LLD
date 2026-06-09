package org.example;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.PriorityBlockingQueue;

public class PriorityStrategy implements SchedulingStrategy{
    @Override
    public BlockingQueue<Task> createQueue(int capacity) {
        return new PriorityBlockingQueue<>(capacity);
    }
}
