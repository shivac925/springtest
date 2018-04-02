package com.spring.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.SQLQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AppMap extends AbstractDao {

	private final static Logger LOGGER = LoggerFactory.getLogger(AppMap.class);
	
	public Map<String, String> getMap() {
		Map<String, String> map = new HashMap<String, String>();
		SQLQuery query = getSession().createSQLQuery("SELECT KEY, VALUE FROM DB_PROPERTIES");
		@SuppressWarnings("unchecked")
		List<Object[]> rows = query.list();
		for (Object[] row : rows) {
			map.put((String) row[0], (String) row[1]);
		}
		LOGGER.info("getMap " + map);
		return map;
	}
}
