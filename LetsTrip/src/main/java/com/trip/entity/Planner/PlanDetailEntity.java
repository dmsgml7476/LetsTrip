package com.trip.entity.Planner;

import java.time.LocalDateTime;

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
public class PlanDetailEntity {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long planDetailId;
	private Long planDateId;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="place_id", nullable=false)
	private PlaceEntity place;
	private LocalDateTime visitTime;
	private LocalDateTime endTime;
	private int theDayOrder;
}