package com.service.property.paymentsbillingservice.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class Unit {

    private Long id;

    private String unitNumber;

    private double squareFt;

    private double rentPerSqFt;

    private double totalRent;

    private String unitStatus;

    public double getTotalRent() {
        return squareFt * totalRent;
    }
}
