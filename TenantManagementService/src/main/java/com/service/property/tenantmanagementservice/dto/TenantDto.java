package com.service.property.tenantmanagementservice.dto;

import com.service.property.tenantmanagementservice.enums.TenantStatus;
import lombok.Data;

import java.time.LocalDate;

@Data
public class TenantDto {

    private String name;

    private String email;

    private String phoneNumber;

    private String address;

    private String occupation;

    private LocalDate dateOfBirth;

    private String emergencyContactPhoneNumber;

    private TenantStatus status;
}
