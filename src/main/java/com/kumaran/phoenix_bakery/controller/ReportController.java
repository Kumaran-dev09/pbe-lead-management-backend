package com.kumaran.phoenix_bakery.controller;

import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kumaran.phoenix_bakery.dto.LeadResponse;
import com.kumaran.phoenix_bakery.service.LeadService;

@RestController
@RequestMapping("/api/reports")
@CrossOrigin(origins = "*")
public class ReportController {

    private final LeadService leadService;

    public ReportController(LeadService leadService) {
        this.leadService = leadService;
    }

    @GetMapping
    public List<LeadResponse> getReports(@RequestParam(required = false) String company,
                                         @RequestParam(required = false) String leadType,
                                         @RequestParam(required = false) String worker,
                                         @RequestParam(required = false) String date,
                                         @RequestParam(required = false) String search) {
        return leadService.getFilteredLeads(company, leadType, worker, date, search);
    }

    @GetMapping("/summary")
    public Map<String, Object> getSummary() {
        return leadService.getReportSummary();
    }
}
