package com.service.property.tenantmanagementservice.dto;

import com.service.property.tenantmanagementservice.enums.UnitStatus;
import lombok.Data;

@Data
public class UnitDto {
    private Long id;
    private String unitNumber;
    private double squareFt;
    private double rentPerSqFt;
    private UnitStatus unitStatus;
}
