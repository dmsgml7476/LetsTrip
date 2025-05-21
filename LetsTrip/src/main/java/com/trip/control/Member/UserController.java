package com.trip.control.Member;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.trip.dto.Member.UserSignUpDto;
import com.trip.entity.Member.HashtagsEntity;
import com.trip.entity.Member.UserAlertSettingEntity;
import com.trip.repository.Member.HashtagsRepository;
import com.trip.service.Member.UserService;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;


@Controller
@RequiredArgsConstructor
public class UserController {
	
	private final UserService userService;
	private final HashtagsRepository hashtagsRepository;
	
    @GetMapping("/login")
    public String login(@RequestParam( name = "pwChanged", required = false) String pwChanged, @RequestParam(value = "redirect", required = false) String redirect, HttpSession session,Model model) {
    	 if ("true".equals(pwChanged)) {
    	        model.addAttribute("pwChangedMsg", "비밀번호가 성공적으로 변경되었습니다.");
    	    }
    	 if (redirect != null) {
    	        session.setAttribute("redirectAfterLogin", redirect);
    	    }
    	return "member/signIn";
    }
    
    // 로그인 실패시
    
    @GetMapping("/login/error")
    public String loginfail(Model model) {
    	model.addAttribute("loginErrorMsg", "아이디와 비밀번호가 일치하지 않습니다");
    	return "member/signIn";
    }
    
    @GetMapping("/terms")
    public String join() {
    	return "member/terms";
    }
    
    @GetMapping("/signUp")
    public String joinform(Model model) {
    	model.addAttribute("userSignUpDto", new UserSignUpDto());
    	List<HashtagsEntity> generalTags = hashtagsRepository.findByIsMbtiFalse();
    	model.addAttribute("hashtags", generalTags);

    	List<HashtagsEntity> mbtiTags = hashtagsRepository.findByIsMbtiTrue();
    	model.addAttribute("mbtiTags", mbtiTags);
    	return "member/signUp";
    }
    
    @PostMapping("/signUp")
    public String signup(@ModelAttribute UserSignUpDto dto, @RequestParam(name = "alertAgree", required = false) String alertAgree, HttpSession session) {
        Boolean isVerified = (Boolean) session.getAttribute("authSuccess");
        
        
        if (isVerified == null || !isVerified) {
            throw new IllegalStateException("이메일 인증이 완료되지 않았습니다.");
        }
    	
    	boolean agree = "true".equals(alertAgree);
    	
    	UserAlertSettingEntity setting = UserAlertSettingEntity.builder()
    			.commAlert(agree)
    			.tripAlert(agree)
    			.build();
    	
    	userService.register(dto, setting);

        return "redirect:/login";
    }
    
    @GetMapping("/api/check-id")
    @ResponseBody
    public Map<String, Boolean> checkId(@RequestParam("loginId") String loginId) {
        boolean exists = userService.existsByLoginId(loginId);
        return Map.of("available", !exists);
    }
    
    @GetMapping("/api/check-nickname")
    @ResponseBody
    public Map<String, Boolean> checkNickname(@RequestParam("nickname") String nickname) {
    	boolean exists = userService.existsByNickname(nickname);
    	return Map.of("available", !exists);
    }
    
    
    
}
