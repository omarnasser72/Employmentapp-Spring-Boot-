package com.employmentBackend.demo.exception;

public class AdminNotFoundException extends RuntimeException {
    public AdminNotFoundException(Long id){
       super("Could not found the admin with id " + id);
    }
}
