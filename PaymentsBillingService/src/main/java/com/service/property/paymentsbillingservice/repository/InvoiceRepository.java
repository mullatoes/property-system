package com.service.property.paymentsbillingservice.repository;

import com.service.property.paymentsbillingservice.model.Invoice;
import com.service.property.paymentsbillingservice.model.Payment;
import java.time.Month;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface InvoiceRepository extends JpaRepository<Invoice, Integer> {
@Query("select i from Invoice i where i.tenantId= :tenantId and i.month= :month and i.year=:year")
    Optional<Invoice> findByTenantIdAndMonthAndYear(Long tenantId, Month month, int year);

    Invoice findById(Long id);

}
