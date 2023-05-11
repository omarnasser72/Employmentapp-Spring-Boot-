package com.employmentBackend.demo.repository;

import com.employmentBackend.demo.model.Admin;
import com.employmentBackend.demo.model.Applicant;
import com.employmentBackend.demo.model.Request;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AdminRepository extends JpaRepository<Admin, Long > {
    @Query("SELECT a FROM Admin a WHERE a.email = :email")
    Admin findAdminByEmail(@Param("email") String email);

    @Query("SELECT a FROM Admin a WHERE a.token = :token")
    Admin findAdminByToken(@Param("token") String token);



}
