package com.service.property.propertymanagementservice.controller;

import com.service.property.propertymanagementservice.dto.PropertyDto;
import com.service.property.propertymanagementservice.model.Property;
import com.service.property.propertymanagementservice.service.PropertyService;
import com.service.property.propertymanagementservice.utils.ResponseWrapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/property")
public class PropertyController {

    private final PropertyService propertyService;

    public PropertyController(PropertyService propertyService) {
        this.propertyService = propertyService;
    }

    @GetMapping
    public ResponseEntity<ResponseWrapper<List<Property>>> getAllProperty() {
        List<Property> properties = propertyService.getAllProperty();

        return ResponseEntity.ok(new ResponseWrapper<>("success",
                "All properties retrieved successfully", properties.size(), properties));

    }

    @PostMapping
    public ResponseEntity<ResponseWrapper<Property>> addProperty(@RequestBody PropertyDto propertyDto) {
        Property property = propertyService.addProperty(propertyDto);
        return ResponseEntity.ok(new ResponseWrapper<>("success",
                propertyDto.getName() + " added successfully",1, property));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseWrapper<Property>> updateProperty(@PathVariable Long id, @RequestBody PropertyDto propertyDto) {
        Property property = propertyService.updateProperty(id,propertyDto);

        return ResponseEntity.ok(new ResponseWrapper<>("success",
                propertyDto.getName() + " updated successfully", property));
    }

    @DeleteMapping
    public ResponseEntity<ResponseWrapper<String>> deleteProperty(@RequestParam Long id) {
        propertyService.deleteById(id);
        return ResponseEntity.ok(new ResponseWrapper<>("success",
                "Property deleted successfully"));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseWrapper<Property>> getPropertyById(@PathVariable Long id) {
        Property property = propertyService.getPropertyById(id);

        return ResponseEntity.ok(new ResponseWrapper<>("success",
                "Property " + property.getName() + " retrieved successfully", 1, property));
    }
}
