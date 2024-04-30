package com.service.property.tenantmanagementservice.dto;

import com.service.property.tenantmanagementservice.enums.InvoiceFrequency;
import lombok.Data;

import java.time.LocalDate;

@Data
public class InvoiceDTO {
    private LocalDate billingPeriodStart;
    private LocalDate billingPeriodEnd;
    private InvoiceFrequency frequency;
}
