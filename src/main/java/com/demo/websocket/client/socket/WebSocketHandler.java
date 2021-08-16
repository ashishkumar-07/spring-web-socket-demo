package com.demo.websocket.client.socket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class WebSocketHandler extends TextWebSocketHandler {

    @Lazy
    @Autowired
    private WebSocketRetry webSocketRetry;
    
    @Autowired
    MyWebSocketConnectionManager connectionManager;

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        log.info("Connection has been established with websocket server. {}", session);
        session.sendMessage(new TextMessage("Great! I am now connected!"));
    }

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
    	log.info("Message received : {} ", message.getPayload());
       //Parse and process the payload as per need
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        super.afterConnectionClosed(session, status);
        log.error(status.toString());
        if (status.getCode() == 1006 || status.getCode() == 1011 || status.getCode() == 1012) {
        	webSocketRetry.retrySocketConnection(connectionManager);  
        }
    }
}
