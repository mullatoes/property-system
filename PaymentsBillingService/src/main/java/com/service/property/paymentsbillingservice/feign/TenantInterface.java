package com.service.property.paymentsbillingservice.feign;

import com.service.property.paymentsbillingservice.dto.Agreement;
import com.service.property.paymentsbillingservice.dto.Tenant;
import com.service.property.paymentsbillingservice.utils.ResponseWrapper;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("TENANTMANAGEMENTSERVICE")
public interface TenantInterface {

    @GetMapping("/api/tenant/{id}")
    ResponseEntity<ResponseWrapper<Tenant>> getTenantById(@PathVariable Long id);

    @GetMapping("/api/agreement/{tenantId}")
    ResponseEntity<ResponseWrapper<Agreement>> getAgreement(@PathVariable Long tenantId);
    @GetMapping("/api/agreement/getAgreement/{tenantId}")
    ResponseEntity<ResponseWrapper<Agreement>> getAgreementWithUnitId(@PathVariable Long unitId);

    @GetMapping("/api/agreement/getAgreementById/{id}")
    ResponseEntity<ResponseWrapper<Agreement>> getAgreementById(@PathVariable Long id);


}
