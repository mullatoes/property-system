package com.service.property.propertymanagementservice.service;

import com.service.property.propertymanagementservice.dto.PropertyDto;
import com.service.property.propertymanagementservice.enums.UnitStatus;
import com.service.property.propertymanagementservice.model.Floor;
import com.service.property.propertymanagementservice.model.Property;
import com.service.property.propertymanagementservice.repository.FloorRepository;
import com.service.property.propertymanagementservice.repository.PropertyRepository;
import com.service.property.propertymanagementservice.repository.UnitRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PropertyService {

    private final PropertyRepository propertyRepository;
    private final FloorRepository floorRepository;
    private final UnitRepository unitRepository;

    public PropertyService(PropertyRepository propertyRepository, FloorRepository floorRepository, UnitRepository unitRepository) {
        this.propertyRepository = propertyRepository;
        this.floorRepository = floorRepository;
        this.unitRepository = unitRepository;
    }

    public List<Property> getAllProperty() {
        return propertyRepository.findAll();
    }


    public Property addProperty(PropertyDto propertyDto) {
        Property property = new Property();
        property.setName(propertyDto.getName());
        property.setDescription(propertyDto.getDescription());
        property.setAddress(propertyDto.getAddress());
        property.setTotalArea(propertyDto.getTotalArea());
        property.setNumberOfFloors(propertyDto.getNumberOfFloors());
        return propertyRepository.save(property);
    }

    public Property updateProperty(Long id, PropertyDto propertyDto) {
        Property property = propertyRepository.findById(id).get();

        property.setName(propertyDto.getName());
        property.setDescription(propertyDto.getDescription());
        property.setAddress(propertyDto.getAddress());
        property.setTotalArea(propertyDto.getTotalArea());
        property.setNumberOfFloors(propertyDto.getNumberOfFloors());
        return propertyRepository.save(property);
    }

    public void deleteById(Long id) {
        Property property = propertyRepository.findById(id).get();
        Long propertyId = property.getId();
        List<Floor> floors = floorRepository.findAll();

        for (Floor floor : floors) {
            if (propertyId.equals(floor.getProperty().getId())) {
                unitRepository.getUnitsByFloorId(floor.getId()).forEach(unit -> {
                    if (unit.getUnitStatus().equals(UnitStatus.OCCUPIED)) {
                        System.out.println("You can't delete this property");
                        throw new RuntimeException("You can't delete this property. It has an occupied unit");
                    }
                });
            }
        }

        propertyRepository.deleteById(id);
    }

    public Property getPropertyById(Long id) {
        return propertyRepository.findById(id).get();
    }
}
