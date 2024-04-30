package com.service.property.paymentsbillingservice.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class InvoiceDTO {

    private LocalDate startDate;
    private LocalDate endDate;
    private Long tenantId;
}
