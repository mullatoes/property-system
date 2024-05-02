package com.service.property.propertymanagementservice.repository;

import com.service.property.propertymanagementservice.model.Unit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UnitRepository extends JpaRepository<Unit, Long> {

    @Query("select u from Unit u where u.floor.id = :floorId")
    List<Unit> getUnitsByFloorId(@Param("floorId") Long floorId);

    @Query("select u from Unit u inner join Floor f on f.id = u.floor.id inner join Property p on p.id = f.property.id  where p.id= :propertyId")
    List<Unit> getUnitsByPropertyId(@Param("propertyId") Long propertyId);

}
