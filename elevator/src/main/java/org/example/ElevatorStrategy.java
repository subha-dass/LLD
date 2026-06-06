package org.example;

import java.util.List;

public interface ElevatorStrategy {
    Elevator findBestElevator(List<Elevator> elevators,Request request);
}
