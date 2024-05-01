package com.service.property.tenantmanagementservice.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.service.property.tenantmanagementservice.dto.TenantDto;
import com.service.property.tenantmanagementservice.enums.TenantStatus;
import com.service.property.tenantmanagementservice.model.Tenant;
import com.service.property.tenantmanagementservice.repository.TenantRepository;
import com.service.property.tenantmanagementservice.service.TenantService;
import com.service.property.tenantmanagementservice.utils.ResponseWrapper;
import java.time.LocalDate;
import java.util.ArrayList;
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
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ContextConfiguration(classes = {TenantController.class})
@ExtendWith(SpringExtension.class)
@DisabledInAotMode
class TenantControllerTest {
    @Autowired
    private TenantController tenantController;

    @MockBean
    private TenantService tenantService;

    @Test
    void testGetAllTenants() throws Exception {
        // Arrange
        when(tenantService.getAllTenants()).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/tenant");

        // Act
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(tenantController)
                .build()
                .perform(requestBuilder);

        // Assert
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    void testGetAllTenants2() throws Exception {
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

        ArrayList<Tenant> tenantList = new ArrayList<>();
        tenantList.add(tenant);
        when(tenantService.getAllTenants()).thenReturn(tenantList);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/tenant");

        // Act and Assert
        MockMvcBuilders.standaloneSetup(tenantController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "{\"status\":\"success\",\"message\":\"All tenants retrieved successfully\",\"itemCount\":1,\"data\":[{\"id\":1,"
                                        + "\"name\":\"Name\",\"email\":\"jane.doe@example.org\",\"phoneNumber\":\"6625550144\",\"address\":\"42 Main"
                                        + " St\",\"occupation\":\"Occupation\",\"dateOfBirth\":[1970,1,1],\"emergencyContactPhoneNumber\":\"6625550144\""
                                        + ",\"status\":\"ACTIVE\"}]}"));
    }

    @Test
    void testAddTenant() {

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
        TenantRepository tenantRepository = mock(TenantRepository.class);
        when(tenantRepository.save(Mockito.<Tenant>any())).thenReturn(tenant);
        TenantController tenantController = new TenantController(new TenantService(tenantRepository));

        TenantDto tenantDto = new TenantDto();
        tenantDto.setAddress("42 Main St");
        tenantDto.setDateOfBirth(LocalDate.of(1970, 1, 1));
        tenantDto.setEmail("jane.doe@example.org");
        tenantDto.setEmergencyContactPhoneNumber("6625550144");
        tenantDto.setName("Name");
        tenantDto.setOccupation("Occupation");
        tenantDto.setPhoneNumber("6625550144");
        tenantDto.setStatus(TenantStatus.ACTIVE);

        // Act
        ResponseEntity<?> actualAddTenantResult = tenantController.addTenant(tenantDto);

        // Assert
        verify(tenantRepository).save(isA(Tenant.class));
        assertEquals("Name created successfully", ((ResponseWrapper<Object>) actualAddTenantResult.getBody()).getMessage());
        assertEquals("success", ((ResponseWrapper<Object>) actualAddTenantResult.getBody()).getStatus());
        assertEquals(1, ((ResponseWrapper<Object>) actualAddTenantResult.getBody()).getItemCount().intValue());
        assertEquals(200, actualAddTenantResult.getStatusCodeValue());
        assertTrue(actualAddTenantResult.hasBody());
        assertTrue(actualAddTenantResult.getHeaders().isEmpty());
        assertSame(tenant, ((ResponseWrapper<Object>) actualAddTenantResult.getBody()).getData());
    }

    @Test
    void testAddTenant2() {
        // Arrange
        Tenant tenant = mock(Tenant.class);
        when(tenant.getName()).thenReturn("Name");
        doNothing().when(tenant).setAddress(Mockito.<String>any());
        doNothing().when(tenant).setDateOfBirth(Mockito.<LocalDate>any());
        doNothing().when(tenant).setEmail(Mockito.<String>any());
        doNothing().when(tenant).setEmergencyContactPhoneNumber(Mockito.<String>any());
        doNothing().when(tenant).setId(Mockito.<Long>any());
        doNothing().when(tenant).setName(Mockito.<String>any());
        doNothing().when(tenant).setOccupation(Mockito.<String>any());
        doNothing().when(tenant).setPhoneNumber(Mockito.<String>any());
        doNothing().when(tenant).setStatus(Mockito.<TenantStatus>any());
        tenant.setAddress("42 Main St");
        tenant.setDateOfBirth(LocalDate.of(1970, 1, 1));
        tenant.setEmail("jane.doe@example.org");
        tenant.setEmergencyContactPhoneNumber("6625550144");
        tenant.setId(1L);
        tenant.setName("Name");
        tenant.setOccupation("Occupation");
        tenant.setPhoneNumber("6625550144");
        tenant.setStatus(TenantStatus.ACTIVE);
        TenantRepository tenantRepository = mock(TenantRepository.class);
        when(tenantRepository.save(Mockito.<Tenant>any())).thenReturn(tenant);
        TenantController tenantController = new TenantController(new TenantService(tenantRepository));

        TenantDto tenantDto = new TenantDto();
        tenantDto.setAddress("42 Main St");
        tenantDto.setDateOfBirth(LocalDate.of(1970, 1, 1));
        tenantDto.setEmail("jane.doe@example.org");
        tenantDto.setEmergencyContactPhoneNumber("6625550144");
        tenantDto.setName("Name");
        tenantDto.setOccupation("Occupation");
        tenantDto.setPhoneNumber("6625550144");
        tenantDto.setStatus(TenantStatus.ACTIVE);

        // Act
        ResponseEntity<?> actualAddTenantResult = tenantController.addTenant(tenantDto);

        // Assert
        verify(tenant).getName();
        verify(tenant).setAddress(eq("42 Main St"));
        verify(tenant).setDateOfBirth(isA(LocalDate.class));
        verify(tenant).setEmail(eq("jane.doe@example.org"));
        verify(tenant).setEmergencyContactPhoneNumber(eq("6625550144"));
        verify(tenant).setId(eq(1L));
        verify(tenant).setName(eq("Name"));
        verify(tenant).setOccupation(eq("Occupation"));
        verify(tenant).setPhoneNumber(eq("6625550144"));
        verify(tenant).setStatus(eq(TenantStatus.ACTIVE));
        verify(tenantRepository).save(isA(Tenant.class));
        assertEquals("Name created successfully", ((ResponseWrapper<Object>) actualAddTenantResult.getBody()).getMessage());
        assertEquals("success", ((ResponseWrapper<Object>) actualAddTenantResult.getBody()).getStatus());
        assertEquals(1, ((ResponseWrapper<Object>) actualAddTenantResult.getBody()).getItemCount().intValue());
        assertEquals(200, actualAddTenantResult.getStatusCodeValue());
        assertTrue(actualAddTenantResult.hasBody());
        assertTrue(actualAddTenantResult.getHeaders().isEmpty());
    }

    @Test
    void testAddTenant3() {
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
        TenantService tenantService = mock(TenantService.class);
        when(tenantService.addTenant(Mockito.<TenantDto>any())).thenReturn(tenant);
        TenantController tenantController = new TenantController(tenantService);

        TenantDto tenantDto = new TenantDto();
        tenantDto.setAddress("42 Main St");
        tenantDto.setDateOfBirth(LocalDate.of(1970, 1, 1));
        tenantDto.setEmail("jane.doe@example.org");
        tenantDto.setEmergencyContactPhoneNumber("6625550144");
        tenantDto.setName("Name");
        tenantDto.setOccupation("Occupation");
        tenantDto.setPhoneNumber("6625550144");
        tenantDto.setStatus(TenantStatus.ACTIVE);

        // Act
        ResponseEntity<?> actualAddTenantResult = tenantController.addTenant(tenantDto);

        // Assert
        verify(tenantService).addTenant(isA(TenantDto.class));
        assertEquals("Name created successfully", ((ResponseWrapper<Object>) actualAddTenantResult.getBody()).getMessage());
        assertEquals("success", ((ResponseWrapper<Object>) actualAddTenantResult.getBody()).getStatus());
        assertEquals(1, ((ResponseWrapper<Object>) actualAddTenantResult.getBody()).getItemCount().intValue());
        assertEquals(200, actualAddTenantResult.getStatusCodeValue());
        assertTrue(actualAddTenantResult.hasBody());
        assertTrue(actualAddTenantResult.getHeaders().isEmpty());
        assertSame(tenant, ((ResponseWrapper<Object>) actualAddTenantResult.getBody()).getData());
    }

    @Test
    void testGetTenantById() throws Exception {
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
        when(tenantService.getTenantById(Mockito.<Long>any())).thenReturn(tenant);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/tenant/{id}", 1L);

        // Act and Assert
        MockMvcBuilders.standaloneSetup(tenantController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "{\"status\":\"success\",\"message\":\"All tenants retrieved successfully\",\"itemCount\":1,\"data\":{\"id\":1,"
                                        + "\"name\":\"Name\",\"email\":\"jane.doe@example.org\",\"phoneNumber\":\"6625550144\",\"address\":\"42 Main"
                                        + " St\",\"occupation\":\"Occupation\",\"dateOfBirth\":[1970,1,1],\"emergencyContactPhoneNumber\":\"6625550144\""
                                        + ",\"status\":\"ACTIVE\"}}"));
    }

    @Test
    @Disabled()
    void testAddTenant4() throws Exception {
        // Arrange
        TenantDto tenantDto = new TenantDto();
        tenantDto.setAddress("42 Main St");
        tenantDto.setDateOfBirth(LocalDate.of(1970, 1, 1));
        tenantDto.setEmail("jane.doe@example.org");
        tenantDto.setEmergencyContactPhoneNumber("6625550144");
        tenantDto.setName("Name");
        tenantDto.setOccupation("Occupation");
        tenantDto.setPhoneNumber("6625550144");
        tenantDto.setStatus(TenantStatus.ACTIVE);
        String content = (new ObjectMapper()).writeValueAsString(tenantDto);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/tenant")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);

        // Act
        MockMvcBuilders.standaloneSetup(tenantController).build().perform(requestBuilder);
    }
}
