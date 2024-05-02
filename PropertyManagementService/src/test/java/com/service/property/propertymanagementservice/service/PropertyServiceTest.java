package com.service.property.propertymanagementservice.service;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.anyDouble;
import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.service.property.propertymanagementservice.dto.PropertyDto;
import com.service.property.propertymanagementservice.model.Floor;
import com.service.property.propertymanagementservice.model.Property;
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

@ContextConfiguration(classes = {PropertyService.class})
@ExtendWith(SpringExtension.class)
@DisabledInAotMode
class PropertyServiceTest {
    @MockBean
    private FloorRepository floorRepository;

    @MockBean
    private PropertyRepository propertyRepository;

    @Autowired
    private PropertyService propertyService;

    @MockBean
    private UnitRepository unitRepository;

    @Test
    void testGetAllProperty() {
        // Arrange
        ArrayList<Property> propertyList = new ArrayList<>();
        when(propertyRepository.findAll()).thenReturn(propertyList);

        // Act
        List<Property> actualAllProperty = propertyService.getAllProperty();

        // Assert
        verify(propertyRepository).findAll();
        assertTrue(actualAllProperty.isEmpty());
        assertSame(propertyList, actualAllProperty);
    }

    @Test
    void testGetAllProperty2() {
        // Arrange
        when(propertyRepository.findAll()).thenThrow(new RuntimeException("foo"));

        // Act and Assert
        assertThrows(RuntimeException.class, () -> propertyService.getAllProperty());
        verify(propertyRepository).findAll();
    }

    @Test
    void testAddProperty() {
        // Arrange
        Property property = new Property();
        property.setAddress("42 Main St");
        property.setDescription("The characteristics of someone or something");
        property.setId(1L);
        property.setName("Name");
        property.setNumberOfFloors(10);
        property.setTotalArea(10.0d);
        when(propertyRepository.save(Mockito.<Property>any())).thenReturn(property);

        PropertyDto propertyDto = new PropertyDto();
        propertyDto.setAddress("42 Main St");
        propertyDto.setDescription("The characteristics of someone or something");
        propertyDto.setName("Name");
        propertyDto.setNumberOfFloors(10);
        propertyDto.setTotalArea(10.0d);

        // Act
        Property actualAddPropertyResult = propertyService.addProperty(propertyDto);

        // Assert
        verify(propertyRepository).save(isA(Property.class));
        assertSame(property, actualAddPropertyResult);
    }

    @Test
    void testAddProperty2() {
        // Arrange
        when(propertyRepository.save(Mockito.<Property>any())).thenThrow(new RuntimeException("foo"));

        PropertyDto propertyDto = new PropertyDto();
        propertyDto.setAddress("42 Main St");
        propertyDto.setDescription("The characteristics of someone or something");
        propertyDto.setName("Name");
        propertyDto.setNumberOfFloors(10);
        propertyDto.setTotalArea(10.0d);

        // Act and Assert
        assertThrows(RuntimeException.class, () -> propertyService.addProperty(propertyDto));
        verify(propertyRepository).save(isA(Property.class));
    }

    @Test
    void testUpdateProperty() {
        // Arrange
        Property property = new Property();
        property.setAddress("42 Main St");
        property.setDescription("The characteristics of someone or something");
        property.setId(1L);
        property.setName("Name");
        property.setNumberOfFloors(10);
        property.setTotalArea(10.0d);
        Optional<Property> ofResult = Optional.of(property);

        Property property2 = new Property();
        property2.setAddress("42 Main St");
        property2.setDescription("The characteristics of someone or something");
        property2.setId(1L);
        property2.setName("Name");
        property2.setNumberOfFloors(10);
        property2.setTotalArea(10.0d);
        when(propertyRepository.save(Mockito.<Property>any())).thenReturn(property2);
        when(propertyRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);

        PropertyDto propertyDto = new PropertyDto();
        propertyDto.setAddress("42 Main St");
        propertyDto.setDescription("The characteristics of someone or something");
        propertyDto.setName("Name");
        propertyDto.setNumberOfFloors(10);
        propertyDto.setTotalArea(10.0d);

        // Act
        Property actualUpdatePropertyResult = propertyService.updateProperty(1L, propertyDto);

        // Assert
        verify(propertyRepository).findById(eq(1L));
        verify(propertyRepository).save(isA(Property.class));
        assertSame(property2, actualUpdatePropertyResult);
    }

    @Test
    void testUpdateProperty2() {
        // Arrange
        Property property = new Property();
        property.setAddress("42 Main St");
        property.setDescription("The characteristics of someone or something");
        property.setId(1L);
        property.setName("Name");
        property.setNumberOfFloors(10);
        property.setTotalArea(10.0d);
        Optional<Property> ofResult = Optional.of(property);
        when(propertyRepository.save(Mockito.<Property>any())).thenThrow(new RuntimeException("foo"));
        when(propertyRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);

        PropertyDto propertyDto = new PropertyDto();
        propertyDto.setAddress("42 Main St");
        propertyDto.setDescription("The characteristics of someone or something");
        propertyDto.setName("Name");
        propertyDto.setNumberOfFloors(10);
        propertyDto.setTotalArea(10.0d);

        // Act and Assert
        assertThrows(RuntimeException.class, () -> propertyService.updateProperty(1L, propertyDto));
        verify(propertyRepository).findById(eq(1L));
        verify(propertyRepository).save(isA(Property.class));
    }

    @Test
    void testDeleteById() {
        // Arrange
        Property property = new Property();
        property.setAddress("42 Main St");
        property.setDescription("The characteristics of someone or something");
        property.setId(1L);
        property.setName("Name");
        property.setNumberOfFloors(10);
        property.setTotalArea(10.0d);
        Optional<Property> ofResult = Optional.of(property);
        doNothing().when(propertyRepository).deleteById(Mockito.<Long>any());
        when(propertyRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);
        when(floorRepository.findAll()).thenReturn(new ArrayList<>());

        // Act
        propertyService.deleteById(1L);

        // Assert that nothing has changed
        verify(propertyRepository).deleteById(eq(1L));
        verify(propertyRepository).findById(eq(1L));
        verify(floorRepository).findAll();
    }

    @Test
    void testDeleteById2() {
        // Arrange
        Property property = new Property();
        property.setAddress("42 Main St");
        property.setDescription("The characteristics of someone or something");
        property.setId(1L);
        property.setName("Name");
        property.setNumberOfFloors(10);
        property.setTotalArea(10.0d);
        Optional<Property> ofResult = Optional.of(property);
        when(propertyRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);
        when(floorRepository.findAll()).thenThrow(new RuntimeException("foo"));

        // Act and Assert
        assertThrows(RuntimeException.class, () -> propertyService.deleteById(1L));
        verify(propertyRepository).findById(eq(1L));
        verify(floorRepository).findAll();
    }

    @Test
    void testDeleteById3() {
        // Arrange
        Property property = new Property();
        property.setAddress("42 Main St");
        property.setDescription("The characteristics of someone or something");
        property.setId(1L);
        property.setName("Name");
        property.setNumberOfFloors(10);
        property.setTotalArea(10.0d);
        Optional<Property> ofResult = Optional.of(property);
        doNothing().when(propertyRepository).deleteById(Mockito.<Long>any());
        when(propertyRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);

        Property property2 = new Property();
        property2.setAddress("42 Main St");
        property2.setDescription("The characteristics of someone or something");
        property2.setId(1L);
        property2.setName("Name");
        property2.setNumberOfFloors(10);
        property2.setTotalArea(10.0d);

        Floor floor = new Floor();
        floor.setFloorNumber(10);
        floor.setId(1L);
        floor.setProperty(property2);
        floor.setTotalArea(10.0d);

        ArrayList<Floor> floorList = new ArrayList<>();
        floorList.add(floor);
        when(floorRepository.findAll()).thenReturn(floorList);
        when(unitRepository.getUnitsByFloorId(Mockito.<Long>any())).thenReturn(new ArrayList<>());

        // Act
        propertyService.deleteById(1L);

        // Assert that nothing has changed
        verify(unitRepository).getUnitsByFloorId(eq(1L));
        verify(propertyRepository).deleteById(eq(1L));
        verify(propertyRepository).findById(eq(1L));
        verify(floorRepository).findAll();
    }

    @Test
    void testDeleteById4() {
        // Arrange
        Property property = mock(Property.class);
        when(property.getId()).thenReturn(-1L);
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
        doNothing().when(propertyRepository).deleteById(Mockito.<Long>any());
        when(propertyRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);

        Property property2 = new Property();
        property2.setAddress("42 Main St");
        property2.setDescription("The characteristics of someone or something");
        property2.setId(1L);
        property2.setName("Name");
        property2.setNumberOfFloors(10);
        property2.setTotalArea(10.0d);

        Floor floor = new Floor();
        floor.setFloorNumber(10);
        floor.setId(1L);
        floor.setProperty(property2);
        floor.setTotalArea(10.0d);

        ArrayList<Floor> floorList = new ArrayList<>();
        floorList.add(floor);
        when(floorRepository.findAll()).thenReturn(floorList);

        // Act
        propertyService.deleteById(1L);

        // Assert that nothing has changed
        verify(property).getId();
        verify(property).setAddress(eq("42 Main St"));
        verify(property).setDescription(eq("The characteristics of someone or something"));
        verify(property).setId(eq(1L));
        verify(property).setName(eq("Name"));
        verify(property).setNumberOfFloors(eq(10));
        verify(property).setTotalArea(eq(10.0d));
        verify(propertyRepository).deleteById(eq(1L));
        verify(propertyRepository).findById(eq(1L));
        verify(floorRepository).findAll();
    }

    @Test
    void testDeleteById5() {
        // Arrange
        Property property = mock(Property.class);
        when(property.getId()).thenReturn(1L);
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

        Property property2 = new Property();
        property2.setAddress("42 Main St");
        property2.setDescription("The characteristics of someone or something");
        property2.setId(1L);
        property2.setName("Name");
        property2.setNumberOfFloors(10);
        property2.setTotalArea(10.0d);

        Property property3 = new Property();
        property3.setAddress("42 Main St");
        property3.setDescription("The characteristics of someone or something");
        property3.setId(1L);
        property3.setName("Name");
        property3.setNumberOfFloors(10);
        property3.setTotalArea(10.0d);
        Floor floor = mock(Floor.class);
        when(floor.getId()).thenThrow(new RuntimeException("foo"));
        when(floor.getProperty()).thenReturn(property3);
        doNothing().when(floor).setFloorNumber(anyInt());
        doNothing().when(floor).setId(Mockito.<Long>any());
        doNothing().when(floor).setProperty(Mockito.<Property>any());
        doNothing().when(floor).setTotalArea(anyDouble());
        floor.setFloorNumber(10);
        floor.setId(1L);
        floor.setProperty(property2);
        floor.setTotalArea(10.0d);

        ArrayList<Floor> floorList = new ArrayList<>();
        floorList.add(floor);
        when(floorRepository.findAll()).thenReturn(floorList);

        // Act and Assert
        assertThrows(RuntimeException.class, () -> propertyService.deleteById(1L));
        verify(floor).getId();
        verify(floor).getProperty();
        verify(floor).setFloorNumber(eq(10));
        verify(floor).setId(eq(1L));
        verify(floor).setProperty(isA(Property.class));
        verify(floor).setTotalArea(eq(10.0d));
        verify(property).getId();
        verify(property).setAddress(eq("42 Main St"));
        verify(property).setDescription(eq("The characteristics of someone or something"));
        verify(property).setId(eq(1L));
        verify(property).setName(eq("Name"));
        verify(property).setNumberOfFloors(eq(10));
        verify(property).setTotalArea(eq(10.0d));
        verify(propertyRepository).findById(eq(1L));
        verify(floorRepository).findAll();
    }

    @Test
    void testGetPropertyById() {
        // Arrange
        Property property = new Property();
        property.setAddress("42 Main St");
        property.setDescription("The characteristics of someone or something");
        property.setId(1L);
        property.setName("Name");
        property.setNumberOfFloors(10);
        property.setTotalArea(10.0d);
        Optional<Property> ofResult = Optional.of(property);
        when(propertyRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);

        // Act
        Property actualPropertyById = propertyService.getPropertyById(1L);

        // Assert
        verify(propertyRepository).findById(eq(1L));
        assertSame(property, actualPropertyById);
    }

    @Test
    void testGetPropertyById2() {
        // Arrange
        when(propertyRepository.findById(Mockito.<Long>any())).thenThrow(new RuntimeException("foo"));

        // Act and Assert
        assertThrows(RuntimeException.class, () -> propertyService.getPropertyById(1L));
        verify(propertyRepository).findById(eq(1L));
    }
}
