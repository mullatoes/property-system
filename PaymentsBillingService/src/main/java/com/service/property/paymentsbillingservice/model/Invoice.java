package com.service.property.paymentsbillingservice.model;

import com.service.property.paymentsbillingservice.utils.InvoiceFrequency;
import com.service.property.paymentsbillingservice.utils.InvoiceStatus;
import com.service.property.paymentsbillingservice.utils.enums.PaymentFrequency;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;

@Data
@Entity
@Table(name = "invoices")
public class Invoice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(name = "total_amount")
    private BigDecimal totalAmount;

    @Enumerated(EnumType.STRING)
    private InvoiceStatus status;

    @Column(name = "tenant_id")
    private Long tenantId;

    @Enumerated(EnumType.STRING)
    private PaymentFrequency frequency;

    @Column(name = "month")
    @Enumerated(EnumType.STRING)
    private Month month;

    @Column(name = "year")
    private Integer year;

    @Column(name = "balance")
    private BigDecimal balance;

    @Column(name = "invoice_date")
    private LocalDate invoiceDate;

    @Column(name = "end_date")
    private LocalDate endDate;

}
