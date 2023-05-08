package com.employmentBackend.demo.repository;

import com.employmentBackend.demo.model.Applicant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ApplicantRepository extends JpaRepository<Applicant, Long> {

    @Query("SELECT a FROM Applicant a WHERE a.email = :email")
    Applicant findApplicantByEmail(@Param("email") String email);
}
