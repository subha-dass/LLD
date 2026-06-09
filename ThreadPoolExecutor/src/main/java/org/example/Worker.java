package org.example;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;

public class Worker implements Runnable{
    BlockingQueue<Task> queue;
    private AtomicBoolean isActive;

    public Worker(BlockingQueue<Task> queue) {
        this.queue = queue;
        this.isActive = new AtomicBoolean(false);
    }

    @Override
    public void run() {
        while (!isActive.get()){
            try{
                Runnable task=queue.take();
                task.run();
            }catch (InterruptedException ex){
                ex.printStackTrace();
                Thread.currentThread().interrupt();
                break;
            }
        }
    }

    public void stopWorker(){
        isActive.set(false);
        // Interrupt the current thread to unblock queue.take()
        Thread.currentThread().interrupt();
    }
}
