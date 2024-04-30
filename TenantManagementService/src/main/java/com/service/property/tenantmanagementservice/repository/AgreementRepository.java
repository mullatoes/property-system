package com.service.property.tenantmanagementservice.repository;

import com.service.property.tenantmanagementservice.model.Agreement;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AgreementRepository extends JpaRepository<Agreement, Long> {

    List<Agreement> findAgreementByTenantId(Long tenantId);

}
