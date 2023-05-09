package com.employmentBackend.demo.repository;

import com.employmentBackend.demo.model.Qualifications;
import com.employmentBackend.demo.model.Request;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RequestRepository extends JpaRepository<Request, Long> {

    @Query("SELECT r FROM Request r WHERE r.job_id = :jobId AND r.applicant_id = :applicantId")
    List<Request> findAllByJobIdAndApplicantId(@Param("jobId") Long jobId, @Param("applicantId") Long applicantId);
}
