package com.service.property.propertymanagementservice.dto;

import lombok.Data;

@Data
public class PropertyDto {

    private String name;

    private String address;

    private String description;

    private int numberOfFloors;

    private double totalArea;
}
