package com.service.property.paymentsbillingservice.dto;

import lombok.Data;

import java.time.Month;

@Data
public class YearMonthDto {
    private Month month;
    private int year;
}
