package com.service.property.paymentsbillingservice.controller;

import com.service.property.paymentsbillingservice.dto.InvoiceDTO;
import com.service.property.paymentsbillingservice.model.Invoice;
import com.service.property.paymentsbillingservice.service.InvoiceService;
import com.service.property.paymentsbillingservice.utils.ResponseWrapper;
import com.service.property.paymentsbillingservice.utils.UnSuccessfulWrapper;
import org.springframework.http.HttpStatus;
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
    public ResponseEntity<ResponseWrapper<List<Invoice>>> getAllInvoices() {
        List<Invoice> invoices = invoiceService.getAllinvoices();

        if (invoices.isEmpty()) {
            return ResponseEntity.ok(new ResponseWrapper<>("success",
                    "No invoices available", 0, invoices));
        }

        return ResponseEntity.ok(new ResponseWrapper<>("success",
                "All invoices retrieved successfully", invoices.size(), invoices));

    }

    @PostMapping
    public ResponseEntity<Object> createInvoice(@RequestBody InvoiceDTO invoiceDTO) {
        Invoice invoice = invoiceService.createInvoice(invoiceDTO);
        if (invoice == null) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    new UnSuccessfulWrapper("failure",
                            "Failure... unable to create an invoice"));
        }

        return ResponseEntity.ok(new ResponseWrapper<>("success",
                "All invoices retrieved successfully", 1, invoice));

    }
}
