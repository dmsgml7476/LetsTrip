package com.trip.service.Planner;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.trip.entity.Planner.PlaceEntity;
import com.trip.entity.Planner.PlanTrafficEntity;
import com.trip.entity.Planner.RegionEntity;
import com.trip.entity.Planner.VehicleEntity;
import com.trip.repository.Planner.PlaceRepository;
import com.trip.repository.Planner.PlanTrafficRepository;
import com.trip.repository.Planner.RegionRepository;
import com.trip.repository.Planner.VehicleRepository;

import lombok.RequiredArgsConstructor;

@Service
public class PlanService {
	@Autowired
	private RegionRepository regionRepository;//지역 레포지토리
	
	@Autowired
	private VehicleRepository vehicleRepository;//교통수단 레포지토리
	
	@Autowired
	private PlaceRepository placeRepository;
	
	//지역 전체 가져오는 메서드
	public List<RegionEntity> allRegionName(){
	List<RegionEntity> regionName = regionRepository.findAll();
	
	return regionName;
	}
	//차량 전체 기져오는 메서드
	public List<VehicleEntity> allVehicleName(){
	List<VehicleEntity> vehicleName = vehicleRepository.findAll();
	
	
	return vehicleName;
	}

	}

		
		
	
	

