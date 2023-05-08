package com.employmentBackend.demo.exception;

public class ApplicantAlreadyExistException extends RuntimeException{
    public ApplicantAlreadyExistException() {
        super("Could not add the applicant because email already exist");
    }
}
