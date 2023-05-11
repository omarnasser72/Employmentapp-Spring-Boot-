package com.employmentBackend.demo.exception;

public class AdminAlreadyExistException extends RuntimeException{
    public AdminAlreadyExistException() {
        super("Could not add the admin because email already exist");
    }
}
