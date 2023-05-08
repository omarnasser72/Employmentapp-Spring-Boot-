package com.employmentBackend.demo.controller;

import com.employmentBackend.demo.exception.DataNotCompleteException;
import com.employmentBackend.demo.exception.JobNotFoundException;
import com.employmentBackend.demo.exception.QualificationNotFoundException;
import com.employmentBackend.demo.model.Job;
import com.employmentBackend.demo.model.Qualifications;
import com.employmentBackend.demo.repository.JobRepostiory;
import com.employmentBackend.demo.repository.QualificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class JobController {

    @Autowired
    private JobRepostiory jobRepostiory;
    @Autowired
    private QualificationRepository qualificationRepository;
    private boolean dataComplete(Job job)
    {
        return !(job.getPosition() == null || job.getDescription() == null || job.getOffer() == -1 || job.getQualifications() == -1L);
    }

    private boolean qualificationNotExist(Job job)
    {
        Qualifications exist =  qualificationRepository.findQualificationById(job.getQualifications());
        return (exist == null);
    }

    @PostMapping("/job")
    Job newJob (@RequestBody Job newJob){
        if(!dataComplete(newJob))
            throw new DataNotCompleteException();
        if(qualificationNotExist(newJob))
            throw new QualificationNotFoundException(newJob.getQualifications());
        newJob.setQualifications(newJob.getQualifications());
        return jobRepostiory.save(newJob);
    }

    @GetMapping("/jobs")
    List<Job> getAllJobs(){
        return jobRepostiory.findAll();
    }

    @GetMapping("/job/{id}")
    Job getJob(@PathVariable Long id){
        return jobRepostiory.findById(id)
                .orElseThrow(()->new JobNotFoundException(id));
    }

    @PutMapping("/job/{id}")
    Job updateJob(@RequestBody Job newJob, @PathVariable Long id){
        if(dataComplete(newJob)) {
            if(qualificationNotExist(newJob))
                throw new QualificationNotFoundException(newJob.getQualifications());
            else {
                return jobRepostiory.findById(id)
                        .map(job -> {
                            job.setPosition(newJob.getPosition());
                            job.setDescription(newJob.getDescription());
                            job.setOffer(newJob.getOffer());
                            job.setQualifications(newJob.getQualifications());
                            return jobRepostiory.save(job);
                        }).orElseThrow(() -> new JobNotFoundException(id));
            }
        }
        else
            throw new DataNotCompleteException();
    }

    @DeleteMapping("/job/{id}")
    String deleteJob(@PathVariable Long id){
        if(jobRepostiory.existsById(id))
        {
            jobRepostiory.deleteById(id);
            return "Job with id "+ id +" has been deleted successfully.";
        }
        throw new JobNotFoundException(id);
    }
}
