package com.test.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.stereotype.Component;

@Component(value = "tokenAuthenticationProvider")
public class TokenAuthenticationProvider implements AuthenticationProvider {
	
	/*public TokenAuthenticationProvider(TokenService tokenService) {
		this.tokenService = tokenService;
	}*/
	
	@Autowired
	private TokenService tokenService;

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		String userId = (String) authentication.getPrincipal();
		String token = authentication.getCredentials().toString();
		if(userId != null && token != null && !userId.isEmpty() && !token.isEmpty()) {
			//Implement token auth here.
			return tokenService.authenticate(userId, token);
		} else {
			return authentication;
		}
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return authentication.equals(PreAuthenticatedAuthenticationToken.class);
	}

}
