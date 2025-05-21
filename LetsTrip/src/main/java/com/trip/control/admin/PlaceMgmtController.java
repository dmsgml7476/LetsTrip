package com.trip.control.admin;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.trip.dto.admin.PlaceListDto;
import com.trip.dto.admin.PlaceSaveDto;
import com.trip.dto.admin.RegionDto;
import com.trip.entity.Planner.PlaceEntity;
import com.trip.entity.Planner.RegionEntity;
import com.trip.repository.Planner.PlaceRepository;
import com.trip.repository.Planner.RegionRepository;
import com.trip.service.admin.PlaceMgmtService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
public class PlaceMgmtController {
	
	private final RegionRepository regionRepository;
	private final PlaceRepository placeRepository;
	private final PlaceMgmtService placeMgmtService;

	
	@GetMapping("/placeSave")
	public String placeSavePage(Model model) {
		List<RegionDto> regionList=regionRepository.findAll()
				.stream()
				.map(RegionDto::from)
				.collect(Collectors.toList());
		
		model.addAttribute("regionList", regionList);
		return "admin/page/placeSave";
	}
	
	@PostMapping("/place/save")
	public String savePlace(@ModelAttribute PlaceSaveDto dto) {
		placeMgmtService.savePlace(dto);
		
		return "redirect:/admin/placeSave";
	}
	
	@GetMapping("/placeMgmt/{category}")
	public String placeMgmtPage(@PathVariable("category") String category, 
								@PageableDefault(size = 10) Pageable pageable,
	                            @RequestParam(value = "upperRegion", required = false) String upperRegion,
	                            @RequestParam(value = "regionId", required = false) Long regionId,
	                            @RequestParam(value = "keyword", required = false) String keyword,
	                            Model model) {

	    Long categoryId = switch (category) {
	        case "tourSpot" -> 1L;
	        case "hotel" -> 2L;
	        case "restaurant" -> 3L;
	        default -> throw new IllegalArgumentException("잘못된 카테고리입니다.");
	    };

	    //서비스에서 필터링 처리
	    Page<PlaceListDto> placePage = placeMgmtService.getFilteredPlaces(categoryId, upperRegion, regionId, keyword, pageable);

	    List<RegionDto> regionList = regionRepository.findAll()
	            .stream()
	            .map(RegionDto::from)
	            .collect(Collectors.toList());
	    
	    model.addAttribute("placeList", placePage.getContent());

	    model.addAttribute("placePage", placePage);
	    model.addAttribute("regionList", regionList);
	    model.addAttribute("category", category);
	    model.addAttribute("categoryName", getCategoryName(category));
	    model.addAttribute("upperRegion", upperRegion); // 선택 유지용
	    model.addAttribute("regionId", regionId);       // 선택 유지용
	    
	    int currentPage = placePage.getNumber(); // 0부터 시작
	    int totalPages = placePage.getTotalPages();

	    int pageGroupSize = 5;
	    int startPage = (currentPage / pageGroupSize) * pageGroupSize + 1;
	    int endPage = Math.min(startPage + pageGroupSize - 1, totalPages);

	    model.addAttribute("currentPage", currentPage);
	    model.addAttribute("totalPages", totalPages);
	    model.addAttribute("startPage", startPage);
	    model.addAttribute("endPage", endPage);
	    
	    System.out.println("✔️ upperRegion = " + upperRegion);
	    System.out.println("✔️ regionId = " + regionId);
	    
	    return "/admin/page/placeMgmt";
	}
	
	
	private String getCategoryName(String category) {
	    return switch (category) {
	        case "tourSpot" -> "관광지";
	        case "hotel" -> "숙소";
	        case "restaurant" -> "식당";
	        default -> "";
	    };
	}
	
}
