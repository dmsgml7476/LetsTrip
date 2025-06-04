package com.trip.websocket;

import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.stereotype.Component;

import java.util.Map;

import org.springframework.http.server.ServerHttpRequest;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import jakarta.servlet.http.HttpSession;
@Component
public class WebSocketHandshakeInterceptor implements HandshakeInterceptor {
	@Override
	public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Map<String, Object> attributes) {
		if(request instanceof ServletServerHttpRequest servletRequest) {
			HttpSession session = servletRequest.getServletRequest().getSession();
			if (session != null) {
				Long userId = (Long) session.getAttribute("userId");
				if(userId != null) {
					attributes.put("userId", userId);
				}
			}
		}
		return true;
	}
	
	@Override
	public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Exception ex) {
		
	}

}
