package com.test.security.web;

import java.io.IOException;
import java.util.Map;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.security.web.authentication.session.SessionAuthenticationException;
import org.springframework.web.filter.GenericFilterBean;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.test.model.EndResult;

public class CustomAuthenticationTokenFilter extends GenericFilterBean {
	
	private AuthenticationManager authenticationManager;
	
	public CustomAuthenticationTokenFilter(AuthenticationManager authenticationManager) {
		this.authenticationManager = authenticationManager;
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest httpRequest = asHttp(request);
		HttpServletResponse httpResponse = asHttp(response);
		ObjectMapper mapper;
		EndResult endResult;
		String userId = httpRequest.getHeader("X-Auth-User");
		String token = httpRequest.getHeader("X-Auth-Token");
		try {
			if(userId != null && !userId.isEmpty() && token != null && !token.isEmpty()) {
				processTokenAuthentication(userId, token);
			} else {
				System.out.println("Token or user is empty or null...!, " + httpRequest.getRequestURL());
			}
			chain.doFilter(request, response);
		} catch (InternalAuthenticationServiceException internalAuthenticationServiceException) {
			SecurityContextHolder.clearContext();
			System.out.println(internalAuthenticationServiceException);
			httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			httpResponse.setHeader("Content-Type", "application/json");
			mapper = new ObjectMapper();
			endResult = new EndResult();
			endResult.setData(null);
			endResult.setErrorCode(1);
			endResult.setMessage("Invalid Session");
			endResult.setStatus("fail");
			httpResponse.getWriter().write(mapper.writeValueAsString(endResult));
		} catch(AuthenticationException authenticationException) {
			SecurityContextHolder.clearContext();
			httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			httpResponse.setHeader("Content-Type", "application/json");
			mapper = new ObjectMapper();
			endResult = new EndResult();
			endResult.setData(null);
			endResult.setErrorCode(1);
			endResult.setMessage(authenticationException.getMessage());
			endResult.setStatus("fail");
			httpResponse.getWriter().write(mapper.writeValueAsString(endResult));
		}
	}

	private HttpServletRequest asHttp(ServletRequest request) {
		return (HttpServletRequest) request;
	}
	
	private HttpServletResponse asHttp(ServletResponse response) {
		return (HttpServletResponse) response;
	}
	
	private void processTokenAuthentication(String userId, String token) {
		Authentication resultOfAuthentication = tryToAuthenticateWithToken(userId, token);
		SecurityContextHolder.getContext().setAuthentication(resultOfAuthentication);
	}
	
	private Authentication tryToAuthenticateWithToken(String userId, String token) {
		PreAuthenticatedAuthenticationToken requestAuthentication = new PreAuthenticatedAuthenticationToken(userId, token);
		return tryToAuthenticate(requestAuthentication);
	}
	
	private Authentication tryToAuthenticate(Authentication requestAuthentication) {
		Authentication responseAuthentication = authenticationManager.authenticate(requestAuthentication);
		if(responseAuthentication == null || !responseAuthentication.isAuthenticated()) {
			if(responseAuthentication.getDetails() != null) {
				@SuppressWarnings("unchecked")
				Map<String, String> map = (Map<String, String>) responseAuthentication.getDetails();
				throw new SessionAuthenticationException(map.get("session"));
			} else {
				throw new SessionAuthenticationException("User does not have a valid session");
			}
		}
		
		return responseAuthentication;
	}
}
