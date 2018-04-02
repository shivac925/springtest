package com.spring.service;

import java.util.List;
import java.util.Map;

import org.hibernate.ObjectNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.spring.model.DbProps;
import com.spring.util.ComGenParam;

@Service("userService")
public class UserServiceImpl implements UserService{
	private final static Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);
//	@Resource(name = "application")
//	Properties sdllsaf;
	
	@Autowired
	private TransactionsServ transServ;
	
	public List<DbProps> getMapFromDb() {
		List<DbProps> lis = null;
		LOGGER.info("In getMapFromDb service.");
		try {
			lis = transServ.getMapFromDb();
		} catch (DataAccessException | ObjectNotFoundException e) {
			LOGGER.error("my msg: " + e.getMessage());
			lis = transServ.getMapFromDb();
			getMapFromDb();
		}
		if(lis == null) {
			LOGGER.info("DB Props list size is null, call evict cache...");
		} else if(lis.size() == 0) {
			LOGGER.info("DBProps list is null: " + lis.size());
		}
		return lis;
	}
	
	public String refreshCache() {
		transServ.refreshCache();
		return "success";
	}
	
	public Map<String, String> getParamMap() {
		return ComGenParam.createInstance().getMap();
	}
	
}
