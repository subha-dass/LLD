package org.example;

public class ParkingSpot {

    private int id;

    private SpotType type;

    private boolean occupied;

    private Vehicle vehicle;



    public ParkingSpot(
            int id,
            SpotType type
    ){

        this.id=id;
        this.type=type;
    }



    public boolean canFit(
            Vehicle vehicle
    ){

        return !occupied &&
                isSuitable(vehicle.getType());
    }



    private boolean isSuitable(
            VehicleType vehicleType
    ){

        if(vehicleType==VehicleType.CAR
                && type==SpotType.CAR_SPOT)
            return true;


        if(vehicleType==VehicleType.BIKE
                && type==SpotType.BIKE_SPOT)
            return true;


        return false;
    }



    public void park(
            Vehicle vehicle
    ){

        this.vehicle=vehicle;
        this.occupied=true;
    }



    public void removeVehicle(){

        this.vehicle=null;
        this.occupied=false;
    }



    public int getId(){

        return id;
    }
}
