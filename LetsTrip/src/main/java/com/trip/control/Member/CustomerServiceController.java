package com.trip.control.Member;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

import com.trip.config.auth.CustomUserDetails;
import com.trip.dto.Member.CustomerServiceDto;
import com.trip.entity.Member.CustomerServiceEntity;
import com.trip.repository.Member.CustomerServiceRepository;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class CustomerServiceController {

	private final CustomerServiceRepository customerServiceRepository;
	
	@PostMapping("/customer")
	public String submitCustomerService(CustomerServiceDto dto, @AuthenticationPrincipal CustomUserDetails userDetails) {
		CustomerServiceEntity entity = CustomerServiceEntity.builder()
				.user(userDetails.getUser())
				.csOption(dto.getCsOption())
				.category(dto.getCategory())
				.questionText(dto.getQuestionText())
				.build();
		
		customerServiceRepository.save(entity);
		
		return "redirect:/";
	}
	
}
