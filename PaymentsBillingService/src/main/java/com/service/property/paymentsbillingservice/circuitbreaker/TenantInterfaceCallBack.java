package com.service.property.paymentsbillingservice.circuitbreaker;

import com.service.property.paymentsbillingservice.dto.Agreement;
import com.service.property.paymentsbillingservice.dto.Tenant;
import com.service.property.paymentsbillingservice.feign.TenantInterface;
import com.service.property.paymentsbillingservice.utils.ResponseWrapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class TenantInterfaceCallBack implements TenantInterface {
    @Override
    public ResponseEntity<ResponseWrapper<Tenant>> getTenantById(Long id) {
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(new ResponseWrapper<>("failure",
                "Failure... unable to reach the service"));

    }

    @Override
    public ResponseEntity<ResponseWrapper<Agreement>> getAgreement(Long tenantId) {
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(new ResponseWrapper<>("failure",
                "Failure... unable to reach the service"));
    }

    @Override
    public ResponseEntity<ResponseWrapper<Agreement>> getAgreementWithUnitId(Long unitId) {
        return null;
    }

    @Override
    public ResponseEntity<ResponseWrapper<Agreement>> getAgreementById(Long id) {
        return null;
    }
}
