package com.trip.entity.Member;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;

import com.trip.constant.Member.Role;
import com.trip.dto.Member.UserSignUpDto;
import com.trip.entity.Lets.StoryEntity;
import com.trip.entity.Planner.TripPlanEntity;

import jakarta.persistence.*;
import lombok.*;


@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name="user")
public class UserEntity {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="user_id")
	private Long id;
	@Column(name="login_id", nullable=false, unique=true)
	private String loginId;
	
	@Column(nullable=false)
	private String password;
	
	@Column(nullable=false, unique=true)
	private String nickname;
	
	@Enumerated(EnumType.STRING)
	@Column(name="role", nullable=false)
	private Role role = Role.USER;
	
	@Column(name="created_time", nullable=false, updatable=false)
	private LocalDateTime createdTime;
	
	@Column(name="withdraw_time")
	private LocalDateTime withdrawTime;
	
//	========연관관계 매핑========
	
	@OneToOne(mappedBy="user", fetch=FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, orphanRemoval = true)
	private UserDetailEntity userDetail;
	
	@OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	private List<UserHashtagEntity> userHashtags = new ArrayList<>();
	
// ==========기능메서드========

	public static UserEntity from(UserSignUpDto dto, PasswordEncoder passwordEncoder) {
		   String rawNickname = dto.getNickname();
		   String nickname = (rawNickname == null || rawNickname.trim().isEmpty())
			        ? dto.getLoginId()
			        : rawNickname.trim();
		
		    return UserEntity.builder()
		            .loginId(dto.getLoginId())
		            .password(passwordEncoder.encode(dto.getPassword()))
		            .nickname(nickname)
		            .role(Role.USER)
		            .createdTime(LocalDateTime.now())
		            .build();
	}
	
	public void updateNickname(String nickname) {
	    if (nickname != null && !nickname.isBlank()) {
	        this.nickname = nickname;
	    }
	}
	
	public void withdraw() {
		this.role=Role.WITHDRAW;
		this.withdrawTime=LocalDateTime.now();
	}
	
	public void updatePassword(String encodedPassword) {
		if(encodedPassword != null && !encodedPassword.isBlank()) {
			this.password = encodedPassword;
		}
	}


	public UserEntity orElseThrow(Object object) {
		// TODO Auto-generated method stub
		return null;
	}
	
	

}
