package com.web.atrio.chat.controllers;

import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;

import com.web.atrio.robot.controllers.RobotController;


public class ChatSocketHandler implements WebSocketHandler {

	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		session.sendMessage(new TextMessage(RobotController.greet()));
	}

	@Override
	public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
		String request = message.getPayload().toString();
		session.sendMessage(new TextMessage("You : "+ request));
		String response = RobotController.response(message.getPayload().toString());
		session.sendMessage(new TextMessage(response));
	}

	@Override
	public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
		session.close();
	}

	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
	}

	@Override
	public boolean supportsPartialMessages() {
		return false;
	}

}
