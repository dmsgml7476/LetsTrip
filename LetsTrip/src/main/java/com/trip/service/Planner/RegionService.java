package com.trip.service.Planner;

import org.springframework.stereotype.Service;

import com.trip.repository.Planner.PlaceRepository;
import com.trip.repository.Planner.RegionRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RegionService {
	private final RegionRepository regionRepository;
}
