package com.employmentBackend.demo.middleware;

import com.employmentBackend.demo.repository.AdminRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class Authentication {

    @Autowired
    private AdminRepository adminRepository;

    public boolean isAdmin(HttpServletRequest request) {
        String authToken = request.getHeader("token");
        if (authToken != null)
            return (adminRepository.findAdminByToken(authToken) != null);
        return false;
    }

    public boolean validateData(String email, String password)
    {
        return (email==password);
    }

}
