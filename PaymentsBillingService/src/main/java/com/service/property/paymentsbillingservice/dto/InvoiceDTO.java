package com.service.property.paymentsbillingservice.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class InvoiceDTO {

    private LocalDate startDate;
    private LocalDate endDate;
    private BigDecimal totalAmount;
    private Long tenantId;
}
