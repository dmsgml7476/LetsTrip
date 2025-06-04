package com.trip.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

import com.trip.websocket.CustomWebSocketHandler;
import com.trip.websocket.WebSocketHandshakeInterceptor;


@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {
    private final CustomWebSocketHandler handler;
    private final WebSocketHandshakeInterceptor interceptor;

    public WebSocketConfig(CustomWebSocketHandler handler, WebSocketHandshakeInterceptor interceptor) {
        this.handler = handler;
        this.interceptor = interceptor;
    }
    

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(handler, "/ws/alert")
                .addInterceptors(interceptor)
                .setAllowedOrigins("*");
    }
	
	

}
