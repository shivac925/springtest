package com.spring.dao;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//import javax.transaction.Transactional;

import org.hibernate.Cache;
import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.spring.model.DbProps;

@Repository("resultDAO")
public class ResultDAOImpl extends AbstractDao implements ResultDAO {

	private final static Logger LOGGER = LoggerFactory.getLogger(ResultDAOImpl.class);
	
	public <T> void save(T object) {
		getSession().save(object);
	}
	
	public <T> void saveOrUpdate(T object) {
		getSession().saveOrUpdate(object);
	}
	
	public <T> void update(T object) {
		getSession().update(object);
	}
	
	public <T> T get(Class<T> cls, Serializable s) {
		return cls.cast(getSession().get(cls, s));
	}

//	@Cacheable(value="test1")
	public List<DbProps> getProperties() {
		Criteria criteria = getSession().createCriteria(DbProps.class);
		criteria.setCacheable(true);
		@SuppressWarnings("unchecked")
		List<DbProps> list = (List<DbProps>) criteria.list();
		// }
		return list;
	}

	public void refreshCacheDao() {
		try {
			Cache cache = sessionFactory().getCache();
			cache.evictAllRegions();
			LOGGER.info("Cleared all cache");
		} catch (Exception e) {
			LOGGER.error("SessionController", "evict2ndLevelCache",
					"Error evicting 2nd level hibernate cache entities: ", e);
		}
	}

	@Transactional(readOnly = true)
	public Map<String, String> getPropertiesMap() {
		Map<String, String> map = new HashMap<String, String>();
		@SuppressWarnings("unchecked")
		List<Object[]> list = getSession().createSQLQuery("SELECT KEY, VALUE FROM DB_PROPERTIES").list();
		for (Object[] column : list) {
			map.put(String.valueOf(column[0]), String.valueOf(column[1]));
		}
		return map;
	}
	
	@Transactional(readOnly = true)
	public String getCronExpression() {
		String sql = "SELECT CRON_EXPRESSION FROM CRON_EXPRESSION WHERE JOB_NAME = :jobName";
		
		SQLQuery query = getSession().createSQLQuery(sql);
		query.setString("jobName", "TESTJOB");
		@SuppressWarnings("unchecked")
		List<String> cronExpList = (List<String>) query.list();
		if(cronExpList != null && !cronExpList.isEmpty()) {
			return cronExpList.get(0);
		}
		return "";
	}

}
