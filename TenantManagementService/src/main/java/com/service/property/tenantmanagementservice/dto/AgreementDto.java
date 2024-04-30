package com.service.property.tenantmanagementservice.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class AgreementDto {

    private LocalDate startDate;

    private LocalDate endDate;

    private Long unitId;
}
