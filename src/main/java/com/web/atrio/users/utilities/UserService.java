package com.web.atrio.users.utilities;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.web.atrio.configuration.AuthenticatedUsersService;
import com.web.atrio.users.models.Account;
import com.web.atrio.users.repositories.AccountRepository;

public class UserService {

	public static String getUser(HttpServletRequest request) {
		String name = AuthenticatedUsersService.getUser(request.getSession(false).getId());
		return name;
	}
}
