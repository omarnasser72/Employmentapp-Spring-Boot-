package com.employmentBackend.demo.exception;

public class AuthenticationException extends RuntimeException{
    public AuthenticationException(){
        super("You don't have the permission to send this request");
    }
}
