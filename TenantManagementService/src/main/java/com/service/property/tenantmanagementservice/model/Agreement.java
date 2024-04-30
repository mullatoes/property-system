package com.service.property.tenantmanagementservice.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.service.property.tenantmanagementservice.dto.UnitDto;
import com.service.property.tenantmanagementservice.enums.AgreementStatus;
import com.service.property.tenantmanagementservice.enums.PaymentFrequency;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;

@Data
@Entity
@Table(name = "agreements")
public class Agreement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDate startDate;

    @Column(nullable = false)
    private LocalDate endDate;

    @Column(nullable = false)
    private double rentAmount;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tenant_id", nullable = false)
    private Tenant tenant;

    @Column(nullable = false)
    private Long rentedUnitId;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private PaymentFrequency paymentFrequency = PaymentFrequency.MONTHLY;

    @Column(nullable = false)
    private BigDecimal securityDeposit;

    @Column(nullable = false)
    private Date createdDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AgreementStatus status = AgreementStatus.ACTIVE;

}
