package com.trip.dto.Member;

import java.util.List;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class UserSignUpDto {
	@Size(min=6, max=16, message="아이디는 6자 이상 20자 이하로 입력해주세요.")
	@NotBlank(message="아이디가 입력되지 않았습니다.")
	@Pattern(regexp="^[a-z0-9_]+$", message="아이디는 소문자, 숫자, 언더바(_)만 사용가합니다.")
	private String loginId;
	
	@NotBlank(message="비밀번호가 입력되지 않았습니다.")
	@Pattern(
			regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d!@#$%^&*]{8,20}$",
			message="비밀번호가 적합하지 않습니다."
			)
	private String password;
	
	@NotBlank(message="이메일이 입력되지 않았습니다.")
	@Email(message="유효한 이메일 형식이 아닙니다.")
	private String email;
	
	@Pattern(regexp="^\\d{3}-\\d{3,4}-\\d{4}$", message="전화번호 형식이 올바르지 않습니다. 예) 010-1234-5678")
	private String tel;
	@Size(max=16, message="닉네임은 16자 이하로 입력해주세요.")
	private String nickname;
	@Size(max = 100, message = "주소는 100자 이하로 입력해주세요.")
	private String address;
	
	@Size(max=6, message="해시태그는 최대 6개까지 선택할 수 있습니다.")
	private List<String> hashtagIds;
	private String profileImg;
	private boolean agreedAllAlerts;

}
