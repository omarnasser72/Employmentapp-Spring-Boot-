package com.employmentBackend.demo.repository;

import com.employmentBackend.demo.model.Job;
import com.employmentBackend.demo.model.Qualifications;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface JobRepostiory extends JpaRepository <Job, Long>{


}
