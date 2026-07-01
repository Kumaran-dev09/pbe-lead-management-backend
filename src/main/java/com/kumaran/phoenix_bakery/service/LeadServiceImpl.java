package com.kumaran.phoenix_bakery.service;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.util.StringUtils;

import org.springframework.stereotype.Service;

import com.kumaran.phoenix_bakery.dto.LeadRequest;
import com.kumaran.phoenix_bakery.dto.LeadResponse;
import com.kumaran.phoenix_bakery.mapper.LeadMapper;
import com.kumaran.phoenix_bakery.model.Lead;
import com.kumaran.phoenix_bakery.repository.LeadRepository;

@Service
public class LeadServiceImpl implements LeadService {

    private final LeadRepository repository;
    private final LeadMapper mapper;

    public LeadServiceImpl(LeadRepository repository, LeadMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public Lead saveLead(LeadRequest request) {
        validateLeadRequest(request);
        Lead lead = mapper.toEntity(request);
        return repository.save(lead);
    }

    @Override
    public List<Lead> getAllLeads() {
        return repository.findAll();
    }

    @Override
    public Lead getLead(Long id) {
        return repository.findById(id).orElseThrow(() -> new IllegalArgumentException("Lead not found: " + id));
    }

    @Override
    public Lead updateLead(Long id, LeadRequest request) {
        validateLeadRequest(request);
        Lead existing = getLead(id);
        mapper.apply(existing, request);
        return repository.save(existing);
    }

    @Override
    public void deleteLead(Long id) {
        repository.deleteById(id);
    }

    @Override
    public List<LeadResponse> getFilteredLeads(String company, String leadType, String worker, String date, String search) {
        LocalDate parsedDate = date == null || date.isBlank() ? null : LocalDate.parse(date);
        String normalizedSearch = search == null ? "" : search.trim().toLowerCase();

        return repository.findAll().stream()
                .filter(lead -> matchesSearch(lead, normalizedSearch))
                .filter(lead -> matchesExactFilter(lead.getCompany(), company))
                .filter(lead -> matchesExactFilter(lead.getLeadType(), leadType))
                .filter(lead -> matchesWorkerFilter(lead, worker))
                .filter(lead -> parsedDate == null || (lead.getCreatedAt() != null && lead.getCreatedAt().toLocalDate().equals(parsedDate)))
                .sorted(Comparator.comparing(Lead::getCreatedAt, Comparator.nullsLast(Comparator.reverseOrder())))
                .limit(200)
                .map(mapper::toResponse)
                .collect(Collectors.toList());
    }

    private boolean matchesSearch(Lead lead, String search) {
        if (search == null || search.isBlank()) {
            return true;
        }

        return containsIgnoreCase(lead.getCustomerName(), search)
                || containsIgnoreCase(lead.getMobile(), search)
                || containsIgnoreCase(lead.getLeadNo(), search);
    }

    private boolean matchesExactFilter(String fieldValue, String filterValue) {
        if (filterValue == null || filterValue.isBlank()) {
            return true;
        }
        return fieldValue != null && fieldValue.equalsIgnoreCase(filterValue);
    }

    private boolean matchesWorkerFilter(Lead lead, String worker) {
        if (worker == null || worker.isBlank()) {
            return true;
        }

        return containsIgnoreCase(lead.getWorkerId(), worker)
                || containsIgnoreCase(lead.getWorkerName(), worker);
    }

    private boolean containsIgnoreCase(String value, String search) {
        return value != null && value.toLowerCase().contains(search);
    }

    private void validateLeadRequest(LeadRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("Lead request is required");
        }
        if (!StringUtils.hasText(request.getCustomerName())) {
            throw new IllegalArgumentException("Customer Name is required");
        }
        if (!StringUtils.hasText(request.getMobile())) {
            throw new IllegalArgumentException("Mobile Number is required");
        }
        if (!StringUtils.hasText(request.getWhatsapp())) {
            throw new IllegalArgumentException("WhatsApp Number is required");
        }
        if (!StringUtils.hasText(request.getLeadType())) {
            throw new IllegalArgumentException("Lead Type is required");
        }
        String leadType = request.getLeadType().trim().toUpperCase();
        if ("EXISTING_BAKERY".equals(leadType) && !StringUtils.hasText(request.getBakeryName())) {
            throw new IllegalArgumentException("Bakery Name is required");
        }
        if ("REFERENCE_CUSTOMER".equals(leadType) && !StringUtils.hasText(request.getReferenceCustomerName())) {
            throw new IllegalArgumentException("Reference Customer Name is required");
        }
        if ("REFERENCE_CUSTOMER".equals(leadType) && !StringUtils.hasText(request.getReferenceCustomerMobile())) {
            throw new IllegalArgumentException("Reference Customer Mobile is required");
        }
    }

    @Override
    public Map<String, Object> getReportSummary() {
        LocalDate today = LocalDate.now();
        LocalDate weekStart = today.minusDays(6);
        LocalDate monthStart = today.withDayOfMonth(1);

        List<Lead> allLeads = repository.findAll();
        long todayLeads = allLeads.stream().filter(lead -> lead.getCreatedAt() != null && !lead.getCreatedAt().toLocalDate().isBefore(today)).count();
        long weeklyLeads = allLeads.stream().filter(lead -> lead.getCreatedAt() != null && !lead.getCreatedAt().toLocalDate().isBefore(weekStart)).count();
        long monthlyLeads = allLeads.stream().filter(lead -> lead.getCreatedAt() != null && !lead.getCreatedAt().toLocalDate().isBefore(monthStart)).count();

        Map<String, Long> companyStats = new LinkedHashMap<>();
        Map<String, Long> leadTypeStats = new LinkedHashMap<>();
        Map<String, Long> workerStats = new LinkedHashMap<>();

        allLeads.forEach(lead -> {
            companyStats.merge(lead.getCompany() == null || lead.getCompany().isBlank() ? "Unknown" : lead.getCompany(), 1L, Long::sum);
            leadTypeStats.merge(lead.getLeadType() == null || lead.getLeadType().isBlank() ? "Unknown" : lead.getLeadType(), 1L, Long::sum);
            workerStats.merge(lead.getWorkerName() == null || lead.getWorkerName().isBlank() ? (lead.getWorkerId() == null ? "Unknown" : lead.getWorkerId()) : lead.getWorkerName(), 1L, Long::sum);
        });

        Map<String, Object> summary = new LinkedHashMap<>();
        summary.put("todayLeads", todayLeads);
        summary.put("weeklyLeads", weeklyLeads);
        summary.put("monthlyLeads", monthlyLeads);
        summary.put("totalLeads", allLeads.size());
        summary.put("companyStats", companyStats);
        summary.put("leadTypeStats", leadTypeStats);
        summary.put("workerStats", workerStats);
        summary.put("recentLeads", repository.findTop6ByOrderByCreatedAtDesc().stream().map(mapper::toResponse).collect(Collectors.toList()));
        return summary;
    }
}