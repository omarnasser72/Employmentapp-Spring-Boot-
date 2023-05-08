package com.employmentBackend.demo.exception;

public class QualificationNotFoundException extends RuntimeException{
    public QualificationNotFoundException(Long id){
        super("Could not found the qualification with id " + id);
    }
}
