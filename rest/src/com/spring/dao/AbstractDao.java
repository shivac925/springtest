package com.spring.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class AbstractDao {

	@Autowired
	private SessionFactory sessionFactory;

	protected Session getSession() {
//		Statistics stats = sessionFactory.getStatistics();
//		System.out.println("Stats enabled=" + stats.isStatisticsEnabled());
//		stats.setStatisticsEnabled(true);
//		System.out.println("Stats enabled=" + stats.isStatisticsEnabled());
//		System.out.println("Fetch Count=" + stats.getEntityFetchCount());
//		System.out.println("Second Level Hit Count="
//				+ stats.getSecondLevelCacheHitCount());
//		System.out.println("Second Level Miss Count="
//				+ stats.getSecondLevelCacheMissCount());
//		System.out.println("Second Level Put Count="
//				+ stats.getSecondLevelCachePutCount());
		return sessionFactory.getCurrentSession();
	}


	protected SessionFactory sessionFactory() {
		return this.sessionFactory;
	}

}