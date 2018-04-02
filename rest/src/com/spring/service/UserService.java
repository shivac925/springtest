package com.spring.service;

import java.util.List;
import java.util.Map;

import com.spring.model.DbProps;

public interface UserService {
	
	public List<DbProps> getMapFromDb();
	
	public String refreshCache();
	
	public Map<String, String> getParamMap();
	
}
