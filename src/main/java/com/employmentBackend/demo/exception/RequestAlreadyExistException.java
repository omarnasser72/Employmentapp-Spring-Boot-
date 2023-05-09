package com.employmentBackend.demo.exception;

public class RequestAlreadyExistException extends RuntimeException{

    public RequestAlreadyExistException() {
        super("Could not add the Request because this applicant already requested this job");
    }
}
