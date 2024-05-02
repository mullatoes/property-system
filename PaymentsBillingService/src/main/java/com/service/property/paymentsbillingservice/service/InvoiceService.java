package com.service.property.paymentsbillingservice.service;

import com.service.property.paymentsbillingservice.dto.Agreement;
import com.service.property.paymentsbillingservice.dto.InvoiceDTO;
import com.service.property.paymentsbillingservice.dto.Property;
import com.service.property.paymentsbillingservice.dto.Tenant;
import com.service.property.paymentsbillingservice.dto.Unit;
import com.service.property.paymentsbillingservice.exceptions.CustomNotFoundException;
import com.service.property.paymentsbillingservice.feign.PropertyInterface;
import com.service.property.paymentsbillingservice.feign.TenantInterface;
import com.service.property.paymentsbillingservice.model.Invoice;
import com.service.property.paymentsbillingservice.repository.InvoiceRepository;
import com.service.property.paymentsbillingservice.utils.InvoiceStatus;
import com.service.property.paymentsbillingservice.utils.ResponseWrapper;
import com.service.property.paymentsbillingservice.utils.enums.AgreementStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;
import java.time.Year;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class InvoiceService {

    private final InvoiceRepository invoiceRepository;

    private final TenantInterface tenantInterface;

    private final PropertyInterface propertyInterface;


    public InvoiceService(InvoiceRepository invoiceRepository, TenantInterface tenantInterface, PropertyInterface propertyInterface) {
        this.invoiceRepository = invoiceRepository;
        this.tenantInterface = tenantInterface;
        this.propertyInterface = propertyInterface;
    }

    public List<Invoice> getAllinvoices() {
        return invoiceRepository.findAll();
    }

    public Invoice createInvoice(Long agreeementId, Month month, int year) {
        System.out.println("Kuja hapa");
        Agreement agreement = tenantInterface.getAgreementById(agreeementId).getBody().getData();

        System.out.println("Agreement Amount: " + agreement.getRentAmount());

        LocalDate firstDayOfMonth = LocalDate.of(year, month, 1);
        if (agreement == null || !agreement.getStatus().equals(AgreementStatus.ACTIVE)) {
            throw new IllegalArgumentException("The agreement is not active. Cannot proceed billing");
        }

        LocalDate agreementDate = agreement.getStartDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

        System.out.println("Kuja hapa::::date is " + agreementDate);
        if (firstDayOfMonth.isBefore(agreementDate)) {
            throw new IllegalArgumentException("Error!! The period being invoiced is before contract was in force");

        }
        System.out.println("Kuja hapa::::3");


//        boolean invoiced = doesInvoiceExistForMonthAndYear(agreement.getTenant().getId(), month, year);


//        if (invoiced){
//            // the invoice exists
//            throw new IllegalArgumentException("The period being invoiced has already been invoiced previously");
//        }

//        validateCreateInvoiceDTO(invoiceDTO);

        Invoice invoice = new Invoice();
        invoice.setInvoiceDate(LocalDate.now());

        invoice.setMonth(month);
        invoice.setYear(year);
        invoice.setTotalAmount(BigDecimal.valueOf(agreement.getRentAmount()));
        invoice.setStatus(InvoiceStatus.PENDING);
        invoice.setTenantId(Long.valueOf(15));
        invoice.setFrequency(agreement.getPaymentFrequency());
        invoice.setBalance(BigDecimal.valueOf(0));
        System.out.println("About to save invoice: " + invoice.toString());
        return invoiceRepository.save(invoice);

    }

    public Invoice createInvoiceFromUnit(Long unitId, Month month, int year) {

        //we need to call tenants service
        Unit unit = Objects.requireNonNull(propertyInterface.getUnitById(unitId).getBody()).getData();

        if (unit == null) {
            throw new IllegalArgumentException("Cannot find specified unit. Please contact administrator for help");
        }

        if (unit.getUnitStatus().equals("VACANT")) {
            throw new IllegalArgumentException("The find specified unit is vacant");
        }

        Agreement agreement = Objects.requireNonNull(tenantInterface.getAgreementWithUnitId(unitId).getBody()).getData();
        if (agreement == null || !agreement.getStatus().equals(AgreementStatus.ACTIVE)) {
            throw new IllegalArgumentException("The unit has not active tenant");
        }


        //check if period is invoiced
        boolean invoiced = doesInvoiceExistForMonthAndYear(agreement.getTenant().getId(), month, year);

        if (invoiced) {
            // the invoice exists
            throw new IllegalArgumentException("The period being invoiced has already been invoiced previously");
        }

//        validateCreateInvoiceDTO(invoiceDTO);

        Invoice invoice = new Invoice();

        invoice.setMonth(month);
        invoice.setYear(year);
        invoice.setTotalAmount(BigDecimal.valueOf(agreement.getRentAmount()));
        invoice.setStatus(InvoiceStatus.PENDING);
        invoice.setTenantId(Long.valueOf(15));
        invoice.setFrequency(agreement.getPaymentFrequency());
        invoice.setBalance(BigDecimal.valueOf(0));
        return invoiceRepository.save(invoice);

    }

    public Invoice createInvoiceFromProperty(Long propertyId, Month month, int year) {

        //we need to call tenants service
        Property property = Objects.requireNonNull(propertyInterface.getPropertyById(propertyId).getBody()).getData();

        if (property == null) {
            throw new IllegalArgumentException("Cannot find property");
        }
        List<Unit> unitList = Objects.requireNonNull(propertyInterface.getUnitsByPropertyId(propertyId).getBody()).getData();
        for (Unit u : unitList) {
            Invoice invoice = createInvoiceFromUnit(u.getId(),month,year);
            return invoice;

        }

        return null;
    }


    private Agreement getAgreementByTenantId(Long tenantId) {
        ResponseEntity<ResponseWrapper<Agreement>> response = tenantInterface.getAgreement(tenantId);
        return Objects.requireNonNull(response.getBody()).getData();
    }

    private void validateCreateInvoiceDTO(InvoiceDTO invoiceDTO) {
        if (invoiceDTO == null ||
                invoiceDTO.getDateInvoiced() == null ||
                invoiceDTO.getMonth() == null ||
                invoiceDTO.getYear() == null ||
                invoiceDTO.getTenantId() == null) {
            throw new IllegalArgumentException("Invalid input parameters for creating invoice");
        }
    }

    private void validateCreateInvoiceDTO(Long tenantId, Month month, int year) {
        if (tenantId == null) {
            throw new IllegalArgumentException("Invalid input parameters for creating invoice");
        }
    }

    public boolean doesInvoiceExistForMonthAndYear(Long tenantId, Month month, int year) {
        Optional<Invoice> invoiceOptional = invoiceRepository.findByTenantIdAndMonthAndYear(tenantId, month, year);
        return invoiceOptional.isPresent();
    }

    public boolean isInvoiceBeforeContractStartDate(Long tenantId, Month month, int year) {
        Optional<Invoice> invoiceOptional = invoiceRepository.findByTenantIdAndMonthAndYear(tenantId, month, year);
        return invoiceOptional.isPresent();
    }


}
