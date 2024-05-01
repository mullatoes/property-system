package com.service.property.tenantmanagementservice.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.service.property.tenantmanagementservice.dto.AgreementDto;
import com.service.property.tenantmanagementservice.dto.UnitDto;
import com.service.property.tenantmanagementservice.enums.AgreementStatus;
import com.service.property.tenantmanagementservice.enums.PaymentFrequency;
import com.service.property.tenantmanagementservice.enums.TenantStatus;
import com.service.property.tenantmanagementservice.enums.UnitStatus;
import com.service.property.tenantmanagementservice.exceptions.UnitNotFoundException;
import com.service.property.tenantmanagementservice.exceptions.UnitOccupiedException;
import com.service.property.tenantmanagementservice.feign.UnitInterface;
import com.service.property.tenantmanagementservice.model.Agreement;
import com.service.property.tenantmanagementservice.model.Tenant;
import com.service.property.tenantmanagementservice.repository.AgreementRepository;
import com.service.property.tenantmanagementservice.repository.TenantRepository;
import com.service.property.tenantmanagementservice.utils.ResponseWrapper;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {AgreementService.class})
@ExtendWith(SpringExtension.class)
@DisabledInAotMode
class AgreementServiceTest {
    @MockBean
    private AgreementRepository agreementRepository;

    @Autowired
    private AgreementService agreementService;

    @MockBean
    private TenantRepository tenantRepository;

    @MockBean
    private UnitInterface unitInterface;


    @Test
    void testGetAllAgreements() {
        // Arrange
        ArrayList<Agreement> agreementList = new ArrayList<>();
        when(agreementRepository.findAll()).thenReturn(agreementList);

        // Act
        List<Agreement> actualAllAgreements = agreementService.getAllAgreements();

        // Assert
        verify(agreementRepository).findAll();
        assertTrue(actualAllAgreements.isEmpty());
        assertSame(agreementList, actualAllAgreements);
    }

    @Test
    void testGetAllAgreements2() {
        // Arrange
        when(agreementRepository.findAll()).thenThrow(new UnitOccupiedException("An error occurred"));

        // Act and Assert
        assertThrows(UnitOccupiedException.class, () -> agreementService.getAllAgreements());
        verify(agreementRepository).findAll();
    }

    @Test
    void testCreateAgreement() {
        // Arrange
        when(unitInterface.getUnitById(Mockito.<Long>any())).thenThrow(new UnitOccupiedException("An error occurred"));

        Tenant tenant = new Tenant();
        tenant.setAddress("42 Main St");
        tenant.setDateOfBirth(LocalDate.of(1970, 1, 1));
        tenant.setEmail("jane.doe@example.org");
        tenant.setEmergencyContactPhoneNumber("6625550144");
        tenant.setId(1L);
        tenant.setName("Name");
        tenant.setOccupation("Occupation");
        tenant.setPhoneNumber("6625550144");
        tenant.setStatus(TenantStatus.ACTIVE);
        Optional<Tenant> ofResult = Optional.of(tenant);
        when(tenantRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);

        AgreementDto agreementDto = new AgreementDto();
        agreementDto.setEndDate(LocalDate.of(1970, 1, 1));
        agreementDto.setPaymentFrequency(PaymentFrequency.MONTHLY);
        agreementDto.setStartDate(LocalDate.of(1970, 1, 1));
        agreementDto.setUnitId(1L);

        // Act and Assert
        assertThrows(UnitNotFoundException.class, () -> agreementService.createAgreement(1L, agreementDto));
        verify(unitInterface).getUnitById(eq(1L));
        verify(tenantRepository).findById(eq(1L));
    }

    /**
     * Method under test:
     * {@link AgreementService#createAgreement(Long, AgreementDto)}
     */
    @Test
    void testCreateAgreement2() {
        // Arrange
        when(unitInterface.getUnitById(Mockito.<Long>any())).thenReturn(null);

        Tenant tenant = new Tenant();
        tenant.setAddress("42 Main St");
        tenant.setDateOfBirth(LocalDate.of(1970, 1, 1));
        tenant.setEmail("jane.doe@example.org");
        tenant.setEmergencyContactPhoneNumber("6625550144");
        tenant.setId(1L);
        tenant.setName("Name");
        tenant.setOccupation("Occupation");
        tenant.setPhoneNumber("6625550144");
        tenant.setStatus(TenantStatus.ACTIVE);
        Optional<Tenant> ofResult = Optional.of(tenant);
        when(tenantRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);

        AgreementDto agreementDto = new AgreementDto();
        agreementDto.setEndDate(LocalDate.of(1970, 1, 1));
        agreementDto.setPaymentFrequency(PaymentFrequency.MONTHLY);
        agreementDto.setStartDate(LocalDate.of(1970, 1, 1));
        agreementDto.setUnitId(1L);

        // Act and Assert
        assertThrows(UnitNotFoundException.class, () -> agreementService.createAgreement(1L, agreementDto));
        verify(unitInterface).getUnitById(eq(1L));
        verify(tenantRepository).findById(eq(1L));
    }

    /**
     * Method under test:
     * {@link AgreementService#createAgreement(Long, AgreementDto)}
     */
    @Test
    void testCreateAgreement3() {
        // Arrange
        ResponseEntity<ResponseWrapper<UnitDto>> responseEntity = mock(ResponseEntity.class);
        when(responseEntity.getBody())
                .thenReturn(new ResponseWrapper<>("Status", "Not all who wander are lost", 3, new UnitDto()));
        when(unitInterface.getUnitById(Mockito.<Long>any())).thenReturn(responseEntity);

        Tenant tenant = new Tenant();
        tenant.setAddress("42 Main St");
        tenant.setDateOfBirth(LocalDate.of(1970, 1, 1));
        tenant.setEmail("jane.doe@example.org");
        tenant.setEmergencyContactPhoneNumber("6625550144");
        tenant.setId(1L);
        tenant.setName("Name");
        tenant.setOccupation("Occupation");
        tenant.setPhoneNumber("6625550144");
        tenant.setStatus(TenantStatus.ACTIVE);
        Optional<Tenant> ofResult = Optional.of(tenant);
        when(tenantRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);

        AgreementDto agreementDto = new AgreementDto();
        agreementDto.setEndDate(LocalDate.of(1970, 1, 1));
        agreementDto.setPaymentFrequency(PaymentFrequency.MONTHLY);
        agreementDto.setStartDate(LocalDate.of(1970, 1, 1));
        agreementDto.setUnitId(1L);

        // Act and Assert
        assertThrows(UnitNotFoundException.class, () -> agreementService.createAgreement(1L, agreementDto));
        verify(unitInterface).getUnitById(eq(1L));
        verify(tenantRepository).findById(eq(1L));
        verify(responseEntity).getBody();
    }

    /**
     * Method under test:
     * {@link AgreementService#createAgreement(Long, AgreementDto)}
     */
    @Test
    void testCreateAgreement4() {
        // Arrange
        ResponseEntity<ResponseWrapper<UnitDto>> responseEntity = mock(ResponseEntity.class);
        when(responseEntity.getBody()).thenReturn(new ResponseWrapper<>("Status", "Not all who wander are lost", 3, null));
        when(unitInterface.getUnitById(Mockito.<Long>any())).thenReturn(responseEntity);

        Tenant tenant = new Tenant();
        tenant.setAddress("42 Main St");
        tenant.setDateOfBirth(LocalDate.of(1970, 1, 1));
        tenant.setEmail("jane.doe@example.org");
        tenant.setEmergencyContactPhoneNumber("6625550144");
        tenant.setId(1L);
        tenant.setName("Name");
        tenant.setOccupation("Occupation");
        tenant.setPhoneNumber("6625550144");
        tenant.setStatus(TenantStatus.ACTIVE);
        Optional<Tenant> ofResult = Optional.of(tenant);
        when(tenantRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);

        AgreementDto agreementDto = new AgreementDto();
        agreementDto.setEndDate(LocalDate.of(1970, 1, 1));
        agreementDto.setPaymentFrequency(PaymentFrequency.MONTHLY);
        agreementDto.setStartDate(LocalDate.of(1970, 1, 1));
        agreementDto.setUnitId(1L);

        // Act and Assert
        assertThrows(UnitNotFoundException.class, () -> agreementService.createAgreement(1L, agreementDto));
        verify(unitInterface).getUnitById(eq(1L));
        verify(tenantRepository).findById(eq(1L));
        verify(responseEntity).getBody();
    }

    /**
     * Method under test:
     * {@link AgreementService#createAgreement(Long, AgreementDto)}
     */
    @Test
    void testCreateAgreement5() {
        // Arrange
        UnitDto unitDto = mock(UnitDto.class);
        when(unitDto.getUnitStatus()).thenReturn(UnitStatus.OCCUPIED);
        ResponseEntity<ResponseWrapper<UnitDto>> responseEntity = mock(ResponseEntity.class);
        when(responseEntity.getBody())
                .thenReturn(new ResponseWrapper<>("Status", "Not all who wander are lost", 3, unitDto));
        when(unitInterface.getUnitById(Mockito.<Long>any())).thenReturn(responseEntity);

        Tenant tenant = new Tenant();
        tenant.setAddress("42 Main St");
        tenant.setDateOfBirth(LocalDate.of(1970, 1, 1));
        tenant.setEmail("jane.doe@example.org");
        tenant.setEmergencyContactPhoneNumber("6625550144");
        tenant.setId(1L);
        tenant.setName("Name");
        tenant.setOccupation("Occupation");
        tenant.setPhoneNumber("6625550144");
        tenant.setStatus(TenantStatus.ACTIVE);
        Optional<Tenant> ofResult = Optional.of(tenant);
        when(tenantRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);

        AgreementDto agreementDto = new AgreementDto();
        agreementDto.setEndDate(LocalDate.of(1970, 1, 1));
        agreementDto.setPaymentFrequency(PaymentFrequency.MONTHLY);
        agreementDto.setStartDate(LocalDate.of(1970, 1, 1));
        agreementDto.setUnitId(1L);

        // Act and Assert
        assertThrows(UnitNotFoundException.class, () -> agreementService.createAgreement(1L, agreementDto));
        verify(unitDto).getUnitStatus();
        verify(unitInterface).getUnitById(eq(1L));
        verify(tenantRepository).findById(eq(1L));
        verify(responseEntity).getBody();
    }

    /**
     * Method under test:
     * {@link AgreementService#createAgreement(Long, AgreementDto)}
     */
    @Test
    void testCreateAgreement6() {
        // Arrange
        UnitDto unitDto = mock(UnitDto.class);
        when(unitDto.getRentPerSqFt()).thenReturn(10.0d);
        when(unitDto.getSquareFt()).thenReturn(10.0d);
        when(unitDto.getId()).thenReturn(1L);
        when(unitDto.getUnitNumber()).thenReturn("42");
        when(unitDto.getUnitStatus()).thenReturn(UnitStatus.VACANT);
        ResponseEntity<ResponseWrapper<UnitDto>> responseEntity = mock(ResponseEntity.class);
        when(responseEntity.getBody())
                .thenReturn(new ResponseWrapper<>("Status", "Not all who wander are lost", 3, unitDto));
        when(unitInterface.updateUnitStatus(Mockito.<Long>any())).thenReturn(null);
        when(unitInterface.getUnitById(Mockito.<Long>any())).thenReturn(responseEntity);

        Tenant tenant = new Tenant();
        tenant.setAddress("42 Main St");
        tenant.setDateOfBirth(LocalDate.of(1970, 1, 1));
        tenant.setEmail("jane.doe@example.org");
        tenant.setEmergencyContactPhoneNumber("6625550144");
        tenant.setId(1L);
        tenant.setName("Name");
        tenant.setOccupation("Occupation");
        tenant.setPhoneNumber("6625550144");
        tenant.setStatus(TenantStatus.ACTIVE);

        Agreement agreement = new Agreement();
        agreement.setCreatedDate(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
        agreement.setEndDate(LocalDate.of(1970, 1, 1));
        agreement.setId(1L);
        agreement.setPaymentFrequency(PaymentFrequency.MONTHLY);
        agreement.setRentAmount(10.0d);
        agreement.setRentedUnitId(1L);
        agreement.setSecurityDeposit(new BigDecimal("2.3"));
        agreement.setStartDate(LocalDate.of(1970, 1, 1));
        agreement.setStatus(AgreementStatus.ACTIVE);
        agreement.setTenant(tenant);
        when(agreementRepository.save(Mockito.<Agreement>any())).thenReturn(agreement);

        Tenant tenant2 = new Tenant();
        tenant2.setAddress("42 Main St");
        tenant2.setDateOfBirth(LocalDate.of(1970, 1, 1));
        tenant2.setEmail("jane.doe@example.org");
        tenant2.setEmergencyContactPhoneNumber("6625550144");
        tenant2.setId(1L);
        tenant2.setName("Name");
        tenant2.setOccupation("Occupation");
        tenant2.setPhoneNumber("6625550144");
        tenant2.setStatus(TenantStatus.ACTIVE);
        Optional<Tenant> ofResult = Optional.of(tenant2);
        when(tenantRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);

        AgreementDto agreementDto = new AgreementDto();
        agreementDto.setEndDate(LocalDate.of(1970, 1, 1));
        agreementDto.setPaymentFrequency(PaymentFrequency.MONTHLY);
        agreementDto.setStartDate(LocalDate.of(1970, 1, 1));
        agreementDto.setUnitId(1L);

        // Act
        Agreement actualCreateAgreementResult = agreementService.createAgreement(1L, agreementDto);

        // Assert
        verify(unitDto).getId();
        verify(unitDto).getRentPerSqFt();
        verify(unitDto).getSquareFt();
        verify(unitDto).getUnitNumber();
        verify(unitDto).getUnitStatus();
        verify(unitInterface).getUnitById(eq(1L));
        verify(unitInterface).updateUnitStatus(eq(1L));
        verify(tenantRepository).findById(eq(1L));
        verify(agreementRepository).save(isA(Agreement.class));
        verify(responseEntity).getBody();
        assertEquals("1970-01-01", actualCreateAgreementResult.getEndDate().toString());
        assertEquals("1970-01-01", actualCreateAgreementResult.getStartDate().toString());
        assertEquals(100.0d, actualCreateAgreementResult.getRentAmount());
        assertEquals(1L, actualCreateAgreementResult.getRentedUnitId().longValue());
        assertEquals(AgreementStatus.ACTIVE, actualCreateAgreementResult.getStatus());
        assertEquals(PaymentFrequency.MONTHLY, actualCreateAgreementResult.getPaymentFrequency());
        assertEquals(tenant, actualCreateAgreementResult.getTenant());
        BigDecimal expectedSecurityDeposit = new BigDecimal("300.0");
        assertEquals(expectedSecurityDeposit, actualCreateAgreementResult.getSecurityDeposit());
    }

    /**
     * Method under test:
     * {@link AgreementService#createAgreement(Long, AgreementDto)}
     */
    @Test
    void testCreateAgreement7() {
        // Arrange
        UnitDto unitDto = mock(UnitDto.class);
        when(unitDto.getRentPerSqFt()).thenReturn(10.0d);
        when(unitDto.getSquareFt()).thenReturn(10.0d);
        when(unitDto.getId()).thenReturn(1L);
        when(unitDto.getUnitNumber()).thenReturn("42");
        when(unitDto.getUnitStatus()).thenReturn(UnitStatus.VACANT);
        ResponseEntity<ResponseWrapper<UnitDto>> responseEntity = mock(ResponseEntity.class);
        when(responseEntity.getBody())
                .thenReturn(new ResponseWrapper<>("Status", "Not all who wander are lost", 3, unitDto));
        when(unitInterface.updateUnitStatus(Mockito.<Long>any())).thenReturn(null);
        when(unitInterface.getUnitById(Mockito.<Long>any())).thenReturn(responseEntity);
        when(agreementRepository.save(Mockito.<Agreement>any())).thenThrow(new UnitOccupiedException("An error occurred"));

        Tenant tenant = new Tenant();
        tenant.setAddress("42 Main St");
        tenant.setDateOfBirth(LocalDate.of(1970, 1, 1));
        tenant.setEmail("jane.doe@example.org");
        tenant.setEmergencyContactPhoneNumber("6625550144");
        tenant.setId(1L);
        tenant.setName("Name");
        tenant.setOccupation("Occupation");
        tenant.setPhoneNumber("6625550144");
        tenant.setStatus(TenantStatus.ACTIVE);
        Optional<Tenant> ofResult = Optional.of(tenant);
        when(tenantRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);

        AgreementDto agreementDto = new AgreementDto();
        agreementDto.setEndDate(LocalDate.of(1970, 1, 1));
        agreementDto.setPaymentFrequency(PaymentFrequency.MONTHLY);
        agreementDto.setStartDate(LocalDate.of(1970, 1, 1));
        agreementDto.setUnitId(1L);

        // Act and Assert
        assertThrows(UnitNotFoundException.class, () -> agreementService.createAgreement(1L, agreementDto));
        verify(unitDto).getId();
        verify(unitDto).getRentPerSqFt();
        verify(unitDto).getSquareFt();
        verify(unitDto).getUnitNumber();
        verify(unitDto).getUnitStatus();
        verify(unitInterface).getUnitById(eq(1L));
        verify(unitInterface).updateUnitStatus(eq(1L));
        verify(tenantRepository).findById(eq(1L));
        verify(agreementRepository).save(isA(Agreement.class));
        verify(responseEntity).getBody();
    }

    /**
     * Method under test:
     * {@link AgreementService#createAgreement(Long, AgreementDto)}
     */
    @Test
    void testCreateAgreement8() {
        // Arrange
        UnitDto unitDto = mock(UnitDto.class);
        when(unitDto.getRentPerSqFt()).thenReturn(Double.NaN);
        when(unitDto.getSquareFt()).thenReturn(10.0d);
        when(unitDto.getUnitNumber()).thenReturn("42");
        when(unitDto.getUnitStatus()).thenReturn(UnitStatus.VACANT);
        ResponseEntity<ResponseWrapper<UnitDto>> responseEntity = mock(ResponseEntity.class);
        when(responseEntity.getBody())
                .thenReturn(new ResponseWrapper<>("Status", "Not all who wander are lost", 3, unitDto));
        when(unitInterface.updateUnitStatus(Mockito.<Long>any())).thenReturn(null);
        when(unitInterface.getUnitById(Mockito.<Long>any())).thenReturn(responseEntity);

        Tenant tenant = new Tenant();
        tenant.setAddress("42 Main St");
        tenant.setDateOfBirth(LocalDate.of(1970, 1, 1));
        tenant.setEmail("jane.doe@example.org");
        tenant.setEmergencyContactPhoneNumber("6625550144");
        tenant.setId(1L);
        tenant.setName("Name");
        tenant.setOccupation("Occupation");
        tenant.setPhoneNumber("6625550144");
        tenant.setStatus(TenantStatus.ACTIVE);
        Optional<Tenant> ofResult = Optional.of(tenant);
        when(tenantRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);

        AgreementDto agreementDto = new AgreementDto();
        agreementDto.setEndDate(LocalDate.of(1970, 1, 1));
        agreementDto.setPaymentFrequency(PaymentFrequency.MONTHLY);
        agreementDto.setStartDate(LocalDate.of(1970, 1, 1));
        agreementDto.setUnitId(1L);

        // Act and Assert
        assertThrows(UnitNotFoundException.class, () -> agreementService.createAgreement(1L, agreementDto));
        verify(unitDto).getRentPerSqFt();
        verify(unitDto).getSquareFt();
        verify(unitDto).getUnitNumber();
        verify(unitDto).getUnitStatus();
        verify(unitInterface).getUnitById(eq(1L));
        verify(unitInterface).updateUnitStatus(eq(1L));
        verify(tenantRepository).findById(eq(1L));
        verify(responseEntity).getBody();
    }

    /**
     * Method under test:
     * {@link AgreementService#createAgreement(Long, AgreementDto)}
     */
    @Test
    void testCreateAgreement9() {
        // Arrange
        ResponseEntity<ResponseWrapper<UnitDto>> responseEntity = mock(ResponseEntity.class);
        when(responseEntity.getBody()).thenReturn(null);
        when(unitInterface.getUnitById(Mockito.<Long>any())).thenReturn(responseEntity);

        Tenant tenant = new Tenant();
        tenant.setAddress("42 Main St");
        tenant.setDateOfBirth(LocalDate.of(1970, 1, 1));
        tenant.setEmail("jane.doe@example.org");
        tenant.setEmergencyContactPhoneNumber("6625550144");
        tenant.setId(1L);
        tenant.setName("Name");
        tenant.setOccupation("Occupation");
        tenant.setPhoneNumber("6625550144");
        tenant.setStatus(TenantStatus.ACTIVE);
        Optional<Tenant> ofResult = Optional.of(tenant);
        when(tenantRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);

        AgreementDto agreementDto = new AgreementDto();
        agreementDto.setEndDate(LocalDate.of(1970, 1, 1));
        agreementDto.setPaymentFrequency(PaymentFrequency.MONTHLY);
        agreementDto.setStartDate(LocalDate.of(1970, 1, 1));
        agreementDto.setUnitId(1L);

        // Act and Assert
        assertThrows(UnitNotFoundException.class, () -> agreementService.createAgreement(1L, agreementDto));
        verify(unitInterface).getUnitById(eq(1L));
        verify(tenantRepository).findById(eq(1L));
        verify(responseEntity).getBody();
    }

    /**
     * Method under test:
     * {@link AgreementService#createAgreement(Long, AgreementDto)}
     */
    @Test
    void testCreateAgreement10() {
        // Arrange
        ResponseWrapper<UnitDto> responseWrapper = mock(ResponseWrapper.class);
        when(responseWrapper.getData()).thenThrow(new UnitOccupiedException("An error occurred"));
        ResponseEntity<ResponseWrapper<UnitDto>> responseEntity = mock(ResponseEntity.class);
        when(responseEntity.getBody()).thenReturn(responseWrapper);
        when(unitInterface.getUnitById(Mockito.<Long>any())).thenReturn(responseEntity);

        Tenant tenant = new Tenant();
        tenant.setAddress("42 Main St");
        tenant.setDateOfBirth(LocalDate.of(1970, 1, 1));
        tenant.setEmail("jane.doe@example.org");
        tenant.setEmergencyContactPhoneNumber("6625550144");
        tenant.setId(1L);
        tenant.setName("Name");
        tenant.setOccupation("Occupation");
        tenant.setPhoneNumber("6625550144");
        tenant.setStatus(TenantStatus.ACTIVE);
        Optional<Tenant> ofResult = Optional.of(tenant);
        when(tenantRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);

        AgreementDto agreementDto = new AgreementDto();
        agreementDto.setEndDate(LocalDate.of(1970, 1, 1));
        agreementDto.setPaymentFrequency(PaymentFrequency.MONTHLY);
        agreementDto.setStartDate(LocalDate.of(1970, 1, 1));
        agreementDto.setUnitId(1L);

        // Act and Assert
        assertThrows(UnitNotFoundException.class, () -> agreementService.createAgreement(1L, agreementDto));
        verify(unitInterface).getUnitById(eq(1L));
        verify(responseWrapper).getData();
        verify(tenantRepository).findById(eq(1L));
        verify(responseEntity).getBody();
    }

    /**
     * Method under test: {@link AgreementService#getAgreement(Long)}
     */
    @Test
    void testGetAgreement() {
        // Arrange
        when(agreementRepository.findAgreementByTenantId(Mockito.<Long>any())).thenReturn(new ArrayList<>());

        // Act
        Agreement actualAgreement = agreementService.getAgreement(1L);

        // Assert
        verify(agreementRepository).findAgreementByTenantId(eq(1L));
        assertNull(actualAgreement);
    }

    /**
     * Method under test: {@link AgreementService#getAgreement(Long)}
     */
    @Test
    void testGetAgreement2() {
        // Arrange
        Tenant tenant = new Tenant();
        tenant.setAddress("42 Main St");
        tenant.setDateOfBirth(LocalDate.of(1970, 1, 1));
        tenant.setEmail("jane.doe@example.org");
        tenant.setEmergencyContactPhoneNumber("6625550144");
        tenant.setId(1L);
        tenant.setName("Name");
        tenant.setOccupation("Occupation");
        tenant.setPhoneNumber("6625550144");
        tenant.setStatus(TenantStatus.ACTIVE);

        Agreement agreement = new Agreement();
        agreement.setCreatedDate(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
        agreement.setEndDate(LocalDate.of(1970, 1, 1));
        agreement.setId(1L);
        agreement.setPaymentFrequency(PaymentFrequency.MONTHLY);
        agreement.setRentAmount(10.0d);
        agreement.setRentedUnitId(1L);
        agreement.setSecurityDeposit(new BigDecimal("2.3"));
        agreement.setStartDate(LocalDate.of(1970, 1, 1));
        agreement.setStatus(AgreementStatus.ACTIVE);
        agreement.setTenant(tenant);

        ArrayList<Agreement> agreementList = new ArrayList<>();
        agreementList.add(agreement);
        when(agreementRepository.findAgreementByTenantId(Mockito.<Long>any())).thenReturn(agreementList);

        // Act
        Agreement actualAgreement = agreementService.getAgreement(1L);

        // Assert
        verify(agreementRepository).findAgreementByTenantId(eq(1L));
        assertSame(agreement, actualAgreement);
    }

    /**
     * Method under test: {@link AgreementService#getAgreement(Long)}
     */
    @Test
    void testGetAgreement3() {
        // Arrange
        when(agreementRepository.findAgreementByTenantId(Mockito.<Long>any()))
                .thenThrow(new UnitOccupiedException("An error occurred"));

        // Act and Assert
        assertThrows(UnitOccupiedException.class, () -> agreementService.getAgreement(1L));
        verify(agreementRepository).findAgreementByTenantId(eq(1L));
    }
}
