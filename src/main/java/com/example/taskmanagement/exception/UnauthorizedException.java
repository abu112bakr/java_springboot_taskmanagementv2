package com.example.taskmanagement.exception;

public class UnauthorizedException extends RuntimeException{
    public UnauthorizedException(String message) {
        super(message);
        System.out.println("Exception being handeled inside  UnauthorizedException: " + message);
    }
     
}
