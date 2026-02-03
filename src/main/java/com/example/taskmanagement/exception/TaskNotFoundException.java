package com.example.taskmanagement.exception;

public class TaskNotFoundException extends RuntimeException {
    public TaskNotFoundException(String message) {
        super(message);
        System.out.println("Exception being handeled inside  TaskNotFoundException: " + message);
    }
    
}
