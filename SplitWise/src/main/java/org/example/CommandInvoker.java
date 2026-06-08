package org.example;

public class CommandInvoker {
    public void invoke(Command command) {
        command.execute();
    }
}
