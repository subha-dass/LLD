package org.example;

import java.util.List;

public class NearestSpotStrategy implements ParkingStrategy{

    public ParkingSpot findSpot(
            List<ParkingFloor> floors,
            Vehicle vehicle
    ){


        for(ParkingFloor floor:floors){

            ParkingSpot spot =
                    floor.findSpot(vehicle);


            if(spot!=null)
                return spot;
        }


        return null;
    }
}
