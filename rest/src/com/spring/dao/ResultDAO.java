package com.spring.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.spring.model.DbProps;

public interface ResultDAO {
	
	public <T> void save(T object);
	
	public <T> void saveOrUpdate(T object);
	
	public <T> void update(T object);
	
	public <T> T get(Class<T> cls, Serializable s);
	
	public List<DbProps> getProperties();
	
	public void refreshCacheDao();
	
	public Map<String, String> getPropertiesMap();
	
	public String getCronExpression();
	
}
