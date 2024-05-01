package com.service.property.propertymanagementservice.controller;

import com.service.property.propertymanagementservice.dto.FloorDto;
import com.service.property.propertymanagementservice.model.Floor;
import com.service.property.propertymanagementservice.model.Unit;
import com.service.property.propertymanagementservice.service.FloorService;
import com.service.property.propertymanagementservice.utils.ResponseWrapper;
import com.service.property.propertymanagementservice.utils.UnSuccessfulWrapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/floor")
public class FloorController {

    private final FloorService floorService;

    public FloorController(FloorService floorService) {
        this.floorService = floorService;
    }

    @GetMapping
    public ResponseEntity<ResponseWrapper<List<Floor>>> getAllFloors() {
        List<Floor> allFloors = floorService.getAllFloors();

        if (allFloors.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(new ResponseWrapper<>("success",
                "All floors retrieved successfully", allFloors.size(), allFloors));
    }

    @PostMapping("/{propertyId}")
    public ResponseEntity<Object> addFloor(@PathVariable Long propertyId,
                                                           @RequestBody FloorDto floorDto) {

        Floor floor = floorService.addFloor(propertyId, floorDto);

        if (floor == null) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new UnSuccessfulWrapper("failure", "Failed to create floor. Maximum number of floors reached"));
        }
        return ResponseEntity.ok(new ResponseWrapper<>("success",
                "Floor created successful", 1, floor));

    }


    @GetMapping("/{floorId}")
    public ResponseEntity<ResponseWrapper<List<Unit>>> getAllUnitsByFloorId(@PathVariable Long floorId){

        List<Unit> allUnitsByFloorId = floorService.getAllUnitsByFloorId(floorId);

        if (allUnitsByFloorId.isEmpty()) {
            ResponseEntity.ok(new ResponseWrapper<>("success",
                    "No units found for that floor", 0, allUnitsByFloorId));
        }

        return ResponseEntity.ok(new ResponseWrapper<>("success",
                "All units retrieved successfully", allUnitsByFloorId.size(), allUnitsByFloorId));

    }

    @GetMapping("/getAllFloorsForProperty/{propertyId}")
    public ResponseEntity<ResponseWrapper<List<Floor>>> getAllFloorsForProperty(@PathVariable Long propertyId){
        List<Floor> allFloorsForProperty = floorService.getAllFloorsForProperty(propertyId);

        if (allFloorsForProperty.isEmpty()) {
            ResponseEntity.ok(new ResponseWrapper<>("success",
                    "No Floors found for this property", 0, allFloorsForProperty));
        }

        return ResponseEntity.ok(new ResponseWrapper<>("success",
                "All floors retrieved successfully", allFloorsForProperty.size(), allFloorsForProperty));
    }
}
