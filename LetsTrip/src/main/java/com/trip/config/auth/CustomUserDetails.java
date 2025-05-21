package com.trip.config.auth;

import java.util.Collection;
import java.util.Collections;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.trip.entity.Member.UserEntity;

import lombok.Getter;

@Getter
public class CustomUserDetails implements UserDetails{
	private final UserEntity user;
	
	public CustomUserDetails(UserEntity user) {
		this.user=user;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities(){
		return Collections.singleton(() -> "ROLE_" + user.getRole().name());
	}
	
    @Override public String getPassword() { return user.getPassword(); }
    @Override public String getUsername() { return user.getLoginId(); }
    @Override public boolean isAccountNonExpired() { return true; }
    @Override public boolean isAccountNonLocked() { return true; }
    @Override public boolean isCredentialsNonExpired() { return true; }
    @Override public boolean isEnabled() { return true; }
	
	
}
