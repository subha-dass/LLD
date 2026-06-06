package org.example;

public class MovingDown implements ElevetorState{

    @Override
    public void move(Elevator elevator) {
        System.out.println("Eleveator moving Down");
    }
}
