package com.service.property.paymentsbillingservice.feign;

import com.service.property.paymentsbillingservice.dto.Agreement;
import com.service.property.paymentsbillingservice.dto.Property;
import com.service.property.paymentsbillingservice.dto.Tenant;
import com.service.property.paymentsbillingservice.dto.Unit;
import com.service.property.paymentsbillingservice.utils.ResponseWrapper;
import java.util.List;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("PROPERTYMANAGEMENTSERVICE")
public interface PropertyInterface {

    @GetMapping("/api/property/{id}")
    ResponseEntity<ResponseWrapper<Property>> getPropertyById(@PathVariable Long id);

    @GetMapping("/api/property/{unitId}")
    ResponseEntity<ResponseWrapper<Unit>> getUnitById(@PathVariable Long unitId);

    @GetMapping("/api/unit/getPropertyUnits{unitId}")
    ResponseEntity<ResponseWrapper<List<Unit>> >getUnitsByPropertyId(@PathVariable Long propertyId);

}
