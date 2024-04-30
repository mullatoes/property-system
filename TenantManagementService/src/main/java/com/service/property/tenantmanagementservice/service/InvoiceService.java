package com.service.property.tenantmanagementservice.service;

import com.service.property.tenantmanagementservice.dto.InvoiceDTO;
import com.service.property.tenantmanagementservice.enums.InvoiceStatus;
import com.service.property.tenantmanagementservice.model.Agreement;
import com.service.property.tenantmanagementservice.model.Invoice;
import com.service.property.tenantmanagementservice.repository.AgreementRepository;
import com.service.property.tenantmanagementservice.repository.InvoiceRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InvoiceService {

    private final InvoiceRepository invoiceRepository;
    private final AgreementRepository agreementRepository;

    public InvoiceService(InvoiceRepository invoiceRepository, AgreementRepository agreementRepository) {
        this.invoiceRepository = invoiceRepository;
        this.agreementRepository = agreementRepository;
    }

    public List<Invoice> getInvoices() {
        return invoiceRepository.findAll();
    }

    public Invoice createInvoice(Long agreementId, InvoiceDTO invoiceDTO) {
        Agreement agreement = agreementRepository.findById(agreementId).get();
        Invoice invoice = new Invoice();
        invoice.setAgreement(agreement);
        invoice.setFrequency(invoiceDTO.getFrequency());
        invoice.setStatus(InvoiceStatus.PENDING);
        invoice.setBillingPeriodEnd(invoiceDTO.getBillingPeriodEnd());
        invoice.setBillingPeriodStart(invoiceDTO.getBillingPeriodStart());
        double rentAmount = agreement.getRentAmount();
        invoice.setAmountDue(rentAmount);
        return invoiceRepository.save(invoice);
    }
}
