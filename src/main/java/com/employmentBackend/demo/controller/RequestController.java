package com.employmentBackend.demo.controller;

import com.employmentBackend.demo.exception.*;
import com.employmentBackend.demo.interfaces.isRequestDataComplete;
import com.employmentBackend.demo.model.Request;
import com.employmentBackend.demo.repository.ApplicantRepository;
import com.employmentBackend.demo.repository.JobRepostiory;
import com.employmentBackend.demo.repository.RequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class RequestController implements isRequestDataComplete {

    @Autowired
    private RequestRepository requestRepository;
    @Autowired
    private ApplicantRepository applicantRepository;
    @Autowired
    private JobRepostiory jobRepostiory;

    @Override
    public boolean dataComplete(Request request)
    {
        return !(request.getApplicant_id() == null || request.getJob_id() == null);
    }

    private boolean jobNotExist(Request request)
    {
        return !jobRepostiory.existsById(request.getJob_id());
    }

    private boolean applicantNotExist(Request request)
    {
        return !applicantRepository.existsById(request.getApplicant_id());
    }

    @PostMapping("/request")
    Request newRequest (@RequestBody Request newRequest){
        if(!dataComplete(newRequest))
            throw new DataNotCompleteException();
        if(jobNotExist(newRequest))
            throw new JobNotFoundException(newRequest.getJob_id());
        if(applicantNotExist(newRequest))
            throw new ApplicantNotFoundException(newRequest.getApplicant_id());
        if(requestRepository.findAllByJobIdAndApplicantId(newRequest.getJob_id(), newRequest.getApplicant_id()).size()>0)
            throw new RequestAlreadyExistException();
        newRequest.setStatus(0);
        return requestRepository.save(newRequest);
    }

    @GetMapping("/requests")
    List<Request> getAllRequests(){
        return requestRepository.findAll();
    }

    @GetMapping("/request/{id}")
    Request getRequests(@PathVariable Long id){
        return requestRepository.findById(id)
                .orElseThrow(()->new RequestNotFoundException(id));
    }

    @PutMapping("/request/{id}")
    Request updateRequest(@RequestBody Request newRequest, @PathVariable Long id){
        if(dataComplete(newRequest)) {
            if(jobNotExist(newRequest))
                throw new JobNotFoundException(newRequest.getJob_id());
            if(applicantNotExist(newRequest))
                throw new ApplicantNotFoundException(newRequest.getApplicant_id());
            else {
                return requestRepository.findById(id)
                        .map(request -> {
                            request.setJob_id(newRequest.getJob_id());
                            request.setApplicant_id(newRequest.getApplicant_id());
                            request.setStatus(newRequest.getStatus());
                            return requestRepository.save(request);
                        }).orElseThrow(() -> new RequestNotFoundException(id));
            }
        }
        else
            throw new DataNotCompleteException();
    }

    @DeleteMapping("/request/{id}")
    String deleteRequest(@PathVariable Long id){
        if(requestRepository.existsById(id))
        {
            requestRepository.deleteById(id);
            return "Request with id "+ id +" has been deleted successfully.";
        }
        throw new RequestNotFoundException(id);
    }

}
