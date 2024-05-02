package com.service.property.paymentsbillingservice.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Property {
    private Long id;

    private String name;

    private String address;

    private String description;

    private int numberOfFloors;

    private double totalArea;
}
