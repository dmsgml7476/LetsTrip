package com.trip.service.Member;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.trip.config.auth.CustomUserDetails;
import com.trip.constant.Member.Role;
import com.trip.dto.Member.InfoChangeDto;
import com.trip.dto.Member.UserSignUpDto;
import com.trip.entity.Member.HashtagsEntity;
import com.trip.entity.Member.UserAlertSettingEntity;
import com.trip.entity.Member.UserDetailEntity;
import com.trip.entity.Member.UserEntity;
import com.trip.entity.Member.UserHashtagEntity;
import com.trip.repository.Member.HashtagsRepository;
import com.trip.repository.Member.UserAlertSettingRepository;
import com.trip.repository.Member.UserDetailRepository;
import com.trip.repository.Member.UserHashtagRepository;
import com.trip.repository.Member.UserRepository;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

	private final UserRepository userRepository;
	private final UserAlertSettingRepository userAlertSettingRepository;
	private final PasswordEncoder passwordEncoder;
	private final HashtagsRepository hashtagsRepository;
	private final UserHashtagRepository userHashtagRepository;
	private final UserDetailRepository userDetailRepository;


	@Transactional
	public void register(UserSignUpDto dto, UserAlertSettingEntity setting) {
	    // 1. 유저 생성 및 저장
	    UserEntity user = UserEntity.from(dto, passwordEncoder);
	    UserEntity savedUser = userRepository.save(user);

	    // 2. 알림 설정 저장
	    setting.setUser(savedUser);
	    userAlertSettingRepository.save(setting);

	    // ✅ 3. 유저 디테일 저장
	    UserDetailEntity detail = UserDetailEntity.from(dto, savedUser);
	    userDetailRepository.save(detail);

	    // 4. 해시태그 저장
	    if (dto.getHashtagIds() != null) {
	        for (String idStr : dto.getHashtagIds()) {
	            Long tagId = Long.parseLong(idStr);
	            HashtagsEntity tag = hashtagsRepository.findById(tagId)
	                    .orElseThrow(() -> new IllegalArgumentException("해시태그 없음"));

	            userHashtagRepository.save(
	                UserHashtagEntity.builder()
	                    .user(savedUser)
	                    .hashtags(tag)
	                    .build()
	            );
	        }
	    }
	}
	public boolean existsByLoginId(String loginId) {
	    return userRepository.existsByLoginId(loginId);
	}
	
	@Override
	public UserDetails loadUserByUsername(String loginId) throws UsernameNotFoundException {
		// 스프링 시트 로그인, 아이디로 회원 테이블에서 정보 조회
		
		UserEntity user = userRepository.findByLoginId(loginId);
		
		if(user==null) {
			throw new UsernameNotFoundException(loginId);
		}
		
		return new CustomUserDetails(user);
	}

	public boolean existsByNickname(String nickname) {
		return userRepository.existsByNickname(nickname);
	}
	
	
	// 비밀번호 변경
	
	@Transactional
	public void updatePassword(UserEntity user, String newPassword) {
	    String encodedPw = passwordEncoder.encode(newPassword);
	    user.updatePassword(encodedPw);
	    userRepository.save(user); // 변경 내용 저장
	}
	
	@Transactional
	public void changeUserInfo(Long userId, InfoChangeDto dto) {
	    UserEntity user = userRepository.findById(userId)
	            .orElseThrow(() -> new IllegalArgumentException("유저를 찾을 수 없습니다."));

	    UserDetailEntity detail = userDetailRepository.findByUserId(userId)
	            .orElseThrow(() -> new IllegalArgumentException("유저 상세정보를 찾을 수 없습니다."));

	    user.setNickname(dto.getNickname());
	    detail.setEmail(dto.getEmail());
	    detail.setAddress(dto.getAddress());
	    detail.setTel(dto.getTel());

	    // JPA에서는 @Transactional이면 save 없이도 Dirty Checking으로 자동 반영됨
	}

	
	
}
