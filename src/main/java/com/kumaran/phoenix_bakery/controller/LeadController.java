package com.kumaran.phoenix_bakery.controller;

import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kumaran.phoenix_bakery.dto.LeadRequest;
import com.kumaran.phoenix_bakery.dto.LeadResponse;
import com.kumaran.phoenix_bakery.model.Lead;
import com.kumaran.phoenix_bakery.service.LeadService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/leads")
@CrossOrigin(origins = "*")
public class LeadController {

    private final LeadService leadService;

    public LeadController(LeadService leadService) {
        this.leadService = leadService;
    }

    @PostMapping
    public Lead saveLead(@Valid @RequestBody LeadRequest request) {
        return leadService.saveLead(request);
    }

    @GetMapping
    public List<Lead> getAllLeads() {
        return leadService.getAllLeads();
    }

    @GetMapping("/page")
    public List<LeadResponse> getFilteredLeads(@RequestParam(required = false) String company,
                                              @RequestParam(required = false) String leadType,
                                              @RequestParam(required = false) String worker,
                                              @RequestParam(required = false) String date,
                                              @RequestParam(required = false) String search) {
        return leadService.getFilteredLeads(company, leadType, worker, date, search);
    }

    @GetMapping("/dashboard")
    public Map<String, Object> getDashboardSummary() {
        return leadService.getReportSummary();
    }

    @GetMapping("/recent")
    public List<LeadResponse> getRecentLeads() {
        return leadService.getFilteredLeads(null, null, null, null, null).stream().limit(6).toList();
    }

    @GetMapping("/{id}")
    public Lead getLead(@PathVariable Long id) {
        return leadService.getLead(id);
    }

    @PutMapping("/{id}")
    public Lead updateLead(@PathVariable Long id, @Valid @RequestBody LeadRequest request) {
        return leadService.updateLead(id, request);
    }

    @DeleteMapping("/{id}")
    public void deleteLead(@PathVariable Long id) {
        leadService.deleteLead(id);
    }
}