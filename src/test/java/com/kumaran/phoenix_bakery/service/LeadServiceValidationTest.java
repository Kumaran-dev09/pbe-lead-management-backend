package com.kumaran.phoenix_bakery.service;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.kumaran.phoenix_bakery.dto.LeadRequest;
import com.kumaran.phoenix_bakery.mapper.LeadMapper;
import com.kumaran.phoenix_bakery.repository.LeadRepository;

@ExtendWith(MockitoExtension.class)
class LeadServiceValidationTest {

    @Mock
    private LeadRepository leadRepository;

    @Mock
    private LeadMapper leadMapper;

    @InjectMocks
    private LeadServiceImpl leadService;

    @Test
    void saveLeadShouldRejectMissingRequiredFieldsForNewBakery() {
        LeadRequest request = new LeadRequest();
        request.setLeadType("NEW_BAKERY");
        request.setCompany("Bakery");

        assertThrows(IllegalArgumentException.class, () -> leadService.saveLead(request));
    }
}
