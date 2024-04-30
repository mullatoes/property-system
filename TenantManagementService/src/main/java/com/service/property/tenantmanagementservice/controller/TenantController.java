package com.service.property.tenantmanagementservice.controller;

import com.service.property.tenantmanagementservice.dto.TenantDto;
import com.service.property.tenantmanagementservice.model.Tenant;
import com.service.property.tenantmanagementservice.service.TenantService;
import com.service.property.tenantmanagementservice.utils.ResponseWrapper;
import com.service.property.tenantmanagementservice.utils.UnSuccessfulWrapper;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/api/tenant")
public class TenantController {

    private final TenantService tenantService;

    public TenantController(TenantService tenantService) {
        this.tenantService = tenantService;
    }

    @GetMapping
    public ResponseEntity<ResponseWrapper<List<Tenant>>> getAllTenants() {
        List<Tenant> tenants = tenantService.getAllTenants();

        if (tenants.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(new ResponseWrapper<>("success",
                "All tenants retrieved successfully", tenants.size(), tenants));
    }

    @PostMapping
    public ResponseEntity<?> addTenant(@RequestBody TenantDto tenantDto) {

        try {

            Tenant tenant = tenantService.addTenant(tenantDto);

            return ResponseEntity.ok(new ResponseWrapper<>("success",
                    tenant.getName() + " created successfully", 1, tenant));

        }catch (DataIntegrityViolationException e){
            String errorMessage = Objects.requireNonNull(e.getRootCause()).getMessage();
            UnSuccessfulWrapper<String> response = new UnSuccessfulWrapper<>("failure", errorMessage);
            return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
        }
    }
}
