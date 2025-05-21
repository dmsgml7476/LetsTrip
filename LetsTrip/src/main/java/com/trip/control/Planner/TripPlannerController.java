package com.trip.control.Planner;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.trip.config.auth.CustomUserDetails;
import com.trip.dto.Planner.PlaceSelectDto;
import com.trip.dto.Planner.RegionSelectDto;
import com.trip.entity.Member.UserEntity;
import com.trip.entity.Planner.PlaceEntity;
import com.trip.entity.Planner.RegionEntity;
import com.trip.entity.Planner.VehicleEntity;
import com.trip.repository.Planner.PlaceRepository;
import com.trip.repository.Planner.RegionRepository;
import com.trip.service.Planner.PlanService;
import com.trip.service.Planner.TripPlanService;

import jakarta.servlet.http.HttpSession;

@Controller
public class TripPlannerController {
   
   @Autowired
   private PlaceRepository placeRepository;
   
   @Autowired
   private PlanService planService;
   
   @Autowired
   private RegionRepository regionRepository;
   
   @Autowired
   private TripPlanService tripPlanService; 
   
   @GetMapping("/tripMain")
   public String tripMain(@AuthenticationPrincipal CustomUserDetails userDetails, Model model) {
      
      return "planner/tripMain";
   }
   
   
   //여행 지역, 여행 인원, 교통수단 선택
   @GetMapping("/tripBasicOption")//사용자가 요청한 주소
   public String tripBasicOption(@RequestParam(name="startDate") String startDate,
         @RequestParam(name="endDate") String endDate, Model model, HttpSession session) {
      session.setAttribute("startDate",startDate);
      session.setAttribute("endDate",endDate);
      List<RegionSelectDto> regionList = regionRepository.findAll()
            .stream()
            .map(RegionSelectDto::from)
            .collect(Collectors.toList());
      
      model.addAttribute("regionList",regionList);
      model.addAttribute("vehicleType",planService.allVehicleName());
      
      System.out.println("시작날짜들어옴 : " + startDate );
      System.out.println("끝나는 날짜 들어옴: " + endDate);
      
      return "planner/tripBasicOption";
      
   }
   @GetMapping("/tripDateOption")
   public String tripDateOption(Model model) {
      return "planner/tripDateOption";
   }
   
   @GetMapping("/placeListOption")
   public String placeListOption(HttpSession session, Model model) {
       Long regionId = (Long) session.getAttribute("regionId");
       Long vehicleId = (Long) session.getAttribute("vehicleId");
       
       // 날짜 차이 계산
       String startDateStr = (String) session.getAttribute("startDate");
       String endDateStr = (String) session.getAttribute("endDate");

       if (startDateStr != null && endDateStr != null) {
           LocalDate startDate = LocalDate.parse(startDateStr);
           LocalDate endDate = LocalDate.parse(endDateStr);
           long days = ChronoUnit.DAYS.between(startDate, endDate) + 1; // +1일 포함
           model.addAttribute("tripDays", days);
       }

       if (regionId != null && vehicleId != null) {
           RegionEntity region = tripPlanService.findRegionById(regionId);
           VehicleEntity vehicle = tripPlanService.findVehicleById(vehicleId);

           model.addAttribute("region", region);
           model.addAttribute("vehicle", vehicle);
       }

       List<PlaceSelectDto> dtoList = placeRepository.findAll().stream()
             .filter(p->p.getRegion().getRegionId().equals(regionId))
               .map(p -> new PlaceSelectDto(
                       p.getPlaceId(),
                       p.getPlaceName(),
                       p.getCategory().getCategoryId()
               ))
               .collect(Collectors.toList());

       model.addAttribute("placeType", dtoList);
       
       System.out.println("지역아이디:" + regionId);
       System.out.println("차량수단:"+vehicleId);

       return "planner/placeListOption";
   }

   
   @PostMapping("/tripStep2")
   public String tripStep2(@RequestParam(name="regionId") Long regionId, @RequestParam(name="vehicleId") Long vehicleId, HttpSession session, Model model) {
      

      session.setAttribute("regionId", regionId);
      session.setAttribute("vehicleId", vehicleId);
      

      return "redirect:/placeListOption";
   }
      
   
   
   
   
   @GetMapping("/placeResult")
   public String placeResult(@RequestParam("placeId") Long placeId, Model model) {
   
      
      return "planner/placeResult";
   }
   
   @GetMapping("/finalPlan")
   public String finalPlan(Model model) {
      
      return "planner/finalPlan";
   }
   
   @GetMapping("/placeDetail")
   public String placeDetail(@RequestParam("placeId") Long placeId, 
                       @RequestParam("selectedDay") int selectedDay, Model model) {
      PlaceEntity place = placeRepository.findById(placeId)
            .orElseThrow(() -> new IllegalAccessError("해당 장소를 찾을 수 없습니다"));
      model.addAttribute("place", place);
       model.addAttribute("selectedDay", selectedDay);
      
      model.addAttribute("placeName",place.getPlaceName());
      model.addAttribute("placeAddress",place.getPlaceAddress());
      model.addAttribute("placeInfo",place.getPlaceInfo());
      
      return "planner/placeDetail";
   }

   }

   
   