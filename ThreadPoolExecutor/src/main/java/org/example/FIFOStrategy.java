package org.example;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

public class FIFOStrategy implements SchedulingStrategy{
    @Override
    public BlockingQueue<Task> createQueue(int capacity) {
        return new LinkedBlockingDeque<>(capacity);
    }
}
