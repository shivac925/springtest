package com.spring.dao;

import java.math.BigDecimal;
import java.util.List;

import org.hibernate.SQLQuery;
import org.springframework.stereotype.Repository;

@Repository("authenticationDAO")
public class AuthenticationDAOImpl extends AbstractDao implements AuthenticationDAO {

	public String getLastAccessedTimeDiff(String userId, String token) {
		String sql = "SELECT ROUND((SYSDATE - LASTUPDATED)*100, 3) DIFF FROM USER_SESSION WHERE USER_ID = :userId AND TOKEN = :token";
		SQLQuery query = getSession().createSQLQuery(sql);
		query.setString("userId", userId);
		query.setString("token", token);
		@SuppressWarnings("unchecked")
		List<BigDecimal> result = (List<BigDecimal>) query.list();
		if(result.size() == 1) {
			BigDecimal timeOut = result.get(0);
			return timeOut.toString();
		} else {
			return "";
		}
	}
	
	public int updateSessionTokenTime(String userId, String token) {
		String sql = "UPDATE USER_SESSION SET LASTUPDATED = SYSDATE, TOKEN = :token WHERE USER_ID = :userId";
		SQLQuery query = getSession().createSQLQuery(sql);
		query.setString("userId", userId);
		query.setString("token", token);
		return query.executeUpdate();
	}
	
	public List<String> getUserRoles(String userId) {
		String sql = "SELECT USER_ROLE FROM USER_ROLES1 WHERE USER_ID = :userId";
		SQLQuery query = getSession().createSQLQuery(sql);
		query.setString("userId", userId);
		@SuppressWarnings("unchecked")
		List<String> list = (List<String>) query.list();
		return list;
	}
}
