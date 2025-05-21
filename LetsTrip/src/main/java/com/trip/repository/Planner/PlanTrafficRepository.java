package com.trip.repository.Planner;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.trip.entity.Planner.PlanTrafficEntity;

@Repository
public interface PlanTrafficRepository extends JpaRepository<PlanTrafficEntity, Long>{

}
