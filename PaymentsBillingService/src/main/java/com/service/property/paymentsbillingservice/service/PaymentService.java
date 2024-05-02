package com.service.property.paymentsbillingservice.service;


import com.service.property.paymentsbillingservice.model.Invoice;
import com.service.property.paymentsbillingservice.model.Payment;
import com.service.property.paymentsbillingservice.repository.InvoiceRepository;
import com.service.property.paymentsbillingservice.repository.PaymentRepository;
import com.service.property.paymentsbillingservice.utils.InvoiceStatus;
import com.service.property.paymentsbillingservice.utils.PaymentMethod;
import com.service.property.paymentsbillingservice.utils.PaymentStatus;
import com.service.property.paymentsbillingservice.utils.ResponseWrapper;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Service
public class PaymentService {

    private final InvoiceRepository invoiceRepository;

    private final PaymentRepository paymentRepository;

    public PaymentService(InvoiceRepository invoiceRepository, PaymentRepository paymentRepository) {
        this.invoiceRepository = invoiceRepository;
        this.paymentRepository = paymentRepository;
    }

    public List<Invoice> getAllinvoices() {
        return invoiceRepository.findAll();
    }

    public List<Payment> getAllPayments() {
        return paymentRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseWrapper<Payment>> getPaymentById(@PathVariable Long id) {
        Payment payment = paymentRepository.findById(id);

        return ResponseEntity.ok(new ResponseWrapper<>("success", "Payment " + payment.getId() + " retrieved successfully", 1, payment));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseWrapper<Invoice>> getInvoiceById(@PathVariable Long id) {
        Invoice invoice = invoiceRepository.findById(id);

        return ResponseEntity.ok(new ResponseWrapper<>("success", "Invoice " + invoice.getId() + " retrieved successfully", 1, invoice));
    }

    public Payment createPayment(Long id) {
        System.out.println("The id is :::::::::"+id);
        Invoice invoice = invoiceRepository.findById(id);
        System.out.println("Got invoice :::::::::"+invoice.getId());

        if (invoice == null || invoice.getTotalAmount() == null || invoice.getMonth() == null || invoice.getYear() == null || invoice.getTenantId() == null) {
            throw new IllegalArgumentException("Invalid input parameters for creating invoice");
        }

        Payment payment = new Payment();

        payment.setPaymentDate(LocalDate.now());
        payment.setPaymentMethod(PaymentMethod.CHEQUE);
        payment.setStatus(PaymentStatus.SUCCESS);
        payment.setAmount(BigDecimal.ZERO);
        payment.setInvoice(invoice);

        invoice.setStatus(InvoiceStatus.PAID);
        payment = paymentRepository.save(payment);
        return payment;


    }

}
