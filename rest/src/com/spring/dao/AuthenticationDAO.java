package com.spring.dao;

import java.util.List;

public interface AuthenticationDAO {

	public String getLastAccessedTimeDiff(String userId, String token);
	
	public int updateSessionTokenTime(String userId, String token);
	
	public List<String> getUserRoles(String userId);
}
