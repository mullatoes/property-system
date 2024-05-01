package com.service.property.tenantmanagementservice.service;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.service.property.tenantmanagementservice.dto.InvoiceDTO;
import com.service.property.tenantmanagementservice.enums.AgreementStatus;
import com.service.property.tenantmanagementservice.enums.InvoiceFrequency;
import com.service.property.tenantmanagementservice.enums.InvoiceStatus;
import com.service.property.tenantmanagementservice.enums.PaymentFrequency;
import com.service.property.tenantmanagementservice.enums.TenantStatus;
import com.service.property.tenantmanagementservice.model.Agreement;
import com.service.property.tenantmanagementservice.model.Invoice;
import com.service.property.tenantmanagementservice.model.Tenant;
import com.service.property.tenantmanagementservice.repository.AgreementRepository;
import com.service.property.tenantmanagementservice.repository.InvoiceRepository;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {InvoiceService.class})
@ExtendWith(SpringExtension.class)
@DisabledInAotMode
class InvoiceServiceTest {
    @MockBean
    private AgreementRepository agreementRepository;

    @MockBean
    private InvoiceRepository invoiceRepository;

    @Autowired
    private InvoiceService invoiceService;

    @Test
    void testGetInvoices() {
        // Arrange
        ArrayList<Invoice> invoiceList = new ArrayList<>();
        when(invoiceRepository.findAll()).thenReturn(invoiceList);

        // Act
        List<Invoice> actualInvoices = invoiceService.getInvoices();

        // Assert
        verify(invoiceRepository).findAll();
        assertTrue(actualInvoices.isEmpty());
        assertSame(invoiceList, actualInvoices);
    }

    @Test
    void testCreateInvoice() {
        // Arrange
        Tenant tenant = new Tenant();
        tenant.setAddress("42 Main St");
        tenant.setDateOfBirth(LocalDate.of(1970, 1, 1));
        tenant.setEmail("jane.doe@example.org");
        tenant.setEmergencyContactPhoneNumber("6625550144");
        tenant.setId(1L);
        tenant.setName("Name");
        tenant.setOccupation("Occupation");
        tenant.setPhoneNumber("6625550144");
        tenant.setStatus(TenantStatus.ACTIVE);

        Agreement agreement = new Agreement();
        agreement.setCreatedDate(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
        agreement.setEndDate(LocalDate.of(1970, 1, 1));
        agreement.setId(1L);
        agreement.setPaymentFrequency(PaymentFrequency.MONTHLY);
        agreement.setRentAmount(10.0d);
        agreement.setRentedUnitId(1L);
        agreement.setSecurityDeposit(new BigDecimal("2.3"));
        agreement.setStartDate(LocalDate.of(1970, 1, 1));
        agreement.setStatus(AgreementStatus.ACTIVE);
        agreement.setTenant(tenant);

        Invoice invoice = new Invoice();
        invoice.setAgreement(agreement);
        invoice.setAmountDue(10.0d);
        invoice.setBillingPeriodEnd(LocalDate.of(1970, 1, 1));
        invoice.setBillingPeriodStart(LocalDate.of(1970, 1, 1));
        invoice.setFrequency(InvoiceFrequency.MONTHLY);
        invoice.setId(1L);
        invoice.setStatus(InvoiceStatus.PAID);
        when(invoiceRepository.save(Mockito.<Invoice>any())).thenReturn(invoice);

        Tenant tenant2 = new Tenant();
        tenant2.setAddress("42 Main St");
        tenant2.setDateOfBirth(LocalDate.of(1970, 1, 1));
        tenant2.setEmail("jane.doe@example.org");
        tenant2.setEmergencyContactPhoneNumber("6625550144");
        tenant2.setId(1L);
        tenant2.setName("Name");
        tenant2.setOccupation("Occupation");
        tenant2.setPhoneNumber("6625550144");
        tenant2.setStatus(TenantStatus.ACTIVE);

        Agreement agreement2 = new Agreement();
        agreement2.setCreatedDate(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
        agreement2.setEndDate(LocalDate.of(1970, 1, 1));
        agreement2.setId(1L);
        agreement2.setPaymentFrequency(PaymentFrequency.MONTHLY);
        agreement2.setRentAmount(10.0d);
        agreement2.setRentedUnitId(1L);
        agreement2.setSecurityDeposit(new BigDecimal("2.3"));
        agreement2.setStartDate(LocalDate.of(1970, 1, 1));
        agreement2.setStatus(AgreementStatus.ACTIVE);
        agreement2.setTenant(tenant2);
        Optional<Agreement> ofResult = Optional.of(agreement2);
        when(agreementRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);

        InvoiceDTO invoiceDTO = new InvoiceDTO();
        invoiceDTO.setBillingPeriodEnd(LocalDate.of(1970, 1, 1));
        invoiceDTO.setBillingPeriodStart(LocalDate.of(1970, 1, 1));
        invoiceDTO.setFrequency(InvoiceFrequency.MONTHLY);

        // Act
        Invoice actualCreateInvoiceResult = invoiceService.createInvoice(1L, invoiceDTO);

        // Assert
        verify(agreementRepository).findById(eq(1L));
        verify(invoiceRepository).save(isA(Invoice.class));
        assertSame(invoice, actualCreateInvoiceResult);
    }
}
