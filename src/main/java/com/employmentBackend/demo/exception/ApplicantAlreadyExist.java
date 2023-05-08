package com.employmentBackend.demo.exception;

public class ApplicantAlreadyExist extends RuntimeException{
    public ApplicantAlreadyExist() {
        super("Could not add the applicant because email already exist");
    }
}
