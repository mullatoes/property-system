package com.service.property.paymentsbillingservice.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.service.property.paymentsbillingservice.utils.enums.AgreementStatus;
import com.service.property.paymentsbillingservice.utils.enums.PaymentFrequency;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;


@Data
public class Agreement {

    private Long id;

    private double rentAmount;
    private double balanceAmount;

    private Tenant tenant;

    private Long rentedUnitId;

    private PaymentFrequency paymentFrequency;

    private BigDecimal securityDeposit;

    private Date createdDate;

    private AgreementStatus status;

    private Date startDate;


}
