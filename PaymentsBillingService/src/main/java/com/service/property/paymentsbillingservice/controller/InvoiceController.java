package com.service.property.paymentsbillingservice.controller;

import com.service.property.paymentsbillingservice.model.Invoice;
import com.service.property.paymentsbillingservice.service.InvoiceService;
import com.service.property.paymentsbillingservice.utils.ResponseWrapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/invoice")
public class InvoiceController {

    private final InvoiceService invoiceService;

    public InvoiceController(InvoiceService invoiceService) {
        this.invoiceService = invoiceService;
    }

    @GetMapping
    public ResponseEntity<ResponseWrapper<List<Invoice>>> getAllInvoices() {
        List<Invoice> invoices = invoiceService.getAllinvoices();

        if (invoices.isEmpty()) {
           return ResponseEntity.ok(new ResponseWrapper<>("success",
                    "No invoices available", 0, invoices));
        }

        return ResponseEntity.ok(new ResponseWrapper<>("success",
                "All invoices retrieved successfully", invoices.size(), invoices));

    }
}
