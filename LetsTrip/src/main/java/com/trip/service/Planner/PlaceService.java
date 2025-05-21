package com.trip.service.Planner;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.trip.dto.Planner.PlaceSelectDto;
import com.trip.entity.Planner.PlaceEntity;
import com.trip.entity.Planner.RegionEntity;
import com.trip.repository.Planner.PlaceRepository;
import com.trip.repository.Planner.RegionRepository;

@Service//서비스
public class PlaceService {
	private final RegionRepository regionRepository;
    private final PlaceRepository placeRepository;

    public PlaceService(RegionRepository regionRepository, PlaceRepository placeRepository) {
        this.regionRepository = regionRepository;
        this.placeRepository = placeRepository;
    }

    public List<PlaceSelectDto> getPlacesByRegionName(String regionName) {
        RegionEntity region = regionRepository.findByRegionName(regionName)
            .orElseThrow(() -> new IllegalArgumentException("지역이 없습니다."));

        List<PlaceEntity> places = placeRepository.findByRegion(region);

        return places.stream()
            .map(p -> new PlaceSelectDto(p.getPlaceId(), p.getPlaceName(), p.getCategory().getCategoryId()))
            .collect(Collectors.toList());
    }
}
