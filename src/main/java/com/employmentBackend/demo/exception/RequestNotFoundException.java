package com.employmentBackend.demo.exception;

public class RequestNotFoundException extends RuntimeException{
    public RequestNotFoundException(Long id){
        super("Could not found the Request with id " + id);
    }

}
