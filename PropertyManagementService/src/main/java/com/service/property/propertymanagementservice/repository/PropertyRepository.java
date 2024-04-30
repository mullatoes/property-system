package com.service.property.propertymanagementservice.repository;

import com.service.property.propertymanagementservice.model.Property;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PropertyRepository extends JpaRepository<Property, Long> {
}
