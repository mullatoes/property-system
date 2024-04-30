package com.service.property.paymentsbillingservice.service;

import com.service.property.paymentsbillingservice.model.Invoice;
import com.service.property.paymentsbillingservice.repository.InvoiceRepository;
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
}
