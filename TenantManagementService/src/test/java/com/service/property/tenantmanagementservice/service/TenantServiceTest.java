package com.service.property.tenantmanagementservice.service;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.service.property.tenantmanagementservice.dto.TenantDto;
import com.service.property.tenantmanagementservice.enums.TenantStatus;
import com.service.property.tenantmanagementservice.model.Tenant;
import com.service.property.tenantmanagementservice.repository.TenantRepository;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {TenantService.class})
@ExtendWith(SpringExtension.class)
@DisabledInAotMode
class TenantServiceTest {
    @MockBean
    private TenantRepository tenantRepository;

    @Autowired
    private TenantService tenantService;

    @Test
    void testGetAllTenants() {
        // Arrange
        ArrayList<Tenant> tenantList = new ArrayList<>();
        when(tenantRepository.findAll()).thenReturn(tenantList);

        // Act
        List<Tenant> actualAllTenants = tenantService.getAllTenants();

        // Assert
        verify(tenantRepository).findAll();
        assertTrue(actualAllTenants.isEmpty());
        assertSame(tenantList, actualAllTenants);
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
        when(tenantRepository.save(Mockito.<Tenant>any())).thenReturn(tenant);

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
        Tenant actualAddTenantResult = tenantService.addTenant(tenantDto);

        // Assert
        verify(tenantRepository).save(isA(Tenant.class));
        assertSame(tenant, actualAddTenantResult);
    }
    
    @Test
    void testGetTenantById() {
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
        Optional<Tenant> ofResult = Optional.of(tenant);
        when(tenantRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);

        // Act
        Tenant actualTenantById = tenantService.getTenantById(1L);

        // Assert
        verify(tenantRepository).findById(eq(1L));
        assertSame(tenant, actualTenantById);
    }
}
