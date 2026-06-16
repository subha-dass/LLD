package org.example;

import java.util.List;

public class ParkingFloor {

    private int floorNumber;


    private List<ParkingSpot> spots;



    public ParkingFloor(
            int floorNumber,
            List<ParkingSpot> spots
    ){

        this.floorNumber=floorNumber;
        this.spots=spots;
    }




    public ParkingSpot findSpot(
            Vehicle vehicle
    ){

        for(ParkingSpot spot:spots){

            if(spot.canFit(vehicle))
                return spot;
        }

        return null;
    }
}
