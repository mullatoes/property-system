package com.service.property.tenantmanagementservice.controller;

import com.service.property.tenantmanagementservice.dto.AgreementDto;
import com.service.property.tenantmanagementservice.dto.InvoiceDTO;
import com.service.property.tenantmanagementservice.model.Agreement;
import com.service.property.tenantmanagementservice.model.Invoice;
import com.service.property.tenantmanagementservice.service.InvoiceService;
import com.service.property.tenantmanagementservice.utils.ResponseWrapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/invoice")
public class InvoiceController {

    private final InvoiceService invoiceService;

    public InvoiceController(InvoiceService invoiceService) {
        this.invoiceService = invoiceService;
    }

    @GetMapping
    public ResponseEntity<ResponseWrapper<List<Invoice>>> getInvoices() {
        List<Invoice> invoices = invoiceService.getInvoices();

        return ResponseEntity.ok(new ResponseWrapper<>("success",
                invoices.isEmpty() ? "Zero invoices retrieved" : "All invoices retrieved successfully", invoices.size(), invoices));
    }

    @PostMapping("/{agreementId}")
    public ResponseEntity<ResponseWrapper<Invoice>> createInvoice(@PathVariable Long agreementId,
                                                                  @RequestBody InvoiceDTO invoiceDTO) {
        Invoice invoice = invoiceService.createInvoice(agreementId, invoiceDTO);

        return ResponseEntity.ok(new ResponseWrapper<>("success",
                "Invoice created successfully", 1, invoice));
    }
}
