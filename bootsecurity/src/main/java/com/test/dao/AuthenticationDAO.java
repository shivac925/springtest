package com.test.dao;

import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Repository;

@Repository("authenticationDAO")
public class AuthenticationDAO {

	/**
	 * Write own code to verify token from authorization service.
	 * @param userId
	 * @param token
	 * @return
	 */
	public String getLastAccessedTimeDiff(String userId, String token) {
		return "1";
	}

	public int updateSessionTokenTime(String userId, String token) {
		return 1;
	}

	/**
	 * Write your own method to check user roles. It can be from DB or else LAM or
	 * Other authorization servers.
	 * @param userId
	 * @return
	 */
	public List<String> getUserRoles(String userId) {
		List<String> userList = Arrays.asList("ROLE_USER", "ADMIN");
		return userList;
	}

}
