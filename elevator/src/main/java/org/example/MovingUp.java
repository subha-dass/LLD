package org.example;

public class MovingUp implements ElevetorState{
    @Override
    public void move(Elevator elevator) {
        System.out.println("Elevator moving Up::");
    }
}
