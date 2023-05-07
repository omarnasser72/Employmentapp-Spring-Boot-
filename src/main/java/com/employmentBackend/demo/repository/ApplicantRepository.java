package com.employmentBackend.demo.repository;

import com.employmentBackend.demo.model.Applicant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApplicantRepository extends JpaRepository<Applicant, Long> {

}
