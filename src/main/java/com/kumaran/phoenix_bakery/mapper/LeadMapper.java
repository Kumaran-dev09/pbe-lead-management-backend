package com.kumaran.phoenix_bakery.mapper;

import org.springframework.stereotype.Component;

import com.kumaran.phoenix_bakery.dto.LeadRequest;
import com.kumaran.phoenix_bakery.dto.LeadResponse;
import com.kumaran.phoenix_bakery.model.Lead;

@Component
public class LeadMapper {

    public LeadResponse toResponse(Lead lead) {
        LeadResponse response = new LeadResponse();
        response.setId(lead.getId());
        response.setLeadNo(lead.getLeadNo());
        response.setLeadType(lead.getLeadType());
        response.setCompany(lead.getCompany());
        response.setWorkerId(lead.getWorkerId());
        response.setWorkerName(lead.getWorkerName());
        response.setWorker(lead.getWorkerName() != null ? lead.getWorkerName() : lead.getWorkerId());
        response.setCustomerName(lead.getCustomerName());
        response.setCustomer(lead.getCustomerName());
        response.setMobile(lead.getMobile());
        response.setWhatsapp(lead.getWhatsapp());
        response.setCountry(lead.getCountry());
        response.setState(lead.getState());
        response.setDistrict(lead.getDistrict());
        response.setArea(lead.getArea());
        response.setBakeryName(lead.getBakeryName());
        response.setCurrentProduction(lead.getCurrentProduction());
        response.setExistingMachines(lead.getExistingMachines());
        response.setReferenceName(lead.getReferenceCustomerName());
        response.setReferenceMobile(lead.getReferenceCustomerMobile());
        response.setReferencePlace(lead.getReferenceCustomerPlace());
        response.setCustomerRequirement(lead.getCustomerRequirement());
        response.setBusinessType(lead.getBusinessType());
        response.setBusinessModel(lead.getBusinessModel());
        response.setOpeningTimeline(lead.getPlannedOpening());
        response.setPlannedOpening(lead.getPlannedOpening());
        response.setProductionArea(lead.getProductionArea());
        response.setInvestment(lead.getInvestment());
        response.setSelectedMachines(lead.getSelectedMachines());
        response.setAssessment(lead.getLeadAssessment());
        response.setLeadAssessment(lead.getLeadAssessment());
        response.setStatus(lead.getStatus() != null ? lead.getStatus().name() : null);
        response.setRemarks(lead.getRemarks());
        response.setCreatedAt(lead.getCreatedAt());
        response.setUpdatedAt(lead.getUpdatedAt());
        return response;
    }

    public Lead toEntity(LeadRequest request) {
        Lead lead = new Lead();
        apply(lead, request);
        return lead;
    }

    public void apply(Lead lead, LeadRequest request) {
        lead.setLeadType(request.getLeadType());
        lead.setCompany(request.getCompany());
        lead.setWorkerId(request.getWorkerId());
        lead.setWorkerName(request.getWorkerName());
        lead.setCustomerName(request.getCustomerName());
        lead.setMobile(request.getMobile());
        lead.setWhatsapp(request.getWhatsapp());
        lead.setCountry(request.getCountry());
        lead.setState(request.getState());
        lead.setDistrict(request.getDistrict());
        lead.setArea(request.getArea());
        lead.setBakeryName(request.getBakeryName());
        lead.setCurrentProduction(request.getCurrentProduction());
        lead.setExistingMachines(request.getExistingMachines());
        lead.setReferenceCustomerName(request.getReferenceCustomerName());
        lead.setReferenceCustomerMobile(request.getReferenceCustomerMobile());
        lead.setReferenceCustomerPlace(request.getReferenceCustomerPlace());
        lead.setCustomerRequirement(request.getCustomerRequirement());
        lead.setBusinessType(request.getBusinessType());
        lead.setBusinessModel(request.getBusinessModel());
        lead.setPlannedOpening(request.getPlannedOpening());
        lead.setProductionArea(request.getProductionArea());
        lead.setInvestment(request.getInvestment());
        lead.setSelectedMachines(request.getSelectedMachines());
        lead.setLeadAssessment(request.getLeadAssessment());
        lead.setRemarks(request.getRemarks());
    }
}
