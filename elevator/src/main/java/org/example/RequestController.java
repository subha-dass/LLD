package org.example;

import java.util.ArrayList;
import java.util.List;

public class RequestController {
private List<Elevator> elevatorList;
private ElevatorStrategy elevatorStrategy;

    public RequestController(List<Elevator> elevatorList, ElevatorStrategy elevatorStrategy) {
        this.elevatorList = elevatorList;
        this.elevatorStrategy = elevatorStrategy;
    }

    public void submitRequest(Request request){
        Elevator elevator=elevatorStrategy.findBestElevator(elevatorList,request);

        System.out.println(
                "Assigned Elevator : "
                        + elevator.getId());

        Command command=new RequestCommand(elevator,request);
        command.execute();
    }

    public void processAllElevators() {

        for (Elevator elevator : elevatorList) {
            elevator.processRequest();
        }
    }
}
