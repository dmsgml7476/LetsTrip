package com.trip.control.Member;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.trip.config.auth.CustomUserDetails;
import com.trip.dto.Member.InfoChangeDto;
import com.trip.dto.Member.LikedStoryDto;
import com.trip.dto.Member.MyCsListDto;
import com.trip.dto.Member.PasswordChangeDto;
import com.trip.entity.Lets.StoryEntity;
import com.trip.entity.Member.HashtagsEntity;
import com.trip.entity.Member.UserAlertSettingEntity;
import com.trip.entity.Member.UserDetailEntity;
import com.trip.entity.Member.UserEntity;
import com.trip.entity.Member.UserHashtagEntity;
import com.trip.entity.Member.UserMainStoryEntity;
import com.trip.entity.Planner.TripPlanEntity;
import com.trip.repository.Lets.StoryRepository;
import com.trip.repository.Member.HashtagsRepository;
import com.trip.repository.Member.UserAlertSettingRepository;
import com.trip.repository.Member.UserDetailRepository;
import com.trip.repository.Member.UserMainStoryRepository;
import com.trip.repository.Member.UserRepository;
import com.trip.repository.Planner.TripPlanRepository;
import com.trip.service.Member.MyPageService;
import com.trip.service.Member.UserService;
import com.trip.service.admin.CustomerServiceAnswerService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/mypage")
public class MyPageController {

	private final UserDetailRepository userDetailRepository;
	private final UserRepository userRepository;
	private final UserMainStoryRepository userMainStoryRepository;
	private final StoryRepository storyRepository;
	private final MyPageService myPageService;
	private final HashtagsRepository hashtagsRepository;
	private final UserAlertSettingRepository userAlertSettingRepository;
	private final PasswordEncoder passwordEncoder;
	private final UserService userService;
	private final TripPlanRepository tripPlanRepository;
	
	@GetMapping("/main")
	public String myPage(@AuthenticationPrincipal CustomUserDetails userDetails, Model model, HttpServletRequest request) {
		
		model.addAttribute("requestUri", request.getRequestURI());
		if (userDetails != null) {	
			UserEntity user= userDetails.getUser();
			model.addAttribute("user", user);
			Long userId	= user.getId()	;
			
			user = userRepository.findWithHashtagsById(userId)
	                .orElseThrow(() -> new IllegalArgumentException("User not found"));
			
			List<String> userHashtags = user.getUserHashtags().stream()
	                .map(uh -> uh.getHashtags().getHashtag())
	                .limit(3)
	                .toList();

	        model.addAttribute("userHashtags", userHashtags);
			
			UserDetailEntity userDetail=user.getUserDetail();
			model.addAttribute("userDetail", userDetail);
			model.addAttribute("nickname", user.getNickname());
			
			// 대표스토리
			
			List<UserMainStoryEntity> mainStories = userMainStoryRepository.findByUserId(userId);
			
			Map<String, Long> mainStoryIds = new HashMap<>();
			Map<String, String> mainStoryImages= new HashMap<>();
			
			for(UserMainStoryEntity mainStory : mainStories) {
				StoryEntity story = storyRepository.findById(mainStory.getStoryId()).orElse(null);
			    if (story != null) {
			        String key = mainStory.getMainStoryNum().name();
			        mainStoryImages.put(key, story.getImageUrl());
			        mainStoryIds.put(key, story.getStoryId());
			    }
			}
			

			model.addAttribute("mainStoryIds", mainStoryIds);		
			model.addAttribute("mainStoryImages", mainStoryImages);
			
			// 내가 좋아요 누른 스토리 목록 추가 
			
			List<LikedStoryDto> likedStories = myPageService.getLikedStories(userId);
		        model.addAttribute("likeStoryList", likedStories);
		        
		        
		     // 캘린더
		        
		    List<TripPlanEntity> plans = tripPlanRepository.findByUser_Id(userId);
		    model.addAttribute("tripPlans", plans);
			
			return "member/myPage";
		}
		
		return "index";
	}
	
	
	@PostMapping("/profile/upload")
	public String updateProfileImg(@AuthenticationPrincipal CustomUserDetails userDetails, @RequestParam("profileImg") MultipartFile profileImg) throws IOException {
		UserEntity user = userDetails.getUser();
		
		String fileName = UUID.randomUUID().toString() + "_" + profileImg.getOriginalFilename();
		Path savePath = Paths.get("C:/trip-uploads/profile", fileName);
		Files.copy(profileImg.getInputStream(), savePath);
		
		UserDetailEntity userDetail=user.getUserDetail();
		userDetail.setProfileImg(fileName);
		
		userDetailRepository.save(userDetail);
		
		System.out.println("▶ 파일 이름: " + fileName);
		System.out.println("▶ 저장 경로: " + savePath.toAbsolutePath());
		System.out.println("▶ 이미지 저장 완료");
		
		return "redirect:/mypage/edit";
	}
	
	
	@GetMapping("/edit")
	public String editForm(@AuthenticationPrincipal CustomUserDetails userDetails, Model model) {
		
		if (userDetails != null) {
			Long userId = userDetails.getUser().getId();

	        // 지연로딩 방지: 해시태그까지 즉시 로딩
	        UserEntity user = userRepository.findWithHashtagsById(userId)
	                .orElseThrow(() -> new IllegalArgumentException("User not found"));

	        model.addAttribute("user", user);

	        UserDetailEntity userDetail = user.getUserDetail();
	        model.addAttribute("userDetail", userDetail);
	        model.addAttribute("nickname", user.getNickname());
	        
	        
	        // 알림 설정값 추가
	        
	        UserAlertSettingEntity alertSetting = userAlertSettingRepository.findByUserId(userId)
	                .orElseThrow(() -> new IllegalArgumentException("알림 설정 정보 없음"));
	        model.addAttribute("commAlert", alertSetting.isCommAlert());
	        model.addAttribute("tripAlert", alertSetting.isTripAlert());
	        
	        //해시태그
	        List<HashtagsEntity> generalTags = hashtagsRepository.findByIsMbtiFalse();
	        model.addAttribute("hashtags", generalTags);

	        List<HashtagsEntity> mbtiTags = hashtagsRepository.findByIsMbtiTrue();
	        model.addAttribute("mbtiTags", mbtiTags);

	        List<Long> selectedHashtagIds = user.getUserHashtags().stream()
	                .map(uh -> uh.getHashtags().getId())
	                .toList();

	        model.addAttribute("selectedHashtagIds", selectedHashtagIds);
	        
	        // mbti 추출
	        
	        String mbti = user.getUserHashtags().stream()
	        		.map(uh -> uh.getHashtags().getHashtag())
	        		.filter(this::isMbti)
	        		.findFirst()
	        		.orElse("MBTI 없음");
	        
	        model.addAttribute("mbti", mbti);

	        return "/member/edit";
		}
		return "index";
	}
	
	private boolean isMbti(String tag) {
        List<String> mbtiTypes = List.of(
            "ISTJ", "ISFJ", "INFJ", "INTJ",
            "ISTP", "ISFP", "INFP", "INTP",
            "ESTP", "ESFP", "ENFP", "ENTP",
            "ESTJ", "ESFJ", "ENFJ", "ENTJ"
        );
        return mbtiTypes.contains(tag.toUpperCase());
    }
	
	
//	mbit 수정 
	
	@PostMapping("/edit/mbti")
	public String updateMbti(@AuthenticationPrincipal CustomUserDetails userDetails,
	                         @RequestParam("mbti") String newMbti) {

		 Long userId = userDetails.getUser().getId();

		    UserEntity user = userRepository.findWithHashtagsById(userId)
		            .orElseThrow(() -> new IllegalArgumentException("User not found"));

		    HashtagsEntity mbtiTag = hashtagsRepository.findByHashtag(newMbti)
		            .orElseThrow(() -> new IllegalArgumentException("해당 MBTI 해시태그 없음: " + newMbti));

		    boolean updated = false;

		    for (UserHashtagEntity uh : user.getUserHashtags()) {
		        if (isMbti(uh.getHashtags().getHashtag())) {
		            uh.setHashtags(mbtiTag);  // 기존 MBTI만 바꿔줌
		            updated = true;
		            break;
		        }
		    }

		    if (!updated) {
		        user.getUserHashtags().add(new UserHashtagEntity(user, mbtiTag));
		    }

		    userRepository.save(user);

	    return "redirect:/mypage/edit"; 
	}
	
	
	// 해시태그 실시간  수정
	
	
	@PostMapping("/hashtag")
	@ResponseBody
	public Map<String, Object> addHashtag(@AuthenticationPrincipal CustomUserDetails userDetails,
	                                      @RequestBody Map<String, Long> request) {
	    Long hashtagId = request.get("hashtagId");
	    Long userId = userDetails.getUser().getId();

	    HashtagsEntity tag = hashtagsRepository.findById(hashtagId)
	            .orElseThrow(() -> new IllegalArgumentException("해시태그 없음"));

	    UserEntity user = userRepository.findWithHashtagsById(userId)
	            .orElseThrow(() -> new IllegalArgumentException("유저 없음"));

	    boolean exists = user.getUserHashtags().stream()
	        .anyMatch(uh -> uh.getHashtags().getId().equals(hashtagId));
	    if (!exists) {
	        user.getUserHashtags().add(new UserHashtagEntity(user, tag));
	        userRepository.save(user);
	    }

	    return Map.of("success", true);
	}

	@PostMapping("/hashtag/delete")
	@ResponseBody
	public Map<String, Object> removeHashtag(@AuthenticationPrincipal CustomUserDetails userDetails,
	                                         @RequestBody Map<String, Long> request) {
	    Long hashtagId = request.get("hashtagId");
	    Long userId = userDetails.getUser().getId();

	    UserEntity user = userRepository.findWithHashtagsById(userId)
	            .orElseThrow(() -> new IllegalArgumentException("유저 없음"));

	    user.getUserHashtags().removeIf(uh -> uh.getHashtags().getId().equals(hashtagId));
	    userRepository.save(user);

	    return Map.of("success", true);
	}
	
	@PostMapping("/alerts/update")
	@ResponseBody
	public Map<String, Object> updateAlertSetting(@AuthenticationPrincipal CustomUserDetails userDetails,
	                                              @RequestBody Map<String, Object> request) {
	    String alertType = (String) request.get("alertType");  // "TRIP" or "COMM"
	    boolean agreed = (boolean) request.get("agreed");

	    Long userId = userDetails.getUser().getId();

	    UserAlertSettingEntity setting = userAlertSettingRepository.findByUserId(userId)
	        .orElseThrow(() -> new IllegalArgumentException("알림 설정 정보 없음"));

	    if ("TRIP".equals(alertType)) {
	        setting.setTripAlert(agreed);
	    } else if ("COMM".equals(alertType)) {
	        setting.setCommAlert(agreed);
	    }

	    userAlertSettingRepository.save(setting);

	    return Map.of("success", true);
	}
	
	
	// 모달
	
	@PostMapping("/chkPw")
	@ResponseBody
	public Map<String, Boolean> checkPassword(@AuthenticationPrincipal CustomUserDetails userDetails,
	                                          @RequestBody Map<String, String> request) {
	    String inputPw = request.get("password");
	    String realPw = userDetails.getUser().getPassword();

	    boolean matches = passwordEncoder.matches(inputPw, realPw);
	    
	    System.out.println("비밀번호 체크" + matches);
	    return Map.of("success", matches);
	}
	
	// 비밀번호 수정
	
	@PostMapping("/editPw")
	public String changePassword(@Valid @ModelAttribute PasswordChangeDto dto,
								BindingResult bindingResult,
								@AuthenticationPrincipal CustomUserDetails userDetails) {
		if (userDetails == null) {
		    // 로그인 상태가 아니면 로그인 페이지로 리다이렉트 방어 코드
		    return "redirect:/login";
		}
		
		if(bindingResult.hasErrors()) {
			return "member/edit";
		}
		
		if (!dto.getNewPassword().equals(dto.getConfirmPassword())) {
	        bindingResult.rejectValue("confirmPassword", "notMatch", "비밀번호가 일치하지 않습니다.");
	        return "member/edit";
	    }
		
		userService.updatePassword(userDetails.getUser(), dto.getNewPassword());
		
		return "redirect:/mypage/edit?pwChanged=true";
	}
	
	// 회원정보 수정
	
	@PostMapping("/editInfo")
	public String changeInfo(@Valid @ModelAttribute InfoChangeDto dto,
			                BindingResult bindingResult, 
			                @AuthenticationPrincipal CustomUserDetails userDetails) {
		
		if(bindingResult.hasErrors()) {
			return "member/edit";
		}
		
		userService.changeUserInfo(userDetails.getUser().getId(), dto);
		
		return "redirect:/mypage/edit?infoChanged=true";
		
	}
	
	@GetMapping("/csMyList")
	public String myCsListView(@AuthenticationPrincipal CustomUserDetails userDetails,
			 @RequestParam(value = "page", defaultValue = "1") int page,
	                           Model model) {
	    Long userId = userDetails.getUser().getId();
	    int pageSize = 10;

	    Page<MyCsListDto> csPage = myPageService.getMyCsList(userId, page, pageSize);
	    List<MyCsListDto> csList = csPage.getContent();

	    int totalPages = csPage.getTotalPages();
	    int currentPageGroup = (int) Math.ceil((double) page / 5);
	    
	    model.addAttribute("csList", csList);
	    model.addAttribute("currentPage", page);
	    model.addAttribute("currentPageGroup", currentPageGroup);
	    model.addAttribute("totalPages", totalPages);
	    
	    return "/member/myCsList";
	}
	
	
	
	
	
	
}
