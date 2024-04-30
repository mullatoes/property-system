package com.service.property.propertymanagementservice.service;

import com.service.property.propertymanagementservice.dto.UnitDto;
import com.service.property.propertymanagementservice.enums.UnitStatus;
import com.service.property.propertymanagementservice.model.Floor;
import com.service.property.propertymanagementservice.model.Property;
import com.service.property.propertymanagementservice.model.Unit;
import com.service.property.propertymanagementservice.repository.FloorRepository;
import com.service.property.propertymanagementservice.repository.PropertyRepository;
import com.service.property.propertymanagementservice.repository.UnitRepository;
import lombok.extern.java.Log;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UnitService {

    private final UnitRepository unitRepository;
    private final FloorRepository floorRepository;
    private final PropertyRepository propertyRepository;

    public UnitService(UnitRepository unitRepository, FloorRepository floorRepository, PropertyRepository propertyRepository) {
        this.unitRepository = unitRepository;
        this.floorRepository = floorRepository;
        this.propertyRepository = propertyRepository;
    }

    public List<Unit> getAllUnits() {
        return unitRepository.findAll();
    }

    public Unit addUnit(Long floorId, UnitDto unitDto) {

        Floor floor = floorRepository.findById(floorId).get();

        Unit unit = new Unit();
        unit.setFloor(floor);
        unit.setUnitNumber(unitDto.getUnitNumber());
        unit.setSquareFt(unitDto.getSquareFt());
        unit.setRentPerSqFt(unitDto.getRentPerSqFt());

        return unitRepository.save(unit);
    }

    public Unit getUnitById(Long id) {
        return unitRepository.findById(id).get();
    }

    public Unit updateUnit(Long unitId, UnitDto unitDto) {
        Unit unit = unitRepository.findById(unitId).get();
        unit.setUnitStatus(unitDto.getUnitStatus());
        return unitRepository.save(unit);
    }

    public Unit updateUnitStatus(Long unitId) {
        Unit unit = unitRepository.findById(unitId).get();
        unit.setUnitStatus(UnitStatus.OCCUPIED);
        return unitRepository.save(unit);
    }
}
