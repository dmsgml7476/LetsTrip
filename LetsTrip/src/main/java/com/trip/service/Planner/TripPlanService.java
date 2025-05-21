package com.trip.service.Planner;

import org.springframework.stereotype.Service;

import com.trip.entity.Planner.RegionEntity;
import com.trip.entity.Planner.VehicleEntity;
import com.trip.repository.Planner.RegionRepository;
import com.trip.repository.Planner.VehicleRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TripPlanService {
	private final RegionRepository regionRepository;
	private final VehicleRepository vehicleRepository;
	
	public RegionEntity findRegionById(Long regionId) {
		return regionRepository.findById(regionId)
				.orElseThrow(()->new IllegalArgumentException("존재하지 않는 지역입니다."));
	}
	
	public VehicleEntity findVehicleById(Long vehicleId) {
		return vehicleRepository.findById(vehicleId)
				.orElseThrow(()->new IllegalArgumentException("존재하지 않는 교통수단입니다."));
	}
}
