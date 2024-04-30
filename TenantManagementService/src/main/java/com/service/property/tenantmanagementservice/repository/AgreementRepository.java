package com.service.property.tenantmanagementservice.repository;

import com.service.property.tenantmanagementservice.model.Agreement;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AgreementRepository extends JpaRepository<Agreement, Long> {
}
