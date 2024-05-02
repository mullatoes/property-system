package com.service.property.propertymanagementservice.service;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.anyDouble;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.service.property.propertymanagementservice.dto.UnitDto;
import com.service.property.propertymanagementservice.enums.UnitStatus;
import com.service.property.propertymanagementservice.model.Floor;
import com.service.property.propertymanagementservice.model.Property;
import com.service.property.propertymanagementservice.model.Unit;
import com.service.property.propertymanagementservice.repository.FloorRepository;
import com.service.property.propertymanagementservice.repository.PropertyRepository;
import com.service.property.propertymanagementservice.repository.UnitRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {UnitService.class})
@ExtendWith(SpringExtension.class)
@DisabledInAotMode
class UnitServiceTest {
    @MockBean
    private FloorRepository floorRepository;

    @MockBean
    private PropertyRepository propertyRepository;

    @MockBean
    private UnitRepository unitRepository;

    @Autowired
    private UnitService unitService;

    @Test
    void testGetAllUnits() {
        // Arrange
        ArrayList<Unit> unitList = new ArrayList<>();
        when(unitRepository.findAll()).thenReturn(unitList);

        // Act
        List<Unit> actualAllUnits = unitService.getAllUnits();

        // Assert
        verify(unitRepository).findAll();
        assertTrue(actualAllUnits.isEmpty());
        assertSame(unitList, actualAllUnits);
    }

    @Test
    void testAddUnit() {
        // Arrange
        Property property = new Property();
        property.setAddress("42 Main St");
        property.setDescription("The characteristics of someone or something");
        property.setId(1L);
        property.setName("Name");
        property.setNumberOfFloors(10);
        property.setTotalArea(10.0d);

        Floor floor = new Floor();
        floor.setFloorNumber(10);
        floor.setId(1L);
        floor.setProperty(property);
        floor.setTotalArea(10.0d);

        Unit unit = new Unit();
        unit.setFloor(floor);
        unit.setId(1L);
        unit.setRentPerSqFt(10.0d);
        unit.setSquareFt(10.0d);
        unit.setUnitNumber("42");
        unit.setUnitStatus(UnitStatus.OCCUPIED);
        when(unitRepository.save(Mockito.<Unit>any())).thenReturn(unit);

        Property property2 = new Property();
        property2.setAddress("42 Main St");
        property2.setDescription("The characteristics of someone or something");
        property2.setId(1L);
        property2.setName("Name");
        property2.setNumberOfFloors(10);
        property2.setTotalArea(10.0d);

        Floor floor2 = new Floor();
        floor2.setFloorNumber(10);
        floor2.setId(1L);
        floor2.setProperty(property2);
        floor2.setTotalArea(10.0d);
        Optional<Floor> ofResult = Optional.of(floor2);
        when(floorRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);

        UnitDto unitDto = new UnitDto();
        unitDto.setRentPerSqFt(10.0d);
        unitDto.setSquareFt(10.0d);
        unitDto.setUnitNumber("42");
        unitDto.setUnitStatus(UnitStatus.OCCUPIED);

        // Act
        Unit actualAddUnitResult = unitService.addUnit(1L, unitDto);

        // Assert
        verify(floorRepository).findById(eq(1L));
        verify(unitRepository).save(isA(Unit.class));
        assertSame(unit, actualAddUnitResult);
    }

    @Test
    void testGetUnitById() {
        // Arrange
        Property property = new Property();
        property.setAddress("42 Main St");
        property.setDescription("The characteristics of someone or something");
        property.setId(1L);
        property.setName("Name");
        property.setNumberOfFloors(10);
        property.setTotalArea(10.0d);

        Floor floor = new Floor();
        floor.setFloorNumber(10);
        floor.setId(1L);
        floor.setProperty(property);
        floor.setTotalArea(10.0d);

        Unit unit = new Unit();
        unit.setFloor(floor);
        unit.setId(1L);
        unit.setRentPerSqFt(10.0d);
        unit.setSquareFt(10.0d);
        unit.setUnitNumber("42");
        unit.setUnitStatus(UnitStatus.OCCUPIED);
        Optional<Unit> ofResult = Optional.of(unit);
        when(unitRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);

        // Act
        Unit actualUnitById = unitService.getUnitById(1L);

        // Assert
        verify(unitRepository).findById(eq(1L));
        assertSame(unit, actualUnitById);
    }

    @Test
    void testUpdateUnit() {
        // Arrange
        Property property = new Property();
        property.setAddress("42 Main St");
        property.setDescription("The characteristics of someone or something");
        property.setId(1L);
        property.setName("Name");
        property.setNumberOfFloors(10);
        property.setTotalArea(10.0d);

        Floor floor = new Floor();
        floor.setFloorNumber(10);
        floor.setId(1L);
        floor.setProperty(property);
        floor.setTotalArea(10.0d);

        Unit unit = new Unit();
        unit.setFloor(floor);
        unit.setId(1L);
        unit.setRentPerSqFt(10.0d);
        unit.setSquareFt(10.0d);
        unit.setUnitNumber("42");
        unit.setUnitStatus(UnitStatus.OCCUPIED);
        Optional<Unit> ofResult = Optional.of(unit);

        Property property2 = new Property();
        property2.setAddress("42 Main St");
        property2.setDescription("The characteristics of someone or something");
        property2.setId(1L);
        property2.setName("Name");
        property2.setNumberOfFloors(10);
        property2.setTotalArea(10.0d);

        Floor floor2 = new Floor();
        floor2.setFloorNumber(10);
        floor2.setId(1L);
        floor2.setProperty(property2);
        floor2.setTotalArea(10.0d);

        Unit unit2 = new Unit();
        unit2.setFloor(floor2);
        unit2.setId(1L);
        unit2.setRentPerSqFt(10.0d);
        unit2.setSquareFt(10.0d);
        unit2.setUnitNumber("42");
        unit2.setUnitStatus(UnitStatus.OCCUPIED);
        when(unitRepository.save(Mockito.<Unit>any())).thenReturn(unit2);
        when(unitRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);

        UnitDto unitDto = new UnitDto();
        unitDto.setRentPerSqFt(10.0d);
        unitDto.setSquareFt(10.0d);
        unitDto.setUnitNumber("42");
        unitDto.setUnitStatus(UnitStatus.OCCUPIED);

        // Act
        Unit actualUpdateUnitResult = unitService.updateUnit(1L, unitDto);

        // Assert
        verify(unitRepository).findById(eq(1L));
        verify(unitRepository).save(isA(Unit.class));
        assertSame(unit2, actualUpdateUnitResult);
    }

    @Test
    void testUpdateUnit2() {
        // Arrange
        Property property = new Property();
        property.setAddress("42 Main St");
        property.setDescription("The characteristics of someone or something");
        property.setId(1L);
        property.setName("Name");
        property.setNumberOfFloors(10);
        property.setTotalArea(10.0d);

        Floor floor = new Floor();
        floor.setFloorNumber(10);
        floor.setId(1L);
        floor.setProperty(property);
        floor.setTotalArea(10.0d);
        Unit unit = mock(Unit.class);
        doNothing().when(unit).setFloor(Mockito.<Floor>any());
        doNothing().when(unit).setId(Mockito.<Long>any());
        doNothing().when(unit).setRentPerSqFt(anyDouble());
        doNothing().when(unit).setSquareFt(anyDouble());
        doNothing().when(unit).setUnitNumber(Mockito.<String>any());
        doNothing().when(unit).setUnitStatus(Mockito.<UnitStatus>any());
        unit.setFloor(floor);
        unit.setId(1L);
        unit.setRentPerSqFt(10.0d);
        unit.setSquareFt(10.0d);
        unit.setUnitNumber("42");
        unit.setUnitStatus(UnitStatus.OCCUPIED);
        Optional<Unit> ofResult = Optional.of(unit);

        Property property2 = new Property();
        property2.setAddress("42 Main St");
        property2.setDescription("The characteristics of someone or something");
        property2.setId(1L);
        property2.setName("Name");
        property2.setNumberOfFloors(10);
        property2.setTotalArea(10.0d);

        Floor floor2 = new Floor();
        floor2.setFloorNumber(10);
        floor2.setId(1L);
        floor2.setProperty(property2);
        floor2.setTotalArea(10.0d);

        Unit unit2 = new Unit();
        unit2.setFloor(floor2);
        unit2.setId(1L);
        unit2.setRentPerSqFt(10.0d);
        unit2.setSquareFt(10.0d);
        unit2.setUnitNumber("42");
        unit2.setUnitStatus(UnitStatus.OCCUPIED);
        when(unitRepository.save(Mockito.<Unit>any())).thenReturn(unit2);
        when(unitRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);

        UnitDto unitDto = new UnitDto();
        unitDto.setRentPerSqFt(10.0d);
        unitDto.setSquareFt(10.0d);
        unitDto.setUnitNumber("42");
        unitDto.setUnitStatus(UnitStatus.OCCUPIED);

        // Act
        Unit actualUpdateUnitResult = unitService.updateUnit(1L, unitDto);

        // Assert
        verify(unit).setFloor(isA(Floor.class));
        verify(unit).setId(eq(1L));
        verify(unit).setRentPerSqFt(eq(10.0d));
        verify(unit).setSquareFt(eq(10.0d));
        verify(unit).setUnitNumber(eq("42"));
        verify(unit, atLeast(1)).setUnitStatus(eq(UnitStatus.OCCUPIED));
        verify(unitRepository).findById(eq(1L));
        verify(unitRepository).save(isA(Unit.class));
        assertSame(unit2, actualUpdateUnitResult);
    }

    @Test
    void testUpdateUnitStatus() {
        // Arrange
        Property property = new Property();
        property.setAddress("42 Main St");
        property.setDescription("The characteristics of someone or something");
        property.setId(1L);
        property.setName("Name");
        property.setNumberOfFloors(10);
        property.setTotalArea(10.0d);

        Floor floor = new Floor();
        floor.setFloorNumber(10);
        floor.setId(1L);
        floor.setProperty(property);
        floor.setTotalArea(10.0d);

        Unit unit = new Unit();
        unit.setFloor(floor);
        unit.setId(1L);
        unit.setRentPerSqFt(10.0d);
        unit.setSquareFt(10.0d);
        unit.setUnitNumber("42");
        unit.setUnitStatus(UnitStatus.OCCUPIED);
        Optional<Unit> ofResult = Optional.of(unit);

        Property property2 = new Property();
        property2.setAddress("42 Main St");
        property2.setDescription("The characteristics of someone or something");
        property2.setId(1L);
        property2.setName("Name");
        property2.setNumberOfFloors(10);
        property2.setTotalArea(10.0d);

        Floor floor2 = new Floor();
        floor2.setFloorNumber(10);
        floor2.setId(1L);
        floor2.setProperty(property2);
        floor2.setTotalArea(10.0d);

        Unit unit2 = new Unit();
        unit2.setFloor(floor2);
        unit2.setId(1L);
        unit2.setRentPerSqFt(10.0d);
        unit2.setSquareFt(10.0d);
        unit2.setUnitNumber("42");
        unit2.setUnitStatus(UnitStatus.OCCUPIED);
        when(unitRepository.save(Mockito.<Unit>any())).thenReturn(unit2);
        when(unitRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);

        // Act
        Unit actualUpdateUnitStatusResult = unitService.updateUnitStatus(1L);

        // Assert
        verify(unitRepository).findById(eq(1L));
        verify(unitRepository).save(isA(Unit.class));
        assertSame(unit2, actualUpdateUnitStatusResult);
    }

    @Test
    void testUpdateUnitStatus2() {
        // Arrange
        Property property = new Property();
        property.setAddress("42 Main St");
        property.setDescription("The characteristics of someone or something");
        property.setId(1L);
        property.setName("Name");
        property.setNumberOfFloors(10);
        property.setTotalArea(10.0d);

        Floor floor = new Floor();
        floor.setFloorNumber(10);
        floor.setId(1L);
        floor.setProperty(property);
        floor.setTotalArea(10.0d);
        Unit unit = mock(Unit.class);
        doNothing().when(unit).setFloor(Mockito.<Floor>any());
        doNothing().when(unit).setId(Mockito.<Long>any());
        doNothing().when(unit).setRentPerSqFt(anyDouble());
        doNothing().when(unit).setSquareFt(anyDouble());
        doNothing().when(unit).setUnitNumber(Mockito.<String>any());
        doNothing().when(unit).setUnitStatus(Mockito.<UnitStatus>any());
        unit.setFloor(floor);
        unit.setId(1L);
        unit.setRentPerSqFt(10.0d);
        unit.setSquareFt(10.0d);
        unit.setUnitNumber("42");
        unit.setUnitStatus(UnitStatus.OCCUPIED);
        Optional<Unit> ofResult = Optional.of(unit);

        Property property2 = new Property();
        property2.setAddress("42 Main St");
        property2.setDescription("The characteristics of someone or something");
        property2.setId(1L);
        property2.setName("Name");
        property2.setNumberOfFloors(10);
        property2.setTotalArea(10.0d);

        Floor floor2 = new Floor();
        floor2.setFloorNumber(10);
        floor2.setId(1L);
        floor2.setProperty(property2);
        floor2.setTotalArea(10.0d);

        Unit unit2 = new Unit();
        unit2.setFloor(floor2);
        unit2.setId(1L);
        unit2.setRentPerSqFt(10.0d);
        unit2.setSquareFt(10.0d);
        unit2.setUnitNumber("42");
        unit2.setUnitStatus(UnitStatus.OCCUPIED);
        when(unitRepository.save(Mockito.<Unit>any())).thenReturn(unit2);
        when(unitRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);

        // Act
        Unit actualUpdateUnitStatusResult = unitService.updateUnitStatus(1L);

        // Assert
        verify(unit).setFloor(isA(Floor.class));
        verify(unit).setId(eq(1L));
        verify(unit).setRentPerSqFt(eq(10.0d));
        verify(unit).setSquareFt(eq(10.0d));
        verify(unit).setUnitNumber(eq("42"));
        verify(unit, atLeast(1)).setUnitStatus(eq(UnitStatus.OCCUPIED));
        verify(unitRepository).findById(eq(1L));
        verify(unitRepository).save(isA(Unit.class));
        assertSame(unit2, actualUpdateUnitStatusResult);
    }
}
