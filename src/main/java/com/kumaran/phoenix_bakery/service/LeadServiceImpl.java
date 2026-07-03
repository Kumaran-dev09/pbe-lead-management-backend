package com.kumaran.phoenix_bakery.service;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

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
        String normalizedSearch = search == null || search.isBlank() ? null : search.trim().toLowerCase();
        String companyFilter = company == null || company.isBlank() ? null : company;
        String leadTypeFilter = leadType == null || leadType.isBlank() ? null : leadType;
        String workerFilter = worker == null || worker.isBlank() ? null : worker;

        Pageable pageable = PageRequest.of(0, 200, Sort.by(Sort.Direction.DESC, "createdAt"));

        List<Lead> leads = repository.findLeads(companyFilter, leadTypeFilter, workerFilter, parsedDate, parsedDate, normalizedSearch, pageable);

        return leads.stream()
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

        long todayLeads = repository.countByCreatedAtAfter(today.atStartOfDay());
        long weeklyLeads = repository.countByCreatedAtAfter(weekStart.atStartOfDay());
        long monthlyLeads = repository.countByCreatedAtAfter(monthStart.atStartOfDay());
        long totalLeads = repository.count();

        Map<String, Long> companyStats = repository.countLeadsByCompany().stream()
                .collect(Collectors.toMap(e -> (String) e.get("name"), e -> (Long) e.get("count"), (v1, v2) -> v1, LinkedHashMap::new));

        Map<String, Long> leadTypeStats = repository.countLeadsByLeadType().stream()
                .collect(Collectors.toMap(e -> (String) e.get("name"), e -> (Long) e.get("count"), (v1, v2) -> v1, LinkedHashMap::new));

        Map<String, Long> workerStats = repository.countLeadsByWorker().stream()
                .collect(Collectors.toMap(e -> (String) e.get("name"), e -> (Long) e.get("count"), (v1, v2) -> v1, LinkedHashMap::new));


        Map<String, Object> summary = new LinkedHashMap<>();
        summary.put("todayLeads", todayLeads);
        summary.put("weeklyLeads", weeklyLeads);
        summary.put("monthlyLeads", monthlyLeads);
        summary.put("totalLeads", totalLeads);
        summary.put("companyStats", companyStats);
        summary.put("leadTypeStats", leadTypeStats);
        summary.put("workerStats", workerStats);
        summary.put("recentLeads", repository.findTop6ByOrderByCreatedAtDesc().stream().map(mapper::toResponse).collect(Collectors.toList()));
        return summary;
    }
}