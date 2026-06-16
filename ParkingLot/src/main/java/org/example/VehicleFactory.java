package org.example;

public class VehicleFactory {
    public static Vehicle createVehicle(
            VehicleType type,
            String number
    ) {

        switch (type) {

            case CAR:
                return new Car(number);


            case BIKE:
                return new Bike(number);


            default:
                throw new RuntimeException(
                        "Invalid vehicle"
                );
        }
    }
}
