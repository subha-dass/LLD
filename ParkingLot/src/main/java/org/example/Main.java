package org.example;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");



            ParkingLot lot =
                    ParkingLot.getInstance();



            List<ParkingSpot> spots =
                    new ArrayList<>();


            spots.add(
                    new ParkingSpot(
                            1,
                            SpotType.CAR_SPOT
                    )
            );


            spots.add(
                    new ParkingSpot(
                            2,
                            SpotType.BIKE_SPOT
                    )
            );



            ParkingFloor floor =
                    new ParkingFloor(
                            1,
                            spots
                    );



            lot.addFloor(floor);




            Vehicle car =
                    VehicleFactory.createVehicle(
                            VehicleType.CAR,
                            "DL01AB1234"
                    );




            // COMMAND CREATED

            ParkVehicleCommand parkCommand =
                    new ParkVehicleCommand(
                            lot,
                            car
                    );



            ParkingInvoker invoker =
                    new ParkingInvoker();



            invoker.setCommand(
                    parkCommand
            );


            invoker.execute();



            ParkingTicket ticket =
                    parkCommand.getTicket();





            ExitVehicleCommand exitCommand =
                    new ExitVehicleCommand(
                            lot,
                            ticket
                    );



            invoker.setCommand(
                    exitCommand
            );


            invoker.execute();

    }
}