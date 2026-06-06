package org.example;

public class Request {

int sourceFloor;
int destFloor;

    public Request(int sourceFloor, int destFloor) {
        this.sourceFloor = sourceFloor;
        this.destFloor = destFloor;
    }

    public int getSourceFloor() {
        return sourceFloor;
    }

    public void setSourceFloor(int sourceFloor) {
        this.sourceFloor = sourceFloor;
    }

    public int getDestFloor() {
        return destFloor;
    }

    public void setDestFloor(int destFloor) {
        this.destFloor = destFloor;
    }
}
