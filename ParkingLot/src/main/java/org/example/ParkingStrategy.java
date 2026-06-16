package org.example;

import java.util.List;

interface ParkingStrategy {
    ParkingSpot findSpot(
            List<ParkingFloor> floors,
            Vehicle vehicle
    );
}
