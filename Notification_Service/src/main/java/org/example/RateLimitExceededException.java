package org.example;

public class RateLimitExceededException extends RuntimeException{
    public RateLimitExceededException(String msg) { super(msg); }
}
