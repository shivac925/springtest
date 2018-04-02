package com.rest.model;

public class VillagesRequest {

	private String paramType;
	
	private String state;
	
	private String district;

	public String getParamType() {
		return paramType;
	}

	public void setParamType(String paramType) {
		this.paramType = paramType;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}
	
	@Override
	public String toString() {
		return "VillagesRequest [paramType=" + paramType + ", state=" + state + ", district=" + district + "]";
	}
	
}
