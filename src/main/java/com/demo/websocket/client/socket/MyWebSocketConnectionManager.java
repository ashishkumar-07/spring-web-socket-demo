package com.demo.websocket.client.socket;

import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.WebSocketConnectionManager;

public class MyWebSocketConnectionManager extends WebSocketConnectionManager{

	public MyWebSocketConnectionManager(WebSocketClient client, WebSocketHandler webSocketHandler, String uriTemplate,
			Object... uriVariables) {
		super(client, webSocketHandler, uriTemplate, uriVariables);
	}
	
	public boolean isConnectionOpen() {
		return super.isConnected();
	}

}
