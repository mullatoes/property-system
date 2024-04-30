package com.service.property.paymentsbillingservice.repository;

import com.service.property.paymentsbillingservice.model.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InvoiceRepository extends JpaRepository<Invoice, Integer> {
}
