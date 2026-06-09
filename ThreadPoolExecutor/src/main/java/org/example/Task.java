package org.example;

public class Task implements Runnable,Comparable<Task>{
    private final int id;
    private final int priority;

    public Task(int id, int priority) {
        this.id = id;
        this.priority = priority;
    }

    @Override
    public int compareTo(Task o) {
        return Integer.compare(o.priority,this.priority);
    }

    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName()
        +"executing Task::"+
        id+"Priority::"+priority);
        try {
            Thread.sleep(2000);
        }catch (Exception e){
            e.printStackTrace();
        }
    }


}
