package com.kumaran.phoenix_bakery.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kumaran.phoenix_bakery.model.Admin;
import com.kumaran.phoenix_bakery.repository.AdminRepository;

@Service
public class AdminService {

    @Autowired
    private AdminRepository adminRepository;

    public Admin login(String username, String password) {

        Admin admin = adminRepository.findByUsername(username);

        if (admin == null) {
            return null;
        }

        // Check Admin Status
        if (!"Active".equalsIgnoreCase(admin.getStatus())) {
            return null;
        }

        // Check Password
        if (!admin.getPassword().equals(password)) {
            return null;
        }

        return admin;
    }

    public Admin save(Admin admin) {
        return adminRepository.save(admin);
    }

    public boolean exists(String username) {
        return adminRepository.findByUsername(username) != null;
    }

}