package com.trip.websocket;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@Component
public class CustomWebSocketHandler extends TextWebSocketHandler {
	private final Map<Long, WebSocketSession> userSessions = new ConcurrentHashMap<>();
	
	@Override
	public void afterConnectionEstablished(WebSocketSession session) {
		System.out.println("✅ [서버] 웹소켓 연결 성공!");
		Long userId = (Long) session.getAttributes().get("userId");
		if(userId != null) {
			userSessions.put(userId, session);
			System.out.println("✅ [서버] 연결 성공! userSessions에 저장됨.");
		} else {
			 System.out.println("❌ [서버] 세션에 userId 없음 - 연결 불가");
		}
	}
	
	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
		   
		Long userId = (Long) session.getAttributes().get("userId");
		if(userId != null) {
			userSessions.remove(userId);
		}
	}
	
	public void sendNotification(Long userId, String message) throws IOException {
		WebSocketSession session = userSessions.get(userId);
		if(session != null && session.isOpen()) {
			session.sendMessage(new TextMessage(message));
		}
	}

}
