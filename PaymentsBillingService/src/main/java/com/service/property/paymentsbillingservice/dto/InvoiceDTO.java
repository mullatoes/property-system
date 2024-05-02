package com.service.property.paymentsbillingservice.dto;

import lombok.Data;

import java.time.LocalDate;
import java.time.Month;

@Data
public class InvoiceDTO {

    private LocalDate dateInvoiced;

    private Month month;

    private Integer year;

    private Long tenantId;


}
