package com.trip.control.Planner;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.trip.config.auth.CustomUserDetails;

@Controller
public class TripPlanChangeController {

	@GetMapping("/planchange1")
	public String planchange1(@AuthenticationPrincipal CustomUserDetails userDetails, Model model) {
		return "planner/planchange1";
	}
	@GetMapping("/planchange2")
	public String planchange2(@AuthenticationPrincipal CustomUserDetails userDetails, Model model) {
		return "planner/planchange2";
	}

	@GetMapping("/restaurantInfo")
	public String restaurantInfo(@AuthenticationPrincipal CustomUserDetails userDetails, Model model) {
		return "planner/restaurantInfo";
	}
	
}
