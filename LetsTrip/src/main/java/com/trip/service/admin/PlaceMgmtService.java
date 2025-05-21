package com.trip.service.admin;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.trip.dto.admin.CoordinateDto;
import com.trip.dto.admin.PlaceListDto;
import com.trip.dto.admin.PlaceSaveDto;
import com.trip.entity.Planner.CategoryEntity;
import com.trip.entity.Planner.PlaceEntity;
import com.trip.entity.Planner.RegionEntity;
import com.trip.repository.Planner.CategoryRepository;
import com.trip.repository.Planner.PlaceRepository;
import com.trip.repository.Planner.RegionRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PlaceMgmtService {

	
	private final PlaceRepository placeRepository;
	private final RegionRepository regionRepository;
	private final CategoryRepository categoryRepository;

	
    public void savePlace(PlaceSaveDto dto) {

        RegionEntity region = regionRepository.findById(dto.getRegionId())
                .orElseThrow(() -> new IllegalArgumentException("지역 없음"));

        CategoryEntity category = categoryRepository.findById(dto.getCategoryType().longValue())
                .orElseThrow(() -> new IllegalArgumentException("해당 카테고리가 존재하지 않습니다."));

        PlaceEntity place = PlaceEntity.of(dto, region, category);

        System.out.println("👉 입력 주소: " + dto.getPlaceAddress());

        // 위도/경도 관련 로직 제거됨

        placeRepository.save(place);
    }
    
    public Page<PlaceListDto> getFilteredPlaces(Long categoryId, String upperRegion, Long regionId, String keyword, Pageable pageable) {

        
        Page<PlaceEntity> entities;

        if (regionId != null) {
            // 지역 ID와 키워드 둘 다 있는 경우
            if (keyword != null && !keyword.isBlank()) {
                entities = placeRepository.findByCategoryAndRegionAndKeyword(categoryId, regionId, keyword, pageable);
            } else {
                entities = placeRepository.findByCategoryAndRegion(categoryId, regionId, pageable);
            }

        } else if (upperRegion != null && !upperRegion.isBlank()) {
            // 상위 지역과 키워드 있는 경우
            if (keyword != null && !keyword.isBlank()) {
                entities = placeRepository.findByCategoryAndUpperRegionAndKeyword(categoryId, upperRegion, keyword, pageable);
            } else {
                entities = placeRepository.findByCategoryAndUpperRegion(categoryId, upperRegion, pageable);
            }

        } else {
            // 카테고리만 필터링하거나 + 키워드만 있는 경우
            if (keyword != null && !keyword.isBlank()) {
                entities = placeRepository.findByCategoryAndKeyword(categoryId, keyword, pageable);
            } else {
                entities = placeRepository.findByCategory_CategoryId(categoryId, pageable);
            }
        }

        return entities.map(PlaceListDto::from);
//        return entities.stream()
//                .map(PlaceListDto::from)
//                .toList();
    }

}
