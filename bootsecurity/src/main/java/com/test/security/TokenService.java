package com.test.security;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.stereotype.Service;

import com.test.dao.AuthenticationDAO;

@Service
public class TokenService {

	@Autowired
	AuthenticationDAO authenticationDAO;

	/**
	 * Write your own code to validate token against user.
	 * @param userId
	 * @param token
	 * @return
	 */
	public Authentication authenticate(final String userId, String token) {
		String count = null;
		Float timeout = 0f;
		PreAuthenticatedAuthenticationToken authentication = null;
		float timeOutValue = Float.valueOf("2.083");
		Map<String, String> sessionDetailsMap = new HashMap<String, String>();
		try {
			count = authenticationDAO.getLastAccessedTimeDiff(userId, token);
			if(count == null || count.isEmpty()) {
				authentication = new PreAuthenticatedAuthenticationToken(userId, token);
				authentication.setAuthenticated(false);
				sessionDetailsMap.put("session", "Invalid Session");
				authentication.setDetails(sessionDetailsMap);
				return authentication;
			} else {
				timeout = Float.valueOf(count);
			}
		} catch (Exception e) {
			authentication = new PreAuthenticatedAuthenticationToken(userId, token);
			authentication.setAuthenticated(false);
			sessionDetailsMap.put("session", "Invalid Session");
			authentication.setDetails(sessionDetailsMap);
			e.printStackTrace();
			return authentication;
		}

		if (timeout.floatValue() <= timeOutValue) {
			int finalToken = updateLastAccessTime(userId, token);
			if (finalToken == 1) {
				List<String> userRoles = getRoles(userId);
				ArrayList<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
				for(String role : userRoles) {
					GrantedAuthority ga = new SimpleGrantedAuthority(role);
					authorities.add(ga);
				}
				authentication = new PreAuthenticatedAuthenticationToken(userId, token, authorities);
				authentication.setAuthenticated(true);
				sessionDetailsMap.put("token", token);
				authentication.setDetails(sessionDetailsMap);
			} else {
				authentication = new PreAuthenticatedAuthenticationToken(userId, token);
				authentication.setAuthenticated(false);
				sessionDetailsMap.put("token", token);
				sessionDetailsMap.put("session", "Could not process request.");
				authentication.setDetails(sessionDetailsMap);
			}
		} else if (timeout.floatValue() > timeOutValue) {
			authentication = new PreAuthenticatedAuthenticationToken(userId, token);
			authentication.setAuthenticated(false);
			sessionDetailsMap.put("session", "Session Expired");
			authentication.setDetails(sessionDetailsMap);
		} else {
			authentication = new PreAuthenticatedAuthenticationToken(null, null);
			authentication.setAuthenticated(false);
		}
		return authentication;
	}

	private int updateLastAccessTime(String userId, String token) {
		int status = authenticationDAO.updateSessionTokenTime(userId, token);
		return status;
	}
	
	private List<String> getRoles(String userId){
		return authenticationDAO.getUserRoles(userId);
	}
}
