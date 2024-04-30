package com.service.property.tenantmanagementservice.controller;

import com.service.property.tenantmanagementservice.dto.AgreementDto;
import com.service.property.tenantmanagementservice.model.Agreement;
import com.service.property.tenantmanagementservice.service.AgreementService;
import com.service.property.tenantmanagementservice.utils.ResponseWrapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/agreement")
public class AgreementController {

    private final AgreementService agreementService;

    public AgreementController(AgreementService agreementService) {
        this.agreementService = agreementService;
    }

    @GetMapping
    public ResponseEntity<ResponseWrapper<List<Agreement>>> getAllAgreements() {
        List<Agreement> allAgreements = agreementService.getAllAgreements();

        return ResponseEntity.ok(new ResponseWrapper<>("success",
                "All agreements retrieved successfully", allAgreements.size(), allAgreements));
    }

    @PostMapping("/{tenantId}")
    public ResponseEntity<ResponseWrapper<Agreement>> createAgreement(@PathVariable Long tenantId, @RequestBody AgreementDto agreementDto) {
        Agreement agreement = agreementService.createAgreement(tenantId, agreementDto);

        return ResponseEntity.ok(new ResponseWrapper<>("success",
                "Agreement created successfully", 1, agreement));
    }

    @GetMapping("/{tenantId}")
    public ResponseEntity<ResponseWrapper<Agreement>> getAgreement(@PathVariable Long tenantId) {
        Agreement agreement = agreementService.getAgreement(tenantId);

        return ResponseEntity.ok(
                new ResponseWrapper<>("success", "Agreement retrieved successful", 1, agreement)
        );
    }
}
