package com.service.property.paymentsbillingservice.repository;


import com.service.property.paymentsbillingservice.dto.Agreement;
import com.service.property.paymentsbillingservice.model.Payment;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, Integer> {
    Payment findById(Long id);


}
