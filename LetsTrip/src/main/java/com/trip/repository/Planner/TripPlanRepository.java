package com.trip.repository.Planner;

import java.util.List;

import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.trip.entity.Planner.TripPlanEntity;

@Repository
public interface TripPlanRepository extends JpaRepository<TripPlanEntity, Long>{
	
	@Query("SELECT t FROM TripPlanEntity t WHERE t.user.id = :userId")
	public List<TripPlanEntity> findUpcomingTripsByUserId(@Param("userId") Long userId);

	public List<TripPlanEntity> findByUser_Id(Long userId);
}
