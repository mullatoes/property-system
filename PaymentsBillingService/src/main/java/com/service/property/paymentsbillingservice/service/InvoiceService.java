package com.service.property.paymentsbillingservice.service;

import com.service.property.paymentsbillingservice.dto.Agreement;
import com.service.property.paymentsbillingservice.dto.InvoiceDTO;
import com.service.property.paymentsbillingservice.dto.Tenant;
import com.service.property.paymentsbillingservice.exceptions.CustomNotFoundException;
import com.service.property.paymentsbillingservice.feign.TenantInterface;
import com.service.property.paymentsbillingservice.model.Invoice;
import com.service.property.paymentsbillingservice.repository.InvoiceRepository;
import com.service.property.paymentsbillingservice.utils.InvoiceStatus;
import com.service.property.paymentsbillingservice.utils.ResponseWrapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

@Service
public class InvoiceService {

    private final InvoiceRepository invoiceRepository;

    private final TenantInterface tenantInterface;

    public InvoiceService(InvoiceRepository invoiceRepository, TenantInterface tenantInterface) {
        this.invoiceRepository = invoiceRepository;
        this.tenantInterface = tenantInterface;
    }

    public List<Invoice> getAllinvoices() {
        return invoiceRepository.findAll();
    }

    public Invoice createInvoice(InvoiceDTO invoiceDTO) {

        //we need to call tenants service
        Tenant tenant = Objects.requireNonNull(tenantInterface.getTenantById(invoiceDTO.getTenantId()).getBody()).getData();

        if (tenant == null) {
            return null;
        }

        validateCreateInvoiceDTO(invoiceDTO);
        //I need an agreement.. get rent, get frequency
        Agreement agreement = getAgreementByTenantId(invoiceDTO.getTenantId());

        if (agreement == null) {
            throw new CustomNotFoundException("Agreement is null. Create one before proceeding");
        }

        Invoice invoice = new Invoice();
        invoice.setStartDate(invoiceDTO.getStartDate());
        invoice.setEndDate(invoiceDTO.getEndDate());
        invoice.setTotalAmount(BigDecimal.valueOf(agreement.getRentAmount()));
        invoice.setStatus(InvoiceStatus.PENDING);
        invoice.setTenantId(invoiceDTO.getTenantId());
        invoice.setFrequency(agreement.getPaymentFrequency());
        return invoiceRepository.save(invoice);

    }

    private Agreement getAgreementByTenantId(Long tenantId) {
        ResponseEntity<ResponseWrapper<Agreement>> response = tenantInterface.getAgreement(tenantId);
        return Objects.requireNonNull(response.getBody()).getData();
    }

    private void validateCreateInvoiceDTO(InvoiceDTO invoiceDTO) {
        if (invoiceDTO == null ||
                invoiceDTO.getStartDate() == null ||
                invoiceDTO.getEndDate() == null ||
                invoiceDTO.getTenantId() == null) {
            throw new IllegalArgumentException("Invalid input parameters for creating invoice");
        }
    }
}
