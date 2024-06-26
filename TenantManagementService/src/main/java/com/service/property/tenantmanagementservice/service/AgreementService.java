package com.service.property.tenantmanagementservice.service;

import com.service.property.tenantmanagementservice.dto.AgreementDto;
import com.service.property.tenantmanagementservice.dto.MailDTO;
import com.service.property.tenantmanagementservice.dto.UnitDto;
import com.service.property.tenantmanagementservice.enums.PaymentFrequency;
import com.service.property.tenantmanagementservice.enums.UnitStatus;
import com.service.property.tenantmanagementservice.exceptions.UnitNotFoundException;
import com.service.property.tenantmanagementservice.exceptions.UnitOccupiedException;
import com.service.property.tenantmanagementservice.feign.NotificationInterface;
import com.service.property.tenantmanagementservice.feign.UnitInterface;
import com.service.property.tenantmanagementservice.model.Agreement;
import com.service.property.tenantmanagementservice.model.Tenant;
import com.service.property.tenantmanagementservice.repository.AgreementRepository;
import com.service.property.tenantmanagementservice.repository.TenantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;

import jdk.jfr.Frequency;

@Service
public class AgreementService {

    private final AgreementRepository agreementRepository;
    private final TenantRepository tenantRepository;

    @Autowired
    UnitInterface unitInterface;

    @Autowired
    NotificationInterface notificationInterface;

    public AgreementService(AgreementRepository agreementRepository, TenantRepository tenantRepository) {
        this.agreementRepository = agreementRepository;
        this.tenantRepository = tenantRepository;
    }

    public List<Agreement> getAllAgreements() {
        return agreementRepository.findAll();
    }

    public Agreement createAgreement(Long tenantId, AgreementDto agreementDto) {
        //find tenant first
        Tenant tenant = tenantRepository.findById(tenantId).get();

        // Retrieve unit information from the property service
        //Feign takes place here
        try {

            UnitDto rentedUnit = Objects.requireNonNull(unitInterface.getUnitById(agreementDto.getUnitId()).getBody()).getData();


            //check unit status before assigning it to tenant
            boolean isOccupied = rentedUnit.getUnitStatus().equals(UnitStatus.OCCUPIED);

            if (isOccupied) {
                throw new UnitOccupiedException("Unit is occupied. Please select another unit");
            }

            //this unit is occupied... update its status
            unitInterface.updateUnitStatus(agreementDto.getUnitId());

            System.out.println("Unit Number is: " + rentedUnit.getUnitNumber());

            Date now = new Date();

            Agreement agreement = new Agreement();
            agreement.setTenant(tenant);
            agreement.setStartDate(agreementDto.getStartDate());
            agreement.setEndDate(agreementDto.getEndDate());
            agreement.setCreatedDate(now);

            if (agreementDto.getPaymentFrequency() != null){
                agreement.setPaymentFrequency(agreementDto.getPaymentFrequency());
            }

            double totalRent = rentedUnit.getRentPerSqFt() * rentedUnit.getSquareFt();

            double rentDepositAmount = totalRent * 3;

            agreement.setSecurityDeposit(BigDecimal.valueOf(rentDepositAmount));

            agreement.setRentAmount(totalRent);
            //we also need unit... its available from the property service
            agreement.setRentedUnitId(rentedUnit.getId());

            agreementRepository.save(agreement);

            if (tenant.getEmail() != null) {
                sendTenancyAgreementEmail(tenant, agreement);
            }

            return agreement;

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error while creating agreement: " + e.getMessage());
            throw new UnitNotFoundException("Unit not found");
        }
    }

    public Agreement getAgreement(Long tenantId) {

        List<Agreement> agreements = agreementRepository.findAgreementByTenantId(tenantId);

        if (!agreements.isEmpty()) {
            return agreements.get(0);
        }

        return null;
    }

    public Agreement getAgreementById(Long id) {

  return agreementRepository.findById(id).get();

     }

    public Agreement getAgreementWithUnitId(Long rentedUnitId) {

        List<Agreement> agreements = agreementRepository.findAgreementByRentedUnitId(rentedUnitId);

        if (!agreements.isEmpty()) {
            return agreements.get(0);
        }

        return null;
    }

    private void sendTenancyAgreementEmail(Tenant tenant, Agreement agreement) {
        MailDTO mailDTO = new MailDTO();

        String mailContent = "Dear " + tenant.getName() + ",\n" +
                "We trust this email finds you well. Below are agreement details for the rented unit(s), you will find your tenancy agreement for the rented property." +
                "Agreement Details are follows: " +
                "1. Start Date: " + agreement.getStartDate() + "\n" +
                "2. End Date: " + agreement.getEndDate() + "\n" +
                "3. Payment Frequency: " + agreement.getPaymentFrequency()+ "\n" +
                "4. Security Deposit: " + agreement.getSecurityDeposit() + "\n" +
                "5. Rent Amount: " + agreement.getRentAmount();

        mailDTO.setTo(tenant.getEmail());
        mailDTO.setContent(mailContent);

        Object mailSendObject = Objects.requireNonNull(notificationInterface.sendMail(mailDTO).getBody());

    }
}
