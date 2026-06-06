package org.example;

import java.util.Arrays;

public class Main {
    public static void main(String[] args) {

        System.out.println("Hello world!");

        Elevator e1 =
                new Elevator(1);

        Elevator e2 =
                new Elevator(2);
        RequestController requestController=new RequestController(Arrays.asList(e1,e2),new NearestElevatorStrategy());
        Request r1=new Request(3,8);
        Request r2=new Request(4,0);
        Request r3=new Request(1,5);

        requestController.submitRequest(r1);
        requestController.submitRequest(r2);
        requestController.submitRequest(r3);

        requestController.processAllElevators();
    }
}