package org.example;

public class ParkingInvoker {
    private Command command;


    public void setCommand(
            Command command
    ) {

        this.command = command;
    }


    public void execute() {

        command.execute();

    }
}
