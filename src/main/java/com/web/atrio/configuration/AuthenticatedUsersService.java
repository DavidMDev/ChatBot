package com.web.atrio.configuration;

import org.apache.commons.collections4.map.PassiveExpiringMap;

public class AuthenticatedUsersService {
	private static PassiveExpiringMap<String, String> loggedUsers = new PassiveExpiringMap<String, String>(1800000);

	public AuthenticatedUsersService() {
		super();
	}

	public synchronized static void logUser(String username, String sessionId) {
		loggedUsers.put(sessionId, username);
	}

	public synchronized static String getUser(String sessionId) {
		return loggedUsers.get(sessionId);
	}

	public synchronized static void removeUser(String sessionId) {
		loggedUsers.remove(sessionId);
	}
}
