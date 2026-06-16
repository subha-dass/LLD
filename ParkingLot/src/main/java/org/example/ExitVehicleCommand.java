package org.example;

public class ExitVehicleCommand implements Command{
    private ParkingLot parkingLot;


    private ParkingTicket ticket;



    public ExitVehicleCommand(
            ParkingLot parkingLot,
            ParkingTicket ticket
    ){

        this.parkingLot=parkingLot;
        this.ticket=ticket;
    }




    public void execute(){

        parkingLot.exit(ticket);

    }
}
