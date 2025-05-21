package com.trip.entity.Planner;

import java.time.LocalTime;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class PlanTrafficEntity {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long planTrafficId;
	private int planDateId;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="depart_place_id", nullable = false)
	private PlaceEntity departPlace;
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="arrival_place_id", nullable = false)
	private PlaceEntity arrivalPlace;
	private LocalTime trafficDepartTime;
	private LocalTime trafficArrivalTime;
	@ManyToOne
	@JoinColumn(name="vehicle_id", nullable = false)
	private VehicleEntity vehicleEntity;
	private String more;
	
	
}