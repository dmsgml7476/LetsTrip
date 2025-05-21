package com.trip.entity.Planner;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class VehicleEntity {
@Id
@GeneratedValue(strategy=GenerationType.IDENTITY)
private Long vehicleId;
private String vehicleType;
private String vehicleDetail;

}
