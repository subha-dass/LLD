package org.example;

import java.util.ArrayList;
import java.util.List;

public class ParkingLot {
    private static ParkingLot instance;


    private List<ParkingFloor> floors;



    private ParkingStrategy strategy;



    private ParkingLot(){

        floors=new ArrayList<>();

        strategy =
                new NearestSpotStrategy();
    }




    public static ParkingLot getInstance(){


        if(instance==null){

            synchronized(ParkingLot.class){

                if(instance==null){

                    instance =
                            new ParkingLot();
                }
            }
        }


        return instance;
    }




    public void addFloor(
            ParkingFloor floor
    ){

        floors.add(floor);
    }





    public ParkingTicket park(
            Vehicle vehicle
    ) {


        ParkingSpot spot =
                strategy.findSpot(
                        floors,
                        vehicle
                );


        if (spot == null)
            throw new RuntimeException(
                    "No spot available"
            );


        spot.park(vehicle);


        return new ParkingTicket(
                vehicle,
                spot
        );
    }
    public void exit(
            ParkingTicket ticket
    ){

        ticket.getSpot()
                .removeVehicle();


        System.out.println(
                "Vehicle removed"
        );
    }
}
