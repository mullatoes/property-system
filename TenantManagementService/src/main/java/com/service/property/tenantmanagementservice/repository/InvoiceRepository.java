package com.service.property.tenantmanagementservice.repository;

import com.service.property.tenantmanagementservice.model.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InvoiceRepository extends JpaRepository<Invoice, Long> {
}
