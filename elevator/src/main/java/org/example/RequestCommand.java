package org.example;

public class RequestCommand implements Command{

Elevator elevator;
Request request;

    public RequestCommand(Elevator elevator,Request request) {
        this.elevator = elevator;
        this.request=request;
    }

public void execute(){
    elevator.addRequest(request);
}

}
