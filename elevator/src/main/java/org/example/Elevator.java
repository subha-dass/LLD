package org.example;

import java.util.TreeMap;
import java.util.TreeSet;

public class Elevator{

Direction direction;
private int id;

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ElevetorState getElevetorState() {
        return elevetorState;
    }

    public void setElevetorState(ElevetorState elevetorState) {
        this.elevetorState = elevetorState;
    }

    public TreeSet<Integer> getUp() {
        return up;
    }

    public TreeSet<Integer> getDown() {
        return down;
    }

    public int currentFloor;

    public Elevator( int id) {
        this.direction = Direction.IDEAl;
        this.id = id;
        this.currentFloor = 0;
        this.elevetorState = new IdealState();
    }

    ElevetorState elevetorState;

private final TreeSet<Integer> up=new TreeSet<>();
private final TreeSet<Integer> down =new TreeSet<>();

public void addRequest(Request request){

int pickUpFloor=request.getSourceFloor();
int destFoor=request.getDestFloor();

if(pickUpFloor>currentFloor){
    up.add(pickUpFloor);
}else if(pickUpFloor < currentFloor){
    down.add(pickUpFloor);
}

if(destFoor>pickUpFloor){
    up.add(destFoor);
}else{
    down.add(destFoor);
}

}

    public void processRequest(){
        while(!up.isEmpty() || !down.isEmpty())
        {
            if(direction==Direction.IDEAl){
                if(!up.isEmpty()){
                    direction=Direction.MOVING_UP;
                    elevetorState=new MovingUp();
                }else {
                    direction=Direction.MOVING_DOWN;
                    elevetorState=new MovingDown();
                }
            }else if(direction==Direction.MOVING_UP){
                processUpRequest();
            }else{
                processDownRequest();
            }
        }direction=Direction.IDEAl;
        elevetorState=new IdealState();
    }

    public void processUpRequest(){
        while (!up.isEmpty()){
            int floor=up.pollFirst();
            elevetorState.move(this);
            moveToFloor(floor);
        }if(!down.isEmpty()){
            direction=Direction.MOVING_DOWN;
            elevetorState=new MovingDown();
        }
    }

    public void processDownRequest(){
        while (!down.isEmpty()){
            int floor=down.pollFirst();
            elevetorState.move(this);
            moveToFloor(floor);
        }if(!up.isEmpty()){
            direction=Direction.MOVING_UP;
            elevetorState=new MovingUp();
        }
    }



    public int getCurrentFloor() {
        return currentFloor;
    }

    public void setCurrentFloor(int currentFloor) {
        this.currentFloor = currentFloor;
    }

    public void moveToFloor(int floor){
        System.out.println("moving to floor::"+floor);
        System.out.println("Reached to floor ::"+floor);
    }
}
