package com.service.property.tenantmanagementservice.repository;

import com.service.property.tenantmanagementservice.model.Tenant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TenantRepository extends JpaRepository<Tenant, Long> {

}
