package com.employmentBackend.demo.exception;

public class JobNotFoundException extends RuntimeException{

    public JobNotFoundException(Long id){
        super("Could not found the job with id " + id);
    }
}
