package com.trip.repository.Planner;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.trip.entity.Planner.PlanDetailEntity;

@Repository
public interface PlanDetailRepository extends JpaRepository<PlanDetailEntity, Long>{

}
