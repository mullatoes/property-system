package com.service.property.paymentsbillingservice.service;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.service.property.paymentsbillingservice.dto.Agreement;
import com.service.property.paymentsbillingservice.dto.InvoiceDTO;
import com.service.property.paymentsbillingservice.dto.Tenant;
import com.service.property.paymentsbillingservice.exceptions.CustomNotFoundException;
import com.service.property.paymentsbillingservice.feign.TenantInterface;
import com.service.property.paymentsbillingservice.model.Invoice;
import com.service.property.paymentsbillingservice.repository.InvoiceRepository;
import com.service.property.paymentsbillingservice.utils.InvoiceStatus;
import com.service.property.paymentsbillingservice.utils.ResponseWrapper;
import com.service.property.paymentsbillingservice.utils.enums.PaymentFrequency;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {InvoiceService.class})
@ExtendWith(SpringExtension.class)
@DisabledInAotMode
class InvoiceServiceTest {
    @MockBean
    private InvoiceRepository invoiceRepository;

    @Autowired
    private InvoiceService invoiceService;

    @MockBean
    private TenantInterface tenantInterface;

    @Test
    void testGetAllinvoices() {
        // Arrange
        ArrayList<Invoice> invoiceList = new ArrayList<>();
        when(invoiceRepository.findAll()).thenReturn(invoiceList);

        // Act
        List<Invoice> actualAllinvoices = invoiceService.getAllinvoices();

        // Assert
        verify(invoiceRepository).findAll();
        assertTrue(actualAllinvoices.isEmpty());
        assertSame(invoiceList, actualAllinvoices);
    }

    @Test
    void testGetAllinvoices2() {
        // Arrange
        when(invoiceRepository.findAll()).thenThrow(new CustomNotFoundException("An error occurred"));

        // Act and Assert
        assertThrows(CustomNotFoundException.class, () -> invoiceService.getAllinvoices());
        verify(invoiceRepository).findAll();
    }

    @Test
    void testCreateInvoice() {
        // Arrange
        ResponseEntity<ResponseWrapper<Tenant>> responseEntity = mock(ResponseEntity.class);
        when(responseEntity.getBody())
                .thenReturn(new ResponseWrapper<>("Status", "Not all who wander are lost", 3, new Tenant()));
        when(tenantInterface.getAgreement(Mockito.<Long>any())).thenThrow(new CustomNotFoundException("An error occurred"));
        when(tenantInterface.getTenantById(Mockito.<Long>any())).thenReturn(responseEntity);

        InvoiceDTO invoiceDTO = new InvoiceDTO();
        invoiceDTO.setEndDate(LocalDate.of(1970, 1, 1));
        invoiceDTO.setStartDate(LocalDate.of(1970, 1, 1));
        invoiceDTO.setTenantId(1L);

        // Act and Assert
        assertThrows(CustomNotFoundException.class, () -> invoiceService.createInvoice(invoiceDTO));
        verify(tenantInterface).getAgreement(eq(1L));
        verify(tenantInterface).getTenantById(eq(1L));
        verify(responseEntity).getBody();
    }

    @Test
    void testCreateInvoice2() {
        // Arrange
        Invoice invoice = new Invoice();
        invoice.setEndDate(LocalDate.of(1970, 1, 1));
        invoice.setFrequency(PaymentFrequency.MONTHLY);
        invoice.setId(1L);
        invoice.setStartDate(LocalDate.of(1970, 1, 1));
        invoice.setStatus(InvoiceStatus.PENDING);
        invoice.setTenantId(1L);
        invoice.setTotalAmount(new BigDecimal("2.3"));
        when(invoiceRepository.save(Mockito.<Invoice>any())).thenReturn(invoice);
        ResponseEntity<ResponseWrapper<Tenant>> responseEntity = mock(ResponseEntity.class);
        when(responseEntity.getBody())
                .thenReturn(new ResponseWrapper<>("Status", "Not all who wander are lost", 3, new Tenant()));
        ResponseEntity<ResponseWrapper<Agreement>> responseEntity2 = mock(ResponseEntity.class);
        when(responseEntity2.getBody())
                .thenReturn(new ResponseWrapper<>("Status", "Not all who wander are lost", 3, new Agreement()));
        when(tenantInterface.getAgreement(Mockito.<Long>any())).thenReturn(responseEntity2);
        when(tenantInterface.getTenantById(Mockito.<Long>any())).thenReturn(responseEntity);

        InvoiceDTO invoiceDTO = new InvoiceDTO();
        invoiceDTO.setEndDate(LocalDate.of(1970, 1, 1));
        invoiceDTO.setStartDate(LocalDate.of(1970, 1, 1));
        invoiceDTO.setTenantId(1L);

        // Act
        Invoice actualCreateInvoiceResult = invoiceService.createInvoice(invoiceDTO);

        // Assert
        verify(tenantInterface).getAgreement(eq(1L));
        verify(tenantInterface).getTenantById(eq(1L));
        verify(invoiceRepository).save(isA(Invoice.class));
        verify(responseEntity2).getBody();
        verify(responseEntity).getBody();
        assertSame(invoice, actualCreateInvoiceResult);
    }

    @Test
    void testCreateInvoice3() {
        // Arrange
        when(invoiceRepository.save(Mockito.<Invoice>any())).thenThrow(new CustomNotFoundException("An error occurred"));
        ResponseEntity<ResponseWrapper<Tenant>> responseEntity = mock(ResponseEntity.class);
        when(responseEntity.getBody())
                .thenReturn(new ResponseWrapper<>("Status", "Not all who wander are lost", 3, new Tenant()));
        ResponseEntity<ResponseWrapper<Agreement>> responseEntity2 = mock(ResponseEntity.class);
        when(responseEntity2.getBody())
                .thenReturn(new ResponseWrapper<>("Status", "Not all who wander are lost", 3, new Agreement()));
        when(tenantInterface.getAgreement(Mockito.<Long>any())).thenReturn(responseEntity2);
        when(tenantInterface.getTenantById(Mockito.<Long>any())).thenReturn(responseEntity);

        InvoiceDTO invoiceDTO = new InvoiceDTO();
        invoiceDTO.setEndDate(LocalDate.of(1970, 1, 1));
        invoiceDTO.setStartDate(LocalDate.of(1970, 1, 1));
        invoiceDTO.setTenantId(1L);

        // Act and Assert
        assertThrows(CustomNotFoundException.class, () -> invoiceService.createInvoice(invoiceDTO));
        verify(tenantInterface).getAgreement(eq(1L));
        verify(tenantInterface).getTenantById(eq(1L));
        verify(invoiceRepository).save(isA(Invoice.class));
        verify(responseEntity2).getBody();
        verify(responseEntity).getBody();
    }

    @Test
    void testCreateInvoice4() {
        // Arrange
        ResponseEntity<ResponseWrapper<Tenant>> responseEntity = mock(ResponseEntity.class);
        when(responseEntity.getBody())
                .thenReturn(new ResponseWrapper<>("Status", "Not all who wander are lost", 3, new Tenant()));
        ResponseEntity<ResponseWrapper<Agreement>> responseEntity2 = mock(ResponseEntity.class);
        when(responseEntity2.getBody()).thenReturn(new ResponseWrapper<>("Status", "Not all who wander are lost", 3, null));
        when(tenantInterface.getAgreement(Mockito.<Long>any())).thenReturn(responseEntity2);
        when(tenantInterface.getTenantById(Mockito.<Long>any())).thenReturn(responseEntity);

        InvoiceDTO invoiceDTO = new InvoiceDTO();
        invoiceDTO.setEndDate(LocalDate.of(1970, 1, 1));
        invoiceDTO.setStartDate(LocalDate.of(1970, 1, 1));
        invoiceDTO.setTenantId(1L);

        // Act and Assert
        assertThrows(CustomNotFoundException.class, () -> invoiceService.createInvoice(invoiceDTO));
        verify(tenantInterface).getAgreement(eq(1L));
        verify(tenantInterface).getTenantById(eq(1L));
        verify(responseEntity2).getBody();
        verify(responseEntity).getBody();
    }

    @Test
    void testCreateInvoice5() {
        // Arrange
        ResponseEntity<ResponseWrapper<Tenant>> responseEntity = mock(ResponseEntity.class);
        when(responseEntity.getBody())
                .thenReturn(new ResponseWrapper<>("Status", "Not all who wander are lost", 3, new Tenant()));
        Agreement agreement = mock(Agreement.class);
        when(agreement.getRentAmount()).thenThrow(new IllegalArgumentException("foo"));
        ResponseEntity<ResponseWrapper<Agreement>> responseEntity2 = mock(ResponseEntity.class);
        when(responseEntity2.getBody())
                .thenReturn(new ResponseWrapper<>("Status", "Not all who wander are lost", 3, agreement));
        when(tenantInterface.getAgreement(Mockito.<Long>any())).thenReturn(responseEntity2);
        when(tenantInterface.getTenantById(Mockito.<Long>any())).thenReturn(responseEntity);

        InvoiceDTO invoiceDTO = new InvoiceDTO();
        invoiceDTO.setEndDate(LocalDate.of(1970, 1, 1));
        invoiceDTO.setStartDate(LocalDate.of(1970, 1, 1));
        invoiceDTO.setTenantId(1L);

        // Act and Assert
        assertThrows(IllegalArgumentException.class, () -> invoiceService.createInvoice(invoiceDTO));
        verify(agreement).getRentAmount();
        verify(tenantInterface).getAgreement(eq(1L));
        verify(tenantInterface).getTenantById(eq(1L));
        verify(responseEntity2).getBody();
        verify(responseEntity).getBody();
    }

    @Test
    void testCreateInvoice6() {
        // Arrange
        ResponseEntity<ResponseWrapper<Tenant>> responseEntity = mock(ResponseEntity.class);
        when(responseEntity.getBody())
                .thenReturn(new ResponseWrapper<>("Status", "Not all who wander are lost", 3, new Tenant()));
        ResponseWrapper<Agreement> responseWrapper = mock(ResponseWrapper.class);
        when(responseWrapper.getData()).thenThrow(new CustomNotFoundException("An error occurred"));
        ResponseEntity<ResponseWrapper<Agreement>> responseEntity2 = mock(ResponseEntity.class);
        when(responseEntity2.getBody()).thenReturn(responseWrapper);
        when(tenantInterface.getAgreement(Mockito.<Long>any())).thenReturn(responseEntity2);
        when(tenantInterface.getTenantById(Mockito.<Long>any())).thenReturn(responseEntity);
        new IllegalArgumentException("foo");
        new IllegalArgumentException("foo");

        InvoiceDTO invoiceDTO = new InvoiceDTO();
        invoiceDTO.setEndDate(LocalDate.of(1970, 1, 1));
        invoiceDTO.setStartDate(LocalDate.of(1970, 1, 1));
        invoiceDTO.setTenantId(1L);

        // Act and Assert
        assertThrows(CustomNotFoundException.class, () -> invoiceService.createInvoice(invoiceDTO));
        verify(tenantInterface).getAgreement(eq(1L));
        verify(tenantInterface).getTenantById(eq(1L));
        verify(responseWrapper).getData();
        verify(responseEntity2).getBody();
        verify(responseEntity).getBody();
    }
}
