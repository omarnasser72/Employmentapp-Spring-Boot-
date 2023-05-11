package com.employmentBackend.demo.controller;

import com.employmentBackend.demo.exception.AdminAuthenticateException;
import com.employmentBackend.demo.model.Admin;
import com.employmentBackend.demo.model.Applicant;
import com.employmentBackend.demo.repository.AdminRepository;
import com.employmentBackend.demo.repository.ApplicantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class ValidateData{

    @Autowired
    ApplicantRepository applicantRepository;
/*
    @PostMapping ("/login")
    Applicant login(@RequestBody Applicant newApplicant){
        if ((applicantRepository.validate(newApplicant.getEmail(), newApplicant.getPassword()))!= null)
            return applicantRepository.validate(newApplicant.getEmail(), newApplicant.getPassword());
        throw new AdminAuthenticateException();
    }*/
}
