package com.service.property.paymentsbillingservice.service;

import com.service.property.paymentsbillingservice.dto.InvoiceDTO;
import com.service.property.paymentsbillingservice.model.Invoice;
import com.service.property.paymentsbillingservice.repository.InvoiceRepository;
import com.service.property.paymentsbillingservice.utils.InvoiceStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InvoiceService {

    private final InvoiceRepository invoiceRepository;

    public InvoiceService(InvoiceRepository invoiceRepository) {
        this.invoiceRepository = invoiceRepository;
    }

    public List<Invoice> getAllinvoices() {
        return invoiceRepository.findAll();
    }

    public Invoice createInvoice(InvoiceDTO invoiceDTO) {

        Long tenantId = invoiceDTO.getTenantId();//validate if tenant exists and is active

        //we need to call tenants service


        validateCreateInvoiceDTO(invoiceDTO);

        Invoice invoice = new Invoice();
        invoice.setStartDate(invoiceDTO.getStartDate());
        invoice.setEndDate(invoiceDTO.getEndDate());
        invoice.setTotalAmount(invoiceDTO.getTotalAmount());
        invoice.setStatus(InvoiceStatus.PENDING);
        invoice.setTenantId(tenantId);
        return invoiceRepository.save(invoice);
    }

    private void validateCreateInvoiceDTO(InvoiceDTO invoiceDTO) {
        if (invoiceDTO == null ||
                invoiceDTO.getStartDate() == null ||
                invoiceDTO.getEndDate() == null ||
                invoiceDTO.getTotalAmount() == null ||
                invoiceDTO.getTenantId() == null) {
            throw new IllegalArgumentException("Invalid input parameters for creating invoice");
        }
    }
}
