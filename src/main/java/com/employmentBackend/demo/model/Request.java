package com.employmentBackend.demo.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class Request {
    @Id
    @GeneratedValue
    private Long id;
    private Long job_id;
    private Long applicant_id;
    private int status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getJob_id() {
        return job_id;
    }

    public void setJob_id(Long job_id) {
        this.job_id = job_id;
    }

    public Long getApplicant_id() {
        return applicant_id;
    }

    public void setApplicant_id(Long applicant_id) {
        this.applicant_id = applicant_id;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
