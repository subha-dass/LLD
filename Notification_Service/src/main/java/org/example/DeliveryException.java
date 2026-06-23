package org.example;

public class DeliveryException extends Exception{
    public DeliveryException(String msg) {
        super(msg);
    }
    public DeliveryException(String msg,Throwable c){
        super(msg,c);
    }
}
