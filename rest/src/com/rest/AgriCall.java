package com.rest;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rest.model.EndResponse;
import com.rest.model.LoginData;
import com.rest.model.LoginRequest;
import com.rest.model.VillagesRequest;

public class AgriCall {
	
	public static void main(String[] args) {
		AgriCall ac = new AgriCall();
		EndResponse response = ac.loginCall();
		if(response.getError().equals("0")) {
			LoginData loginData = (LoginData) response.getData();
			ac.villages(loginData);
		} else {
			System.err.println(response.getMessage());
		}
	}
	
	public void villages(LoginData loginData) {
		RestTemplate restTemplate = new RestTemplate();
		restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
		restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
		String url = "https://tabuat.icicibank.com/AGRI/masters/villages1";
		Map<String, String> headers = new HashMap<String, String>();
		headers.put("X-Auth-User", "2XzGcpQCDNrxtsAmO0LA7Q==");
		headers.put("X-Auth-Token", loginData.getToken());
		VillagesRequest request = new VillagesRequest();
		request.setParamType("state");
		ResponseEntity<EndResponse> responseEntity = restTemplate.exchange(url, HttpMethod.POST, 
				Utils.getRequestEntity(request, headers), EndResponse.class);
		EndResponse response = responseEntity.getBody();
		if(!response.getStatus().equalsIgnoreCase("fail")) {
			ObjectMapper objectMapper = new ObjectMapper();
			try {
				System.out.println(objectMapper.writeValueAsString(response.getData()));
			} catch (JsonProcessingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			System.err.println(response.getMessage());
		}
		
	}
	
	public EndResponse loginCall() {
		RestTemplate restTemplate = new RestTemplate();
		restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
		restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
		String url = "https://tabuat.icicibank.com/AGRI/login";
		
		LoginRequest loginRequest = new LoginRequest();
		loginRequest.setUserId("2XzGcpQCDNrxtsAmO0LA7Q==");
		loginRequest.setPassword("/EMkRO8Q6QeptgaSJBmUgA==");
		
		ResponseEntity<EndResponse> responseEntity = restTemplate.exchange(url, HttpMethod.POST, 
				Utils.getRequestEntity(loginRequest, null), EndResponse.class);
		EndResponse respone = responseEntity.getBody();
		if(respone.getError().equalsIgnoreCase("0")) {
			ObjectMapper objectMapper = new ObjectMapper();
			try {
				objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
				LoginData loginData = objectMapper.readValue(objectMapper.writeValueAsString(respone.getData()), LoginData.class);
				respone.setData(loginData);
			} catch (IOException e) {
				e.printStackTrace();
			} 
		} else {
			System.out.println(respone.getMessage());
		}
		return respone;
	}
}
