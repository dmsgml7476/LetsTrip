package com.trip.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import jakarta.servlet.http.HttpSession;

@Configuration
@EnableWebSecurity

public class SecurityConfig {
	
	 @Bean
	    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
	        http
	            .authorizeHttpRequests(auth -> auth
	            	.requestMatchers("/admin/**").hasRole("ADMIN")
	            	.requestMatchers("/mypage/**").authenticated()
	                .anyRequest().permitAll()  
	                

	            )
	            .formLogin (
		        		form->form
		        			.loginPage("/login")
		        			.successHandler((request, response, authentication) -> {
		        				HttpSession session = request.getSession(false);
		        				if (session != null) {
		        			        String redirectUrl = (String) session.getAttribute("redirectAfterLogin");
		        			        if (redirectUrl != null) {
		        			            session.removeAttribute("redirectAfterLogin");
		        			            response.sendRedirect(redirectUrl);
		        			            return;
		        			        }
		        			    }
		        				// 이전 요청이 저장되어 있는지 확인
				                        SavedRequest savedRequest = new HttpSessionRequestCache().getRequest(request, response);
		
				                        if (savedRequest != null) {
				                            // 이전 URL로 리디렉션
				                            String targetUrl = savedRequest.getRedirectUrl();
				                            response.sendRedirect(targetUrl);
				                        } else {
				                            // 기본 URL로 이동
				                            response.sendRedirect("/");
				                        }
		        				    
		                    })
		        			.usernameParameter("loginId")
		        			.failureUrl("/login/error")
		        			.permitAll()
		        			)
	            .logout(logout->logout
	            		.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
	            		.logoutSuccessUrl("/")
	            		.invalidateHttpSession(true)
	            		)
	            .csrf(csrf -> csrf.disable())
	            .headers(headers -> headers.disable()); 
	        	
	        return http.build();
	    }
	 @Bean
	 public PasswordEncoder passwordEncoder()	{
		 return new BCryptPasswordEncoder();
	 }

}
