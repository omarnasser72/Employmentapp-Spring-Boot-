package com.employmentBackend.demo.repository;

import com.employmentBackend.demo.model.Applicant;
import com.employmentBackend.demo.model.Qualifications;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface QualificationRepository extends JpaRepository <Qualifications, Long>{
    @Query("SELECT a FROM Qualifications a WHERE a.name = :name")
    Qualifications findQualificationByName(@Param("name") String name);

    @Query("SELECT a FROM Qualifications a WHERE a.id = :id")
    Qualifications findQualificationById(@Param("id") Long id);
}
