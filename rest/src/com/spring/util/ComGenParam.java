package com.spring.util;

import java.util.Map;

import com.spring.dao.AppMap;

public class ComGenParam {

	private static ComGenParam comGenParam = new ComGenParam();
	
	private static Map<String, String> paramMap;
	
//	@Autowired
//	AppMap appMap;
	
	private ComGenParam() {
	}
	
	private ComGenParam(AppMap appMap) {
		paramMap = appMap.getMap();
	}
	
	public static ComGenParam createInstance() {
//		if(comGenParam == null) {
//			comGenParam = new ComGenParam();
//		}
		return comGenParam;
	}
	
	public Map<String, String> getMap() {
		return paramMap;
	}
}
