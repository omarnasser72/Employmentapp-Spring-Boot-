package com.employmentBackend.demo.controller;

import com.employmentBackend.demo.exception.ApplicantAlreadyExistException;
import com.employmentBackend.demo.exception.AuthenticationException;
import com.employmentBackend.demo.exception.DataNotCompleteException;
import com.employmentBackend.demo.exception.ApplicantNotFoundException;
import com.employmentBackend.demo.interfaces.isApplicantDataComplete;
import com.employmentBackend.demo.model.Applicant;
import com.employmentBackend.demo.middleware.Authentication;
import com.employmentBackend.demo.repository.AdminRepository;
import com.employmentBackend.demo.repository.ApplicantRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ApplicantController implements isApplicantDataComplete {

    @Autowired
    private ApplicantRepository applicantRepository;
    @Autowired
    private AdminRepository adminRepository;
    @Autowired
    private Authentication authentication;

    private Applicant applicantExist(String email) {
        Applicant exists = applicantRepository.findApplicantByEmail(email);
        return exists;
    }

    @Override
    public boolean dataComplete(Applicant applicant)
    {
        return !(applicant.getEmail() == null || applicant.getName() == null || applicant.getPassword() == null || applicant.getPhone() == null);
    }

    @PostMapping("/applicant")
    Applicant newApplicant (@RequestBody Applicant newApplicant, HttpServletRequest req){
        if (dataComplete(newApplicant)) {
            newApplicant.setStatus(0);
            newApplicant.setJobSearches("");
        }
        else
            throw new DataNotCompleteException();
        if (applicantExist(newApplicant.getEmail()) == null)
            return applicantRepository.save(newApplicant);
        throw new ApplicantAlreadyExistException();
    }

    @GetMapping("/applicants")
    List<Applicant> getAllApplicants(HttpServletRequest req){
        if(authentication.isAdmin(req)) {
            return applicantRepository.findAll();
        }
        throw new AuthenticationException();
    }

    @GetMapping("/applicant/{id}")
    Applicant getApplicant(@PathVariable Long id,HttpServletRequest req){
        if(authentication.isAdmin(req)) {
        return applicantRepository.findById(id)
                .orElseThrow(()->new ApplicantNotFoundException(id));}
        throw new AuthenticationException();
    }

    @PutMapping("/applicant/{id}")
    Applicant updateApplicant(@RequestBody Applicant newApplicant, @PathVariable Long id,HttpServletRequest req){
        if(authentication.isAdmin(req)) {
        if(dataComplete(newApplicant)) {
            return applicantRepository.findById(id)
                    .map(applicant -> {
                        applicant.setName(newApplicant.getName());
                        applicant.setEmail(newApplicant.getEmail());
                        applicant.setPassword(newApplicant.getPassword());
                        applicant.setPhone(newApplicant.getPhone());
                        applicant.setJobSearches(newApplicant.getJobSearches());
                        applicant.setStatus(newApplicant.getStatus());
                        return applicantRepository.save(applicant);
                    }).orElseThrow(() -> new ApplicantNotFoundException(id));
        }
        else
            throw new DataNotCompleteException();
        }throw new AuthenticationException();

    }

    @DeleteMapping("/applicant/{id}")
    String deleteApplicant(@PathVariable Long id, HttpServletRequest req){
        if(authentication.isAdmin(req)) {
            if(applicantRepository.existsById(id))
            {
                applicantRepository.deleteById(id);
                return "Applicant with id "+ id +" has been deleted successfully.";
            }
            throw new ApplicantNotFoundException(id);
            }
        throw new AuthenticationException();

    }


}
