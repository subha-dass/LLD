package org.example;

import java.util.concurrent.BlockingQueue;

public interface SchedulingStrategy {
    BlockingQueue<Task> createQueue(int capacity);
 }
