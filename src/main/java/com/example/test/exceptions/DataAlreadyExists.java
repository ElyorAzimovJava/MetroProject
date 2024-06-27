package com.example.test.exceptions;

import lombok.AllArgsConstructor;

public class DataAlreadyExists extends RuntimeException{
    public DataAlreadyExists(String message){
        super(message);
    }
}
