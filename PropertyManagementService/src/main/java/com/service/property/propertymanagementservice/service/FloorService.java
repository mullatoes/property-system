package com.service.property.propertymanagementservice.service;

import com.service.property.propertymanagementservice.dto.FloorDto;
import com.service.property.propertymanagementservice.model.Floor;
import com.service.property.propertymanagementservice.model.Property;
import com.service.property.propertymanagementservice.model.Unit;
import com.service.property.propertymanagementservice.repository.FloorRepository;
import com.service.property.propertymanagementservice.repository.PropertyRepository;
import com.service.property.propertymanagementservice.repository.UnitRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FloorService {

    private final FloorRepository floorRepository;
    private final PropertyRepository propertyRepository;
    private final UnitRepository unitRepository;

    public FloorService(FloorRepository floorRepository, PropertyRepository propertyRepository, UnitRepository unitRepository) {
        this.floorRepository = floorRepository;
        this.propertyRepository = propertyRepository;
        this.unitRepository = unitRepository;
    }


    public List<Floor> getAllFloors() {
        return floorRepository.findAll();
    }

    public Floor addFloor(Long propertyId, FloorDto floorDto) {
        Property property = propertyRepository.findById(propertyId).get();

        int numberOfFloors = property.getNumberOfFloors();

        if (floorDto.getFloorNumber() <= numberOfFloors)
        {
            Floor floor = new Floor();
            floor.setFloorNumber(floorDto.getFloorNumber());
            floor.setTotalArea(floorDto.getTotalArea());
            floor.setProperty(property);
            return floorRepository.save(floor);
        }else {
            //add good exception handling
            return null;
        }
    }

    public List<Unit> getAllUnitsByFloorId(Long floorId) {
        return unitRepository.getUnitsByFloorId(floorId);
    }

    public List<Floor> getAllFloorsForProperty(Long propertyId) {
        return floorRepository.findFloorsByPropertyId(propertyId);
    }
}
