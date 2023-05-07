package com.employmentBackend.demo.exception;

public class ApplicantNotFoundException extends RuntimeException{
    public ApplicantNotFoundException(Long id){
        super("Could not found the applicant with id " + id);
    }
}
