package com.trip.config.auth;


import java.util.Map;



import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.trip.repository.Member.UserDetailRepository;
import com.trip.service.Member.EmailAuthService;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth/email")
@RequiredArgsConstructor
public class EmailAuthController {
	
	 private final EmailAuthService emailAuthService;
	 private final UserDetailRepository userDetailRepository;

	    @PostMapping("/send")
	    public Map<String, String> sendEmailCode(@RequestBody Map<String, String> request, HttpSession session) {
	        String email = request.get("email");
	        String context = request.get("context");
	        
	        boolean exists=userDetailRepository.existsByEmail(email);
	        
	        if("signup".equals(context)) {
	        	if (exists) {
	        		return Map.of("status", "exists"); // 이미 존재 -> 회원가입실패
	        	}
	        } else if ("find".equals(context)) {
	        	if(!exists) {
	        		return Map.of("status", "not_found"); // 존재안함 -> 아이디 찾기불가
	        	} 
	        } else if ("pw".equals(context)) {
	        	if (!exists) {
	        		return Map.of("status", "not_found");
	        	}
	        } else {
	        	return Map.of("status", "invalid_context"); // 방어로직?
	        }
	        
	        boolean success = emailAuthService.sendAuthCode(email, session);

	        return Map.of("status", success ? "sent" : "fail");
	    }

	    @PostMapping("/verify")
	    public Map<String, Boolean> verifyCode(@RequestBody Map<String, String> request, HttpSession session) {
	        String code = request.get("code");
	        boolean success = emailAuthService.verifyCode(code, session);
	        return Map.of("success", success);
	    }
	
}
