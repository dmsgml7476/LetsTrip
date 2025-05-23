package com.trip.config.auth;

import java.util.Collection;
import java.util.Collections;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.trip.entity.Member.UserEntity;

import lombok.Getter;

@Getter
public class CustomUserDetails implements UserDetails{
	
//	스프링 시큐리티에서 로그인한 사용자를 관리하기 위해 만든 UserDetails 인터페이스(기본) 구현체.
	
	private final UserEntity user;
	
	
//  로그인시 DB에서 조회한 UserEntity 를 받아서 user 필드에 저장.
//	이후 user.getLoginId(), user.getPassword(), user.getRole() 를 사용하여 정보 꺼냄
	
	public CustomUserDetails(UserEntity user) {
		this.user=user;
	}
	
	

// 사용자 권한(역할)을 반환함
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities(){
		return Collections.singleton(() -> "ROLE_" + user.getRole().name());
	}
	
	
//	Getter가 있어도 UserDetails 인터페이스가 요구하는 메서드이기 때문에 반드시 오버라이딩 해야만 한다.
    @Override public String getPassword() { return user.getPassword(); }
    @Override public String getUsername() { return user.getLoginId(); }
    
//  계정이 만료되지 않았는지를 반환 false면 "계정이 만료되어 사용할 수 없습니다." 라는 에러 발생
    @Override public boolean isAccountNonExpired() { return true; }
//    계정이 잠겨있지 않은지 확인/ 로그인 실패 5회 시 잠금 처리 같은 기능에 쓰임
    @Override public boolean isAccountNonLocked() { return true; }
//    비밀번호가 만료되지 않았는지 확인
    @Override public boolean isCredentialsNonExpired() { return true; }
//    계정이 활성화(사용 가능) 상태인지 확인. 탈퇴/휴면 계정 등의 로그인을 막고 싶을때 false 로 설정하면 됨.
    @Override public boolean isEnabled() { return true; }
	
	
}
