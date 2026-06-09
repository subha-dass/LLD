package org.example;

public class PoolMonitor implements Runnable{
    private CustomThreadPoolExecutor customThreadPoolExecutor;

    public PoolMonitor(CustomThreadPoolExecutor customThreadPoolExecutor) {
        this.customThreadPoolExecutor = customThreadPoolExecutor;
    }

    @Override
    public void run() {
        while (true){
            try {
                Thread.sleep(5000);
                System.out.println("Checking If thread need to increase or not");
                customThreadPoolExecutor.scaleDown();
            }catch (Exception e){
                System.out.println("Problem to running mionitor Thread::"+e.toString());
                break;
            }
        }
    }
}
