package com.service.property.tenantmanagementservice.model;

import com.service.property.tenantmanagementservice.enums.TenantStatus;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity
@Table(name = "tenants")
public class Tenant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String phoneNumber;

    private String address;

    private String occupation;

    @Column(nullable = false)
    private LocalDate dateOfBirth;

    @Column(name = "emergency_contact_phone_number")
    private String emergencyContactPhoneNumber;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TenantStatus status = TenantStatus.INACTIVE;
}


