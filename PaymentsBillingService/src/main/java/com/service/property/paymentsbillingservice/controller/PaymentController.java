package com.service.property.paymentsbillingservice.controller;

import com.service.property.paymentsbillingservice.dto.InvoiceDTO;
import com.service.property.paymentsbillingservice.model.Invoice;
import com.service.property.paymentsbillingservice.model.Payment;
import com.service.property.paymentsbillingservice.service.InvoiceService;
import com.service.property.paymentsbillingservice.service.PaymentService;
import com.service.property.paymentsbillingservice.utils.ResponseWrapper;
import com.service.property.paymentsbillingservice.utils.UnSuccessfulWrapper;
import java.time.Month;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/payment")
public class PaymentController {

    private final PaymentService paymentService;

    private final InvoiceService invoiceService;


    public PaymentController(PaymentService paymentService, InvoiceService invoiceService) {
        this.paymentService = paymentService;
        this.invoiceService = invoiceService;
    }


    @GetMapping
    public ResponseEntity<ResponseWrapper<List<Invoice>>> getAllInvoices() {
        List<Invoice> invoices = invoiceService.getAllinvoices();

        if (invoices.isEmpty()) {
            return ResponseEntity.ok(new ResponseWrapper<>("success", "No invoices available", 0, invoices));
        }

        return ResponseEntity.ok(new ResponseWrapper<>("success", "All invoices retrieved successfully", invoices.size(), invoices));

    }

    @PostMapping("/createPayment/{id}")
    public ResponseEntity<Object> createPayment(@PathVariable Long id) {
        Payment payment = paymentService.createPayment(id);
        if (payment == null) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new UnSuccessfulWrapper("failure", "Failure... unable to make a payment"));
        }

        return ResponseEntity.ok(new ResponseWrapper<>("success", "Payment successfully done", 1, payment));

    }

    @PostMapping("/{unitId}")
    public ResponseEntity<Object> createInvoiceFromUnit(@PathVariable Long unitId, @PathVariable Month month, @PathVariable int year) {
        Invoice invoice = invoiceService.createInvoiceFromUnit(unitId, month, year);
        if (invoice == null) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new UnSuccessfulWrapper("failure", "Failure... unable to create an invoice"));
        }

        return ResponseEntity.ok(new ResponseWrapper<>("success", "All invoices retrieved successfully", 1, invoice));

    }

    @PostMapping("/{propertyId}")
    public ResponseEntity<Object> createInvoiceFromProperty(@PathVariable Long propertyId, @PathVariable Month month, @PathVariable int year, @RequestBody InvoiceDTO invoiceDTO) {
        Invoice invoice = invoiceService.createInvoiceFromProperty(propertyId, month, year);
        if (invoice == null) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new UnSuccessfulWrapper("failure", "Failure... unable to create an invoice"));
        }

        return ResponseEntity.ok(new ResponseWrapper<>("success", "All invoices retrieved successfully", 1, invoice));

    }


}
