package com.trip.control.Planner;

import java.util.List;
import java.util.stream.Collectors;

import javax.swing.plaf.synth.Region;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.trip.dto.Planner.PlaceSelectDto;
import com.trip.entity.Planner.PlaceEntity;
import com.trip.repository.Planner.PlaceRepository;
import com.trip.repository.Planner.RegionRepository;
import com.trip.service.Planner.PlaceService;

@Controller
public class PlaceSelectController {


	private final RegionRepository regionRepository;
	
	
	private final PlaceRepository placeRepository;
	
	private final PlaceService placeService;
	
	public PlaceSelectController(RegionRepository regionRepository,
										PlaceRepository placeRepository,
										PlaceService placeService) {
		this.regionRepository = regionRepository;
		this.placeRepository = placeRepository;
		this.placeService = placeService;
	}
	


@GetMapping("/placeListOptionByRegion")
public String placeListOption(@RequestParam(value="subRegion",required=false) String regionName, Model model) {
    // 지역 엔티티 조회
	if(regionName == null || regionName.isEmpty()) {
		throw new IllegalArgumentException("subRegion 파라미터가 없습니다. ");
	}
	
	List<PlaceSelectDto> dtoList = placeService.getPlacesByRegionName(regionName);
    model.addAttribute("placeType", dtoList);
    return "planner/placeListOption";
	}
}




