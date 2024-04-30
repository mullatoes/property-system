package com.service.property.paymentsbillingservice.dto;

import com.service.property.paymentsbillingservice.utils.TenantStatus;
import lombok.Data;

import java.time.LocalDate;

@Data
public class Tenant {

    private Long id;

    private String name;

    private String email;

    private String phoneNumber;

    private String address;

    private String occupation;

    private LocalDate dateOfBirth;

    private String emergencyContactPhoneNumber;

    private String status;
}
