package com.trip.entity.Member;


import com.trip.dto.Member.UserSignUpDto;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user_detail")
public class UserDetailEntity {
	@Id
	@Column(name = "user_id")
	private Long userId;
	
	@OneToOne(fetch = FetchType.LAZY)
	@MapsId
	@JoinColumn(name="user_id")
	private UserEntity user;
	
	@Column(name="email", nullable=false)
	private String email;
	
	@Column(name="address")
	private String address;
	
	@Column(name="tel")
	private String tel;
	
	@Column(name="profile_img")
	private String profileImg;
	


	public static UserDetailEntity from(UserSignUpDto dto, UserEntity user) {
		return UserDetailEntity.builder()
				.user(user)
				.email(dto.getEmail())
				.tel(dto.getTel())
				.address(dto.getAddress())
				.profileImg(dto.getProfileImg())
				.build();
	}
	
	public void update(String email, String tel, String address) {
	    if (email != null && !email.isBlank()) {
	        this.email = email;
	    }
	    if (tel != null && !tel.isBlank()) {
	        this.tel = tel;
	    }
	    if (address != null && !address.isBlank()) {
	        this.address = address;
	    }
	}
	
	public void updateProfileImg(String profileImg) {
	    if (profileImg != null && !profileImg.isBlank()) {
	        this.profileImg = profileImg;
	    }
	}
	
}
