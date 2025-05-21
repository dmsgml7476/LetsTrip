package com.trip.repository.Planner;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.trip.entity.Planner.PlanDateEntity;

@Repository
public interface PlanDateRepository extends JpaRepository<PlanDateEntity, Long>{

}
