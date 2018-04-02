package com.spring.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Table(name = "DB_PROPERTIES")
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE, region = "test1")
//@org.hibernate.annotations.Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
public class DbProps {

	@Id
	@Column(name = "KEY")
	private String key;
	@Column(name = "VALUE")
	private String value;
	
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	
	
}
