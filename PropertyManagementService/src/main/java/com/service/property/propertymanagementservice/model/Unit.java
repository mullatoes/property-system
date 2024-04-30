package com.service.property.propertymanagementservice.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.service.property.propertymanagementservice.enums.UnitStatus;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "units")
public class Unit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String unitNumber;

    @Column(nullable = false)
    private double squareFt;

    @ManyToOne
    @JoinColumn(name = "floor_id", nullable = false)
    @JsonIgnore
    private Floor floor;

    @Column(nullable = false)
    private double rentPerSqFt;

    @Column(nullable = false)
    private UnitStatus unitStatus = UnitStatus.VACANT;

}

