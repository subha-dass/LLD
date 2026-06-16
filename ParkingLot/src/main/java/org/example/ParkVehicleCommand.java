package org.example;

public class ParkVehicleCommand implements Command{
    private ParkingLot parkingLot;


    private Vehicle vehicle;


    private ParkingTicket ticket;



    public ParkVehicleCommand(
            ParkingLot parkingLot,
            Vehicle vehicle
    ){

        this.parkingLot=parkingLot;
        this.vehicle=vehicle;
    }





    public void execute(){


        ticket =
                parkingLot.park(vehicle);



        System.out.println(
                "Vehicle parked at spot "
                        + ticket.getSpot().getId()
        );

    }




    public ParkingTicket getTicket(){

        return ticket;
    }
}
