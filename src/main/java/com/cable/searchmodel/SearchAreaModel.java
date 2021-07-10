package com.cable.searchmodel;

public class SearchAreaModel {

	private Integer areaId;
	
	private String areaName;

	public Integer getAreaId() {
		return areaId;
	}

	public void setAreaId(Integer areaId) {
		this.areaId = areaId;
	}

	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	@Override
	public String toString() {
		return "SearchAreaModel [areaId=" + areaId + ", areaName=" + areaName + "]";
	}
	
	
}
