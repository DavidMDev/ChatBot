package com.web.atrio.configuration;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configurers.GlobalAuthenticationConfigurerAdapter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.web.atrio.users.models.Account;
import com.web.atrio.users.repositories.AccountRepository;
import com.web.atrio.users.utilities.RoleService;

@Configuration
public class WebSecurityConfiguration extends GlobalAuthenticationConfigurerAdapter {

	@Autowired
	private AccountRepository accountRepository;

	@Override
	public void init(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService());
	}

	@Bean
	UserDetailsService userDetailsService() {
		return new UserDetailsService() {
			@Override
			public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
				Account account = accountRepository.findByUsername(username);
				String authorityString = RoleService.getAuthorityStringSeperatedByCommas(account);

				if (account != null) {
					List<GrantedAuthority> authList = AuthorityUtils
							.commaSeparatedStringToAuthorityList(authorityString);
					return new User(account.getUsername(), account.getPassword(), true, true, true, true, authList);
				} else {
					throw new UsernameNotFoundException("could not find the user '" + username + "'");
				}
			}
		};
	}
}
