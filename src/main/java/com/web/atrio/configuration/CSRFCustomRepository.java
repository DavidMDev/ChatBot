package com.web.atrio.configuration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections4.map.PassiveExpiringMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.security.web.csrf.CsrfTokenRepository;

import com.web.atrio.users.repositories.AccountRepository;

public class CSRFCustomRepository implements CsrfTokenRepository {
	private static PassiveExpiringMap<String, CsrfToken> tokens = new PassiveExpiringMap<String, CsrfToken>(1800000);
	
	@Autowired
	AccountRepository accountRepository;
	
	@Override
	public synchronized CsrfToken generateToken(HttpServletRequest request) {
		CsrfToken token = CookieCsrfTokenRepository.withHttpOnlyFalse().generateToken(request);
		return token;
	}

	@Override
	public synchronized void saveToken(CsrfToken token, HttpServletRequest request, HttpServletResponse response) {
		String sessionId = request.getSession().getId();
		tokens.put(sessionId, token);
	}

	@Override
	public synchronized CsrfToken loadToken(HttpServletRequest request) {
		return obtainToken(request);
	}
	
	public synchronized static void deleteToken(HttpServletRequest request){
		String sessionId = request.getSession().getId();
		request.getSession().invalidate();
		tokens.remove(sessionId);
		AuthenticatedUsersService.removeUser(sessionId);
	}

	public synchronized static String getTokenFromSessionId(HttpServletRequest request){
		CsrfToken token = tokens.get(request.getSession().getId());
		if(token == null){
			token = CookieCsrfTokenRepository.withHttpOnlyFalse().generateToken(request);
			tokens.put(request.getSession().getId(), token);
		}
		return token.getToken();
	}
	
	public synchronized static CsrfToken obtainToken(HttpServletRequest request) {
		String sessionId = request.getSession().getId();
		CsrfToken token = tokens.get(sessionId);
		String tokenFromRequest = request.getHeader("X-XSRF-TOKEN");
		if(token != null && token.getToken().equals(tokenFromRequest)){
			return token;
		} else {
			tokens.remove(sessionId);
			request.getSession().invalidate();
			AuthenticatedUsersService.removeUser(sessionId);
			return null;
		}
	}
}
