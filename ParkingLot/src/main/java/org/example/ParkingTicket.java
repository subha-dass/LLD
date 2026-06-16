package org.example;

public class ParkingTicket {
    private Vehicle vehicle;


    private ParkingSpot spot;



    public ParkingTicket(
            Vehicle vehicle,
            ParkingSpot spot
    ){

        this.vehicle=vehicle;
        this.spot=spot;
    }




    public ParkingSpot getSpot(){

        return spot;
    }
}
