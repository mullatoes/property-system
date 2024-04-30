package com.service.property.tenantmanagementservice.service;

import com.service.property.tenantmanagementservice.dto.TenantDto;
import com.service.property.tenantmanagementservice.model.Tenant;
import com.service.property.tenantmanagementservice.repository.TenantRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TenantService {

    private final TenantRepository tenantRepository;

    public TenantService(TenantRepository tenantRepository) {
        this.tenantRepository = tenantRepository;
    }


    public List<Tenant> getAllTenants() {
        return tenantRepository.findAll();
    }

    public Tenant addTenant(TenantDto tenantDto) {
        Tenant tenant = new Tenant();
        tenant.setName(tenantDto.getName());
        tenant.setAddress(tenantDto.getAddress());
        tenant.setEmail(tenantDto.getEmail());
        tenant.setOccupation(tenantDto.getOccupation());
        tenant.setDateOfBirth(tenantDto.getDateOfBirth());
        tenant.setPhoneNumber(tenantDto.getPhoneNumber());
        tenant.setEmergencyContactPhoneNumber(tenantDto.getEmergencyContactPhoneNumber());
        return tenantRepository.save(tenant);
    }
}
