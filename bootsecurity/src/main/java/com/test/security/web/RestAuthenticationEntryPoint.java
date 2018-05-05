package com.test.security.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.test.model.EndResult;

@Component
public class RestAuthenticationEntryPoint implements AuthenticationEntryPoint{

	ObjectMapper mapper;
	EndResult endResult;
	
	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException)
			throws IOException, ServletException {
		mapper = new ObjectMapper();
		endResult = new EndResult();
		endResult.setData(null);
		endResult.setErrorCode(1);
		endResult.setMessage("Valid Credentials are required");
		endResult.setStatus("fail");
		response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		response.setContentType("application/json");
		response.getWriter().write(mapper.writeValueAsString(endResult));
	}

	
}
