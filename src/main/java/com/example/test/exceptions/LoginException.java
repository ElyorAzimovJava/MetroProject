package com.example.test.exceptions;

public class LoginException extends RuntimeException{
    public LoginException(String message){
        super(message);
    }
}
