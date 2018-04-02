package com.rest;

import java.util.Collections;
import java.util.Map;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

public class Utils {
	
	public static <T> HttpEntity<T> getRequestEntity(T request, Map<String, String> headersMap) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
		headers.set("iv", "ad803055fed116a95033b1fb8e289672");
		headers.set("salt", "f55aca49f45dcf681a7701b1fc9aabaf");
		if(headersMap != null && !headersMap.isEmpty()) {
			for (Map.Entry<String, String> map : headersMap.entrySet()) {
				headers.set(map.getKey(), map.getValue());
			}
		}
		HttpEntity<T> entity = new HttpEntity<T>(request, headers);
		return entity;
	}

}
