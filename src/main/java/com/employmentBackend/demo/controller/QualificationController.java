package com.employmentBackend.demo.controller;

import com.employmentBackend.demo.exception.AuthenticationException;
import com.employmentBackend.demo.exception.DataNotCompleteException;
import com.employmentBackend.demo.exception.QualificationAlreadyExistException;
import com.employmentBackend.demo.exception.QualificationNotFoundException;
import com.employmentBackend.demo.interfaces.isQualificationDataComplete;
import com.employmentBackend.demo.middleware.Authentication;
import com.employmentBackend.demo.model.Qualifications;
import com.employmentBackend.demo.repository.QualificationRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class QualificationController implements isQualificationDataComplete {

    @Autowired
    private QualificationRepository qualificationRepository;
    @Autowired
    private Authentication authentication;

    @Override
    public boolean dataComplete(Qualifications qualifications)
    {
        return !(qualifications.getName() == null || qualifications.getDescription() == null );
    }

    private Qualifications qualificationExist(String name) {
        Qualifications exists = qualificationRepository.findQualificationByName(name);
        return exists;
    }

    @PostMapping("/qualification")
    Qualifications newQualification (@RequestBody Qualifications newQualification, HttpServletRequest req){
        if(authentication.isAdmin(req)) {
            if (!dataComplete(newQualification))
                throw new DataNotCompleteException();
            if (qualificationExist(newQualification.getName()) == null)
                return qualificationRepository.save(newQualification);

            throw new QualificationAlreadyExistException();
        }
        throw new AuthenticationException();
    }

    @GetMapping("/qualifications")
    List<Qualifications> getAllQualifications(HttpServletRequest req){
        if(authentication.isAdmin(req)) {
            return qualificationRepository.findAll();
        }
        throw new AuthenticationException();
    }

    @GetMapping("/qualification/{id}")
    Qualifications getQualification(@PathVariable Long id, HttpServletRequest req){
        if(authentication.isAdmin(req)) {
            return qualificationRepository.findById(id)
                    .orElseThrow(() -> new QualificationNotFoundException(id));
        }
        throw new AuthenticationException();
    }

    @PutMapping("/qualification/{id}")
    Qualifications updateQualification(@RequestBody Qualifications newQualification, @PathVariable Long id, HttpServletRequest req){
        if(authentication.isAdmin(req)) {
            if (dataComplete(newQualification)) {
                return qualificationRepository.findById(id)
                        .map(qualification -> {
                            qualification.setName(newQualification.getName());
                            qualification.setDescription(newQualification.getDescription());
                            return qualificationRepository.save(qualification);
                        }).orElseThrow(() -> new QualificationNotFoundException(id));
            } else
                throw new DataNotCompleteException();
        }
        throw new AuthenticationException();
    }

    @DeleteMapping("/qualification/{id}")
    String deleteQualification(@PathVariable Long id, HttpServletRequest req){
        if(authentication.isAdmin(req)) {
            if (qualificationRepository.existsById(id)) {
                qualificationRepository.deleteById(id);
                return "Qualification with id " + id + " has been deleted successfully.";
            }
            throw new QualificationNotFoundException(id);
        }
        throw new AuthenticationException();
    }
}
