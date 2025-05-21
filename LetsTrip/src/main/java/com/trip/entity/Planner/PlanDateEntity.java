package com.trip.entity.Planner;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class PlanDateEntity {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long planDateId;
	@OneToOne
	@JoinColumn(name="plan_id")
	private TripPlanEntity tripPlan;
	private int planOrder;
	private LocalDate planDate;
	private String memo;

}
