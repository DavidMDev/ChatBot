package com.web.atrio.chat.controllers;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;

import com.web.atrio.configuration.AuthenticatedUsersService;

public class ChatSocketHandler implements WebSocketHandler {

	ArrayList<WebSocketSession> openSessions = new ArrayList<WebSocketSession>();
	HashMap<String, String> sessionIdToUsername = new HashMap<String, String>();

	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		Iterator<List<String>> it = session.getHandshakeHeaders().values().iterator();
		String jsessionId = "";
		String username = "";
		while (it.hasNext()) {
			String headerValue = it.next().toString();
			if (headerValue.contains("JSESSIONID=")) {
				jsessionId = headerValue.split("JSESSIONID=")[1].replaceAll("]", "");
				username = AuthenticatedUsersService.getUser(jsessionId);
				if (username != "" && jsessionId != "") {
					if (sessionIdToUsername.containsValue(username)) {
						session.close(CloseStatus.BAD_DATA);
						username = "";
					} else {
						sessionIdToUsername.put(session.getId(), username);
					}
				}
			}
		}
		if (username != "") {
			Date now = new Date();
			for (WebSocketSession openSession : openSessions) {
				openSession.sendMessage(new TextMessage(
						"/<" + now.getHours() + ":" + now.getMinutes() + " " + username + "> : connected to the chat"));
			}
			openSessions.add(session);
		} else {
			session.close(CloseStatus.PROTOCOL_ERROR);
		}
	}

	@Override
	public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
		Date now = new Date();
		for (WebSocketSession openSession : openSessions) {
			String text = message.getPayload().toString();
			if (text.startsWith("/me ")) {
				openSession.sendMessage(new TextMessage(
						"/*" + sessionIdToUsername.get(session.getId()) + " " + text.replaceAll("/me", "")));
			} else {
				openSession.sendMessage(new TextMessage("/<" + now.getHours() + ":" + now.getMinutes() + " "
						+ sessionIdToUsername.get(session.getId()) + "> " + text));
			}
		}
	}

	@Override
	public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
		sessionIdToUsername.remove(session.getId());
		session.close();
	}

	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
		if (closeStatus.getCode() != CloseStatus.BAD_DATA.getCode()) {
			openSessions.remove(session);
			Date now = new Date();
			for (WebSocketSession openSession : openSessions) {
				openSession.sendMessage(new TextMessage("/<" + now.getHours() + ":" + now.getMinutes() + " "
						+ sessionIdToUsername.get(session.getId()) + "> " + " : disconnected from the chat"));
			}
			sessionIdToUsername.remove(session.getId());
		}
	}

	@Override
	public boolean supportsPartialMessages() {
		return false;
	}

}
