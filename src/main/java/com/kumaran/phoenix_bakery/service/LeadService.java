package com.kumaran.phoenix_bakery.service;

import java.util.List;
import java.util.Map;

import com.kumaran.phoenix_bakery.dto.LeadRequest;
import com.kumaran.phoenix_bakery.dto.LeadResponse;
import com.kumaran.phoenix_bakery.model.Lead;

public interface LeadService {

    Lead saveLead(LeadRequest request);

    List<Lead> getAllLeads();

    Lead getLead(Long id);

    Lead updateLead(Long id, LeadRequest request);

    void deleteLead(Long id);

    List<LeadResponse> getFilteredLeads(String company, String leadType, String worker, String date, String search);

    Map<String, Object> getReportSummary();
}