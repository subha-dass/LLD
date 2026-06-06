package org.example;

import java.util.List;

public class NearestElevatorStrategy implements ElevatorStrategy{

    @Override
    public Elevator findBestElevator(List<Elevator> elevators, Request request) {
        int maxi=(int)1e9;
        Elevator mindis=null;
        for(Elevator elevator:elevators){
            int dis=Math.abs(elevator.getCurrentFloor()-request.sourceFloor);
            if(dis<maxi){
                maxi=dis;
                mindis=elevator;
            }

        }return mindis;
    }
}
