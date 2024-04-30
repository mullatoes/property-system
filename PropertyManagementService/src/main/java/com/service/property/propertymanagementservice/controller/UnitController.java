package com.service.property.propertymanagementservice.controller;

import com.service.property.propertymanagementservice.dto.UnitDto;
import com.service.property.propertymanagementservice.model.Unit;
import com.service.property.propertymanagementservice.service.UnitService;
import com.service.property.propertymanagementservice.utils.ResponseWrapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/unit")
public class UnitController {

    private final UnitService unitService;

    public UnitController(UnitService unitService) {
        this.unitService = unitService;
    }

    @GetMapping
    public ResponseEntity<ResponseWrapper<List<Unit>>> getAllUnits() {
        List<Unit> allUnits = unitService.getAllUnits();

        if (allUnits.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(new ResponseWrapper<>("success",
                "All units retrieved successfully", allUnits.size(), allUnits));
    }

    @PostMapping("/{floorId}")
    public ResponseEntity<ResponseWrapper<Unit>> addUnit(@PathVariable Long floorId, @RequestBody UnitDto unitDto) {
        Unit unit = unitService.addUnit(floorId, unitDto);
        if (unit == null) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(new ResponseWrapper<>("success",
                unit.getUnitNumber() + " added successful", 1, unit));
    }

    @GetMapping("/{unitId}")
    public ResponseEntity<ResponseWrapper<Unit>> getUnitById(@PathVariable Long unitId) {
        System.out.println("Call getUnitById..... ");
        Unit unit = unitService.getUnitById(unitId);
        return ResponseEntity.ok(new ResponseWrapper<>("success",
                unit.getUnitNumber() + " unit retrieved successful", 1, unit));
    }

    @PutMapping("/{unitId}")
    public ResponseEntity<ResponseWrapper<Unit>> updateUnit(@PathVariable Long unitId, @RequestBody UnitDto unitDto) {
        Unit unit = unitService.updateUnit(unitId, unitDto);
        return ResponseEntity.ok(new ResponseWrapper<>("success",
                unit.getUnitNumber() + " unit updated successful", 1, unit));
    }

    @PutMapping("/status/update/{unitId}")
    public ResponseEntity<ResponseWrapper<Unit>> updateUnitStatus(@PathVariable Long unitId) {
        Unit unit = unitService.updateUnitStatus(unitId);
        return ResponseEntity.ok(new ResponseWrapper<>("success",
                unit.getUnitNumber() + " unit updated successful", 1, unit));
    }
}
