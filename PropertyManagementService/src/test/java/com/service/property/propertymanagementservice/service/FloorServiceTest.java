package com.service.property.propertymanagementservice.service;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.anyDouble;
import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.service.property.propertymanagementservice.dto.FloorDto;
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

@ContextConfiguration(classes = {FloorService.class})
@ExtendWith(SpringExtension.class)
@DisabledInAotMode
class FloorServiceTest {
    @MockBean
    private FloorRepository floorRepository;

    @Autowired
    private FloorService floorService;

    @MockBean
    private PropertyRepository propertyRepository;

    @MockBean
    private UnitRepository unitRepository;

    @Test
    void testGetAllFloors() {
        // Arrange
        ArrayList<Floor> floorList = new ArrayList<>();
        when(floorRepository.findAll()).thenReturn(floorList);

        // Act
        List<Floor> actualAllFloors = floorService.getAllFloors();

        // Assert
        verify(floorRepository).findAll();
        assertTrue(actualAllFloors.isEmpty());
        assertSame(floorList, actualAllFloors);
    }

    @Test
    void testAddFloor() {
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
        when(floorRepository.save(Mockito.<Floor>any())).thenReturn(floor);

        Property property2 = new Property();
        property2.setAddress("42 Main St");
        property2.setDescription("The characteristics of someone or something");
        property2.setId(1L);
        property2.setName("Name");
        property2.setNumberOfFloors(10);
        property2.setTotalArea(10.0d);
        Optional<Property> ofResult = Optional.of(property2);
        when(propertyRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);

        FloorDto floorDto = new FloorDto();
        floorDto.setFloorNumber(10);
        floorDto.setTotalArea(10.0d);

        // Act
        Floor actualAddFloorResult = floorService.addFloor(1L, floorDto);

        // Assert
        verify(propertyRepository).findById(eq(1L));
        verify(floorRepository).save(isA(Floor.class));
        assertSame(floor, actualAddFloorResult);
    }

    @Test
    void testAddFloor2() {
        // Arrange
        Property property = mock(Property.class);
        when(property.getNumberOfFloors()).thenReturn(1);
        doNothing().when(property).setAddress(Mockito.<String>any());
        doNothing().when(property).setDescription(Mockito.<String>any());
        doNothing().when(property).setId(Mockito.<Long>any());
        doNothing().when(property).setName(Mockito.<String>any());
        doNothing().when(property).setNumberOfFloors(anyInt());
        doNothing().when(property).setTotalArea(anyDouble());
        property.setAddress("42 Main St");
        property.setDescription("The characteristics of someone or something");
        property.setId(1L);
        property.setName("Name");
        property.setNumberOfFloors(10);
        property.setTotalArea(10.0d);
        Optional<Property> ofResult = Optional.of(property);
        when(propertyRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);

        FloorDto floorDto = new FloorDto();
        floorDto.setFloorNumber(10);
        floorDto.setTotalArea(10.0d);

        // Act
        Floor actualAddFloorResult = floorService.addFloor(1L, floorDto);

        // Assert
        verify(property).getNumberOfFloors();
        verify(property).setAddress(eq("42 Main St"));
        verify(property).setDescription(eq("The characteristics of someone or something"));
        verify(property).setId(eq(1L));
        verify(property).setName(eq("Name"));
        verify(property).setNumberOfFloors(eq(10));
        verify(property).setTotalArea(eq(10.0d));
        verify(propertyRepository).findById(eq(1L));
        assertNull(actualAddFloorResult);
    }

    @Test
    void testGetAllUnitsByFloorId() {
        // Arrange
        ArrayList<Unit> unitList = new ArrayList<>();
        when(unitRepository.getUnitsByFloorId(Mockito.<Long>any())).thenReturn(unitList);

        // Act
        List<Unit> actualAllUnitsByFloorId = floorService.getAllUnitsByFloorId(1L);

        // Assert
        verify(unitRepository).getUnitsByFloorId(eq(1L));
        assertTrue(actualAllUnitsByFloorId.isEmpty());
        assertSame(unitList, actualAllUnitsByFloorId);
    }

    @Test
    void testGetAllFloorsForProperty() {
        // Arrange
        ArrayList<Floor> floorList = new ArrayList<>();
        when(floorRepository.findFloorsByPropertyId(Mockito.<Long>any())).thenReturn(floorList);

        // Act
        List<Floor> actualAllFloorsForProperty = floorService.getAllFloorsForProperty(1L);

        // Assert
        verify(floorRepository).findFloorsByPropertyId(eq(1L));
        assertTrue(actualAllFloorsForProperty.isEmpty());
        assertSame(floorList, actualAllFloorsForProperty);
    }

    @Test
    void testGetUnitsByFloorId() {
        // Arrange
        ArrayList<Unit> unitList = new ArrayList<>();
        when(unitRepository.getUnitsByFloorId(Mockito.<Long>any())).thenReturn(unitList);

        // Act
        List<Unit> actualUnitsByFloorId = floorService.getUnitsByFloorId(1L);

        // Assert
        verify(unitRepository).getUnitsByFloorId(eq(1L));
        assertTrue(actualUnitsByFloorId.isEmpty());
        assertSame(unitList, actualUnitsByFloorId);
    }
}
