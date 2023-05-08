package com.employmentBackend.demo.repository;

import com.employmentBackend.demo.model.Qualifications;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QualificationRepository extends JpaRepository <Qualifications, Long>{
}
