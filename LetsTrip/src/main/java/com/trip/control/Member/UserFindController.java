package com.trip.control.Member;


import java.util.Map;
import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.trip.entity.Member.UserDetailEntity;
import com.trip.repository.Member.UserDetailRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;


@Controller
@RequiredArgsConstructor

public class UserFindController {
	
    private final UserDetailRepository userDetailRepository;
    private final PasswordEncoder passwordEncoder;

    
    @GetMapping("/api/find-id")
    @ResponseBody
    public Map<String, String> findLoginId(@RequestParam("email") String email) {
        Optional<UserDetailEntity> userOpt = userDetailRepository.findByEmail(email);
        if (userOpt.isPresent()) {
            return Map.of("loginId", userOpt.get().getUser().getLoginId());
        } else {
            return Map.of("loginId", "");
        }
    }
    
    @PostMapping("/loginEditPw")
    @Transactional
    public String changePassword(@RequestParam("email") String email,
            @RequestParam("newPassword") String newPassword) {
    	Optional<UserDetailEntity> userOpt = userDetailRepository.findByEmail(email);
    	if (userOpt.isPresent()) {
    		
    		UserDetailEntity userDetail = userOpt.get();
    	userDetail.getUser().setPassword(passwordEncoder.encode(newPassword));
    	return "redirect:/login?pwChanged"; // 성공 후 로그인 페이지 등으로 이동
    	} else {
    	return "redirect:/login?error=emailNotFound";
    	}
}
    
    
    

}
