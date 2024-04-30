package com.service.property.tenantmanagementservice.feign;

import com.service.property.tenantmanagementservice.dto.UnitDto;
import com.service.property.tenantmanagementservice.utils.ResponseWrapper;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

@FeignClient("PROPERTYMANAGEMENTSERVICE")
public interface UnitInterface {

    @GetMapping("/api/unit/{unitId}")
    ResponseEntity<ResponseWrapper<UnitDto>> getUnitById(@PathVariable Long unitId);

    @PutMapping("/api/unit/status/update/{unitId}")
    ResponseEntity<ResponseWrapper<UnitDto>> updateUnitStatus(@PathVariable Long unitId);

}
