package com.trip.control.Planner;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/planner")
public class PlannerController {

	@GetMapping("/finalPlan")
	public String finalPlanPage() {
		return "planner/finalPlan";
	}
}
