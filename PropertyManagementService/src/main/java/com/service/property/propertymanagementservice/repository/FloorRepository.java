package com.service.property.propertymanagementservice.repository;

import com.service.property.propertymanagementservice.model.Floor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FloorRepository extends JpaRepository<Floor, Long> {

    List<Floor> findFloorsByPropertyId(Long propertyId);
}
