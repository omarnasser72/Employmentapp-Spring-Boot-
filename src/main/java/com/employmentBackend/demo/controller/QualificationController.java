package com.employmentBackend.demo.controller;

import com.employmentBackend.demo.exception.QualificationNotFoundException;
import com.employmentBackend.demo.model.Qualifications;
import com.employmentBackend.demo.repository.QualificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class QualificationController {

    @Autowired
    private QualificationRepository qualificationRepository;

    @PostMapping("/qualification")
    Qualifications newQualification (@RequestBody Qualifications newQualification){
        return qualificationRepository.save(newQualification);
    }

    @GetMapping("/qualifications")
    List<Qualifications> getAllQualifications(){
        return qualificationRepository.findAll();
    }

    @GetMapping("/qualification/{id}")
    Qualifications getQualification(@PathVariable Long id){
        return qualificationRepository.findById(id)
                .orElseThrow(()->new QualificationNotFoundException(id));
    }

    @PutMapping("/qualification/{id}")
    Qualifications updateQualification(@RequestBody Qualifications newQualification, @PathVariable Long id){
        return qualificationRepository.findById(id)
                .map(qualification -> {
                    qualification.setName(newQualification.getName());
                    qualification.setDescription(newQualification.getDescription());
                    return qualificationRepository.save(qualification);
                }).orElseThrow(()->new QualificationNotFoundException(id));
    }

    @DeleteMapping("/qualification/{id}")
    String deleteQualification(@PathVariable Long id){
        if(qualificationRepository.existsById(id))
        {
            qualificationRepository.deleteById(id);
            return "Qualification with id "+ id +" has been deleted successfully.";
        }
        throw new QualificationNotFoundException(id);
    }
}
