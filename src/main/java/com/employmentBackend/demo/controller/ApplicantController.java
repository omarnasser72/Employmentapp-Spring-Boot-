package com.employmentBackend.demo.controller;

import com.employmentBackend.demo.exception.ApplicantNotFoundException;
import com.employmentBackend.demo.model.Applicant;
import com.employmentBackend.demo.repository.ApplicantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ApplicantController {

    @Autowired
    private ApplicantRepository applicantRepository;

    @PostMapping("/applicant")
    Applicant newApplicant (@RequestBody Applicant newApplicant){
        return applicantRepository.save(newApplicant);
    }

    @GetMapping("/applicants")
    List<Applicant> getAllApplicants(){
        return applicantRepository.findAll();
    }

    @GetMapping("/applicant/{id}")
    Applicant getApplicant(@PathVariable Long id){
        return applicantRepository.findById(id)
                .orElseThrow(()->new ApplicantNotFoundException(id));
    }

    @PutMapping("/applicant/{id}")
    Applicant updateApplicant(@RequestBody Applicant newApplicant, @PathVariable Long id){
        return applicantRepository.findById(id)
                .map(applicant -> {
                        applicant.setName(newApplicant.getName());
                        applicant.setEmail(newApplicant.getEmail());
                        applicant.setPassword(newApplicant.getPassword());
                        applicant.setPhone(newApplicant.getPhone());
                        applicant.setJobSearches(newApplicant.getJobSearches());
                        applicant.setStatus(newApplicant.getStatus());
                        return applicantRepository.save(applicant);
                }).orElseThrow(()->new ApplicantNotFoundException(id));
    }

    @DeleteMapping("/applicant/{id}")
    String deleteApplicant(@PathVariable Long id){
        if(applicantRepository.existsById(id))
        {
            applicantRepository.deleteById(id);
            return "Applicant with id "+ id +" has been deleted successfully.";
        }
        throw new ApplicantNotFoundException(id);
    }
}
