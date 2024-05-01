package com.service.property.tenantmanagementservice.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.service.property.tenantmanagementservice.dto.AgreementDto;
import com.service.property.tenantmanagementservice.enums.AgreementStatus;
import com.service.property.tenantmanagementservice.enums.PaymentFrequency;
import com.service.property.tenantmanagementservice.enums.TenantStatus;
import com.service.property.tenantmanagementservice.model.Agreement;
import com.service.property.tenantmanagementservice.model.Tenant;
import com.service.property.tenantmanagementservice.service.AgreementService;
import com.service.property.tenantmanagementservice.utils.ResponseWrapper;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Date;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ContextConfiguration(classes = {AgreementController.class})
@ExtendWith(SpringExtension.class)
@DisabledInAotMode
class AgreementControllerTest {
    @Autowired
    private AgreementController agreementController;

    @MockBean
    private AgreementService agreementService;

    @Test
    void testGetAllAgreements() throws Exception {
        // Arrange
        when(agreementService.getAllAgreements()).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/agreement");

        // Act and Assert
        MockMvcBuilders.standaloneSetup(agreementController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "{\"status\":\"success\",\"message\":\"All agreements retrieved successfully\",\"itemCount\":0,\"data\":[]}"));
    }

    @Test
    void testGetAllAgreements2() throws Exception {
        // Arrange
        Tenant tenant = new Tenant();
        tenant.setAddress("42 Main St");
        tenant.setDateOfBirth(LocalDate.of(1970, 1, 1));
        tenant.setEmail("jane.doe@example.org");
        tenant.setEmergencyContactPhoneNumber("6625550144");
        tenant.setId(1L);
        tenant.setName("success");
        tenant.setOccupation("success");
        tenant.setPhoneNumber("6625550144");
        tenant.setStatus(TenantStatus.ACTIVE);

        Agreement agreement = new Agreement();
        agreement.setCreatedDate(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
        agreement.setEndDate(LocalDate.of(1970, 1, 1));
        agreement.setId(1L);
        agreement.setPaymentFrequency(PaymentFrequency.MONTHLY);
        agreement.setRentAmount(10.0d);
        agreement.setRentedUnitId(1L);
        agreement.setSecurityDeposit(new BigDecimal("2.3"));
        agreement.setStartDate(LocalDate.of(1970, 1, 1));
        agreement.setStatus(AgreementStatus.ACTIVE);
        agreement.setTenant(tenant);

        ArrayList<Agreement> agreementList = new ArrayList<>();
        agreementList.add(agreement);
        when(agreementService.getAllAgreements()).thenReturn(agreementList);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/agreement");

        // Act and Assert
        MockMvcBuilders.standaloneSetup(agreementController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "{\"status\":\"success\",\"message\":\"All agreements retrieved successfully\",\"itemCount\":1,\"data\":[{\"id\":1,"
                                        + "\"startDate\":[1970,1,1],\"endDate\":[1970,1,1],\"rentAmount\":10.0,\"rentedUnitId\":1,\"paymentFrequency\":"
                                        + "\"MONTHLY\",\"securityDeposit\":2.3,\"createdDate\":0,\"status\":\"ACTIVE\"}]}"));
    }

    @Test
    void testCreateAgreement() {
        // Arrange
        Tenant tenant = new Tenant();
        tenant.setAddress("42 Main St");
        tenant.setDateOfBirth(LocalDate.of(1970, 1, 1));
        tenant.setEmail("jane.doe@example.org");
        tenant.setEmergencyContactPhoneNumber("6625550144");
        tenant.setId(1L);
        tenant.setName("Name");
        tenant.setOccupation("Occupation");
        tenant.setPhoneNumber("6625550144");
        tenant.setStatus(TenantStatus.ACTIVE);

        Agreement agreement = new Agreement();
        agreement.setCreatedDate(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
        agreement.setEndDate(LocalDate.of(1970, 1, 1));
        agreement.setId(1L);
        agreement.setPaymentFrequency(PaymentFrequency.MONTHLY);
        agreement.setRentAmount(10.0d);
        agreement.setRentedUnitId(1L);
        agreement.setSecurityDeposit(new BigDecimal("2.3"));
        agreement.setStartDate(LocalDate.of(1970, 1, 1));
        agreement.setStatus(AgreementStatus.ACTIVE);
        agreement.setTenant(tenant);
        AgreementService agreementService = mock(AgreementService.class);
        when(agreementService.createAgreement(Mockito.<Long>any(), Mockito.<AgreementDto>any())).thenReturn(agreement);
        AgreementController agreementController = new AgreementController(agreementService);

        AgreementDto agreementDto = new AgreementDto();
        agreementDto.setEndDate(LocalDate.of(1970, 1, 1));
        agreementDto.setPaymentFrequency(PaymentFrequency.MONTHLY);
        agreementDto.setStartDate(LocalDate.of(1970, 1, 1));
        agreementDto.setUnitId(1L);

        // Act
        ResponseEntity<ResponseWrapper<Agreement>> actualCreateAgreementResult = agreementController.createAgreement(1L,
                agreementDto);

        // Assert
        verify(agreementService).createAgreement(eq(1L), isA(AgreementDto.class));
        ResponseWrapper<Agreement> body = actualCreateAgreementResult.getBody();
        assertEquals("1970-01-01", body.getData().getEndDate().toString());
        assertEquals("Agreement created successfully", body.getMessage());
        assertEquals("success", body.getStatus());
        assertEquals(1, body.getItemCount().intValue());
        assertEquals(200, actualCreateAgreementResult.getStatusCodeValue());
        assertTrue(actualCreateAgreementResult.hasBody());
        assertTrue(actualCreateAgreementResult.getHeaders().isEmpty());
    }

    @Test
    void testGetAgreement() throws Exception {
        // Arrange
        Tenant tenant = new Tenant();
        tenant.setAddress("42 Main St");
        tenant.setDateOfBirth(LocalDate.of(1970, 1, 1));
        tenant.setEmail("jane.doe@example.org");
        tenant.setEmergencyContactPhoneNumber("6625550144");
        tenant.setId(1L);
        tenant.setName("Name");
        tenant.setOccupation("Occupation");
        tenant.setPhoneNumber("6625550144");
        tenant.setStatus(TenantStatus.ACTIVE);

        Agreement agreement = new Agreement();
        agreement.setCreatedDate(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
        agreement.setEndDate(LocalDate.of(1970, 1, 1));
        agreement.setId(1L);
        agreement.setPaymentFrequency(PaymentFrequency.MONTHLY);
        agreement.setRentAmount(10.0d);
        agreement.setRentedUnitId(1L);
        agreement.setSecurityDeposit(new BigDecimal("2.3"));
        agreement.setStartDate(LocalDate.of(1970, 1, 1));
        agreement.setStatus(AgreementStatus.ACTIVE);
        agreement.setTenant(tenant);
        when(agreementService.getAgreement(Mockito.<Long>any())).thenReturn(agreement);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/agreement/{tenantId}", 1L);

        // Act and Assert
        MockMvcBuilders.standaloneSetup(agreementController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "{\"status\":\"success\",\"message\":\"Agreement retrieved successful\",\"itemCount\":1,\"data\":{\"id\":1,\"startDate"
                                        + "\":[1970,1,1],\"endDate\":[1970,1,1],\"rentAmount\":10.0,\"rentedUnitId\":1,\"paymentFrequency\":\"MONTHLY\","
                                        + "\"securityDeposit\":2.3,\"createdDate\":0,\"status\":\"ACTIVE\"}}"));
    }

    @Test
    @Disabled()
    void testCreateAgreement2() throws Exception {

        // Arrange
        AgreementDto agreementDto = new AgreementDto();
        agreementDto.setEndDate(LocalDate.of(1970, 1, 1));
        agreementDto.setPaymentFrequency(PaymentFrequency.MONTHLY);
        agreementDto.setStartDate(LocalDate.of(1970, 1, 1));
        agreementDto.setUnitId(1L);
        String content = (new ObjectMapper()).writeValueAsString(agreementDto);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/agreement/{tenantId}", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);

        // Act
        MockMvcBuilders.standaloneSetup(agreementController).build().perform(requestBuilder);
    }
}
