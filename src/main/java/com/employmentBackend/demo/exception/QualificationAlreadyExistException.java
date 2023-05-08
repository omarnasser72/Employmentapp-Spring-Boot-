package com.employmentBackend.demo.exception;

public class QualificationAlreadyExistException extends RuntimeException{

    public QualificationAlreadyExistException() {
        super("Could not add the qualification because name already exist");
    }
}
