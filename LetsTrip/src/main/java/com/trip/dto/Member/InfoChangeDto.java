package com.trip.dto.Member;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class InfoChangeDto {

	@NotBlank(message="이메일이 입력되지 않았습니다.")
	@Email(message="유효한 이메일 형식이 아닙니다.")
	private String email;
	
	@Pattern(regexp="^\\d{3}-\\d{3,4}-\\d{4}$", message="전화번호 형식이 올바르지 않습니다. 예) 010-1234-5678")
	private String tel;
	@Size(max=16, message="닉네임은 16자 이하로 입력해주세요.")
	private String nickname;
	@Size(max = 100, message = "주소는 100자 이하로 입력해주세요.")
	private String address;
}
