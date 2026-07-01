package com.kumaran.phoenix_bakery.model;

import java.time.LocalDateTime;

import jakarta.persistence.*;

@Entity
@Table(name = "leads")
public class Lead {

    /* ==========================================
       PRIMARY KEY
    ========================================== */

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /* ==========================================
       LEAD
    ========================================== */

    @Column(unique = true)
    private String leadNo;

    private String leadType;

    private String company;

    /* ==========================================
       WORKER
    ========================================== */

    private String workerId;

    private String workerName;

    /* ==========================================
       CUSTOMER
    ========================================== */

    private String customerName;

    private String mobile;

    private String whatsapp;

    /* ==========================================
       LOCATION
    ========================================== */

    private String country;

    private String state;

    private String district;

    private String area;

    /* ==========================================
       EXISTING BAKERY
    ========================================== */

    private String bakeryName;

    @Column(columnDefinition = "TEXT")
    private String currentProduction;

    @Column(columnDefinition = "TEXT")
    private String existingMachines;

    /* ==========================================
       REFERENCE CUSTOMER
    ========================================== */

    private String referenceCustomerName;

    private String referenceCustomerMobile;

    private String referenceCustomerPlace;

    private String customerRequirement;

    /* ==========================================
       BUSINESS
    ========================================== */

    private String businessType;

    private String businessModel;

    private String plannedOpening;

    private String productionArea;

    private String investment;

    /* ==========================================
       MACHINE
    ========================================== */

    @Column(columnDefinition = "TEXT")
    private String selectedMachines;

    /* ==========================================
       LEAD
    ========================================== */

    private String leadAssessment;

    @Enumerated(EnumType.STRING)
    private LeadStatus status;

    @Enumerated(EnumType.STRING)
    private LeadTemperature temperature;

    @Column(columnDefinition = "TEXT")
    private String remarks;

    /* ==========================================
       AUDIT
    ========================================== */

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    /* ==========================================
       CONSTRUCTOR
    ========================================== */

    public Lead() {

    }
        /* ==========================================
       PRE PERSIST
    ========================================== */

    @PrePersist
    public void prePersist() {

        LocalDateTime now = LocalDateTime.now();

        this.createdAt = now;
        this.updatedAt = now;

        if (status == null) {
            status = LeadStatus.NEW;
        }

        if (temperature == null) {
            temperature = LeadTemperature.WARM;
        }

        if (leadNo == null || leadNo.isBlank()) {
            leadNo = "PHX-" + System.currentTimeMillis();
        }

    }

    /* ==========================================
       PRE UPDATE
    ========================================== */

    @PreUpdate
    public void preUpdate() {

        this.updatedAt = LocalDateTime.now();

    }
        /* ==========================================
       GETTERS & SETTERS
       PART 1
    ========================================== */

    public Long getId() {

        return id;

    }

    public void setId(Long id) {

        this.id = id;

    }

    public String getLeadNo() {

        return leadNo;

    }

    public void setLeadNo(String leadNo) {

        this.leadNo = leadNo;

    }

    public String getLeadType() {

        return leadType;

    }

    public void setLeadType(String leadType) {

        this.leadType = leadType;

    }

    public String getCompany() {

        return company;

    }

    public void setCompany(String company) {

        this.company = company;

    }

    public String getWorkerId() {

        return workerId;

    }

    public void setWorkerId(String workerId) {

        this.workerId = workerId;

    }

    public String getWorkerName() {

        return workerName;

    }

    public void setWorkerName(String workerName) {

        this.workerName = workerName;

    }

    public String getCustomerName() {

        return customerName;

    }

    public void setCustomerName(String customerName) {

        this.customerName = customerName;

    }

    public String getMobile() {

        return mobile;

    }

    public void setMobile(String mobile) {

        this.mobile = mobile;

    }

    public String getWhatsapp() {

        return whatsapp;

    }

    public void setWhatsapp(String whatsapp) {

        this.whatsapp = whatsapp;

    }

    public String getCountry() {

        return country;

    }

    public void setCountry(String country) {

        this.country = country;

    }

    public String getState() {

        return state;

    }

    public void setState(String state) {

        this.state = state;

    }

    public String getDistrict() {

        return district;

    }

    public void setDistrict(String district) {

        this.district = district;

    }

    public String getArea() {

        return area;

    }

    public void setArea(String area) {

        this.area = area;

    }
        /* ==========================================
       GETTERS & SETTERS
       PART 2
    ========================================== */

    public String getBakeryName() {

        return bakeryName;

    }

    public void setBakeryName(String bakeryName) {

        this.bakeryName = bakeryName;

    }

    public String getCurrentProduction() {

        return currentProduction;

    }

    public void setCurrentProduction(String currentProduction) {

        this.currentProduction = currentProduction;

    }

    public String getExistingMachines() {

        return existingMachines;

    }

    public void setExistingMachines(String existingMachines) {

        this.existingMachines = existingMachines;

    }

    public String getReferenceCustomerName() {

        return referenceCustomerName;

    }

    public void setReferenceCustomerName(String referenceCustomerName) {

        this.referenceCustomerName = referenceCustomerName;

    }

    public String getReferenceCustomerMobile() {

        return referenceCustomerMobile;

    }

    public void setReferenceCustomerMobile(String referenceCustomerMobile) {

        this.referenceCustomerMobile = referenceCustomerMobile;

    }

    public String getReferenceCustomerPlace() {

        return referenceCustomerPlace;

    }

    public void setReferenceCustomerPlace(String referenceCustomerPlace) {

        this.referenceCustomerPlace = referenceCustomerPlace;

    }

    public String getCustomerRequirement() {

        return customerRequirement;

    }

    public void setCustomerRequirement(String customerRequirement) {

        this.customerRequirement = customerRequirement;

    }

    public String getBusinessType() {

        return businessType;

    }

    public void setBusinessType(String businessType) {

        this.businessType = businessType;

    }

    public String getBusinessModel() {

        return businessModel;

    }

    public void setBusinessModel(String businessModel) {

        this.businessModel = businessModel;

    }

    public String getPlannedOpening() {

        return plannedOpening;

    }

    public void setPlannedOpening(String plannedOpening) {

        this.plannedOpening = plannedOpening;

    }

    public String getProductionArea() {

        return productionArea;

    }

    public void setProductionArea(String productionArea) {

        this.productionArea = productionArea;

    }

    public String getInvestment() {

        return investment;

    }

    public void setInvestment(String investment) {

        this.investment = investment;

    }
        /* ==========================================
       GETTERS & SETTERS
       PART 3
    ========================================== */

    public String getSelectedMachines() {

        return selectedMachines;

    }

    public void setSelectedMachines(String selectedMachines) {

        this.selectedMachines = selectedMachines;

    }

    public String getLeadAssessment() {

        return leadAssessment;

    }

    public void setLeadAssessment(String leadAssessment) {

        this.leadAssessment = leadAssessment;

    }

    public LeadStatus getStatus() {

        return status;

    }

    public void setStatus(LeadStatus status) {

        this.status = status;

    }

    public LeadTemperature getTemperature() {

        return temperature;

    }

    public void setTemperature(LeadTemperature temperature) {

        this.temperature = temperature;

    }

    public String getRemarks() {

        return remarks;

    }

    public void setRemarks(String remarks) {

        this.remarks = remarks;

    }

    public LocalDateTime getCreatedAt() {

        return createdAt;

    }

    public void setCreatedAt(LocalDateTime createdAt) {

        this.createdAt = createdAt;

    }

    public LocalDateTime getUpdatedAt() {

        return updatedAt;

    }

    public void setUpdatedAt(LocalDateTime updatedAt) {

        this.updatedAt = updatedAt;

    }

}