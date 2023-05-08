package com.employmentBackend.demo.controller;

import com.employmentBackend.demo.exception.DataNotCompleteException;
import com.employmentBackend.demo.exception.QualificationAlreadyExistException;
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

    private boolean dataComplete(Qualifications qualifications)
    {
        return !(qualifications.getName() == null || qualifications.getDescription() == null );
    }

    private Qualifications qualificationExist(String name) {
        Qualifications exists = qualificationRepository.findQualificationByName(name);
        return exists;
    }

    @PostMapping("/qualification")
    Qualifications newQualification (@RequestBody Qualifications newQualification){
        if(!dataComplete(newQualification))
            throw new DataNotCompleteException();
        if(qualificationExist(newQualification.getName())==null)
            return qualificationRepository.save(newQualification);

        throw new QualificationAlreadyExistException();
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
        if(dataComplete(newQualification)) {
            return qualificationRepository.findById(id)
                    .map(qualification -> {
                        qualification.setName(newQualification.getName());
                        qualification.setDescription(newQualification.getDescription());
                        return qualificationRepository.save(qualification);
                    }).orElseThrow(() -> new QualificationNotFoundException(id));
        }
        else
            throw new DataNotCompleteException();
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
