package com.trip.control.Member;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.trip.config.auth.CustomUserDetails;
import com.trip.constant.Member.NotificationType;
import com.trip.dto.Member.RecommendStoryDto;
import com.trip.entity.Member.UserDetailEntity;
import com.trip.entity.Member.UserEntity;
import com.trip.entity.Member.UserHashtagEntity;
import com.trip.entity.Member.WebNotificationEntity;
import com.trip.entity.Planner.TripPlanEntity;
import com.trip.repository.Member.UserAlertSettingRepository;
import com.trip.repository.Member.UserRepository;
import com.trip.repository.Member.WebNotificationRepository;
import com.trip.repository.Planner.TripPlanRepository;
import com.trip.service.Member.RecommendStoryService;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;



@Controller
@RequiredArgsConstructor
public class MainController {
	
	
	private final TripPlanRepository tripPlanRepository;
	private final UserRepository userRepository;
	private final RecommendStoryService recommendStoryService;
	private final WebNotificationRepository webNotificationRepository;
    private final UserAlertSettingRepository userAlertSettingRepository;

	@GetMapping("/")
	public String showMainPage(Model model, @AuthenticationPrincipal CustomUserDetails userDetails, HttpServletRequest request) {
		model.addAttribute("requestUri", request.getRequestURI());
		List<RecommendStoryDto> storyList;
	    
	    if (userDetails != null) {
	        Long userId = userDetails.getUser().getId();
	        storyList = recommendStoryService.getRecommendedStoriesForUser(userId);

	        // 여기서 Fetch Join 사용!
	        UserEntity user = userRepository.findWithHashtagsById(userId)
	                .orElseThrow(() -> new IllegalArgumentException("User not found"));

	        UserDetailEntity detail = user.getUserDetail();
	        model.addAttribute("userDetail", detail);
	        model.addAttribute("nickname", user.getNickname());

	        List<String> userHashtags = user.getUserHashtags().stream()
	                .map(uh -> uh.getHashtags().getHashtag())
	                .limit(3)
	                .toList();

	        model.addAttribute("userHashtags", userHashtags);
	    
	        List<TripPlanEntity> upcomingTrips = tripPlanRepository.findUpcomingTripsByUserId(userId);
	        if(!upcomingTrips.isEmpty()) {
	        	TripPlanEntity nextTrip = upcomingTrips.get(0);
	        	model.addAttribute("tripTitle", nextTrip.getPlanTitle());
	        	
	        	long dDay=ChronoUnit.DAYS.between(LocalDate.now(), nextTrip.getTripStartDate());
	        	model.addAttribute("dDay", dDay);
	        }
	        
//	        알림 관련
	        
	        
	        userAlertSettingRepository.findByUserId(userId).ifPresentOrElse(setting -> {
	            List<NotificationType> allowedTypes = new ArrayList<>();

	            if (setting.isCommAlert()) {
	                allowedTypes.add(NotificationType.STORY);
	                allowedTypes.add(NotificationType.CUSTOMER_REPLY);
	            }

	            if (setting.isTripAlert()) {
	                allowedTypes.add(NotificationType.TRIP_START);
	            }

	            if (!allowedTypes.isEmpty()) {
	                int unreadCount = webNotificationRepository
	                        .countByUserIdAndIsReadFalseAndTypeIn(userId, allowedTypes);
	                model.addAttribute("unreadNotificationCount", unreadCount);
	                
	                List<WebNotificationEntity> notifications=webNotificationRepository
	                		.findTop5ByUserIdAndIsReadFalseAndTypeInOrderByCreateAtDesc(userId, allowedTypes);
	                		model.addAttribute("notifications", notifications);
	            } else {
	                model.addAttribute("unreadNotificationCount", 0);
	                model.addAttribute("notifications", List.of()); 
	            }
	        }, () -> {
	            model.addAttribute("unreadNotificationCount", 0);
	            model.addAttribute("notifications", List.of()); 
	        });
	    } else {
	    	storyList = recommendStoryService.getRecommendedStoriesForGuest();
	    }
	    
	    model.addAttribute("storyList", storyList);

	    return "index";
	}
	    

	    
}
