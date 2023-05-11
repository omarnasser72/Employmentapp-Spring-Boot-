package com.employmentBackend.demo.controller;

import com.employmentBackend.demo.exception.ApplicantAlreadyExistException;
import com.employmentBackend.demo.exception.ApplicantNotFoundException;
import com.employmentBackend.demo.exception.DataNotCompleteException;
import com.employmentBackend.demo.interfaces.isAdminDataComplete;
import com.employmentBackend.demo.model.Admin;
import com.employmentBackend.demo.model.Applicant;
import com.employmentBackend.demo.repository.AdminRepository;
import com.employmentBackend.demo.repository.ApplicantRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class AdminController implements isAdminDataComplete {
    @Autowired
    private AdminRepository adminRepository;

    private Admin adminExist(String email) {
        Admin exists = adminRepository.findAdminByEmail(email);
        return exists;
    }

    @Override
    public boolean dataComplete(Admin admin)
    {
        return !(admin.getEmail() == null || admin.getName() == null || admin.getPassword() == null || admin.getPhone() == null);
    }

    @PostMapping("/admin")
    Admin newAdmin (@RequestBody Admin newAdmin){
        if(dataComplete(newAdmin)) {
            newAdmin.setStatus(0);
        }
        else
            throw new DataNotCompleteException();
        if (adminExist(newAdmin.getEmail()) == null)
            return adminRepository.save(newAdmin);

        throw new ApplicantAlreadyExistException();
    }

    @GetMapping("/admins")
    List<Admin> getAllAdmins(){
        return adminRepository.findAll();
    }

    @GetMapping("/admin/{id}")
    Admin getAdmin(@PathVariable Long id){
        return adminRepository.findById(id)
                .orElseThrow(()->new ApplicantNotFoundException(id));
    }

    @PutMapping("/admin/{id}")
    Admin updateAdmin(@RequestBody Admin newAdmin, @PathVariable Long id){
        if(dataComplete(newAdmin)) {
            return adminRepository.findById(id)
                    .map(admin -> {
                        admin.setName(newAdmin.getName());
                        admin.setEmail(newAdmin.getEmail());
                        admin.setPassword(newAdmin.getPassword());
                        admin.setPhone(newAdmin.getPhone());
                        admin.setStatus(newAdmin.getStatus());
                        return adminRepository.save(admin);
                    }).orElseThrow(() -> new ApplicantNotFoundException(id));
        }
        else
            throw new DataNotCompleteException();
    }

    @DeleteMapping("/admin/{id}")
    String deleteAdmin(@PathVariable Long id){
        if(adminRepository.existsById(id))
        {
            adminRepository.deleteById(id);
            return "Admin with id "+ id +" has been deleted successfully.";
        }
        throw new ApplicantNotFoundException(id);
    }

}
