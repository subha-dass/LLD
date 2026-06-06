package org.example;

public class IdealState implements ElevetorState{
    @Override
    public void move(Elevator elevator) {
        System.out.println("Elevator is Ideal");
    }
}
