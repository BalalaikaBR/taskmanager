package com.taskmanager.exceptions;

public class GenericException {
    public <T> T handleException(Exception error){
            throw new RuntimeException(error.getMessage());
        }
        
}
