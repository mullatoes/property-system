package com.service.property.propertymanagementservice.dto;

import com.service.property.propertymanagementservice.enums.UnitStatus;
import lombok.Data;

@Data
public class UnitDto {

    private String unitNumber;

    private double squareFt;

    private UnitStatus unitStatus;

    private double rentPerSqFt;
}
