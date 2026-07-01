package com.kumaran.phoenix_bakery.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kumaran.phoenix_bakery.model.Admin;
import com.kumaran.phoenix_bakery.service.AdminService;

@RestController
@RequestMapping("/api/admin")
@CrossOrigin(origins = "*")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @PostMapping("/login")
    public Admin login(@RequestBody Admin request) {

        return adminService.login(
                request.getUsername(),
                request.getPassword()
        );

    }

    @PostMapping
    public Admin createAdmin(@RequestBody Admin admin) {

        return adminService.save(admin);

    }

}