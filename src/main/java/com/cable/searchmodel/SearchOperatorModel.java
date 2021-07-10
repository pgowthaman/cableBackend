package com.cable.searchmodel;

public class SearchOperatorModel {

	private Integer operatorId;
	
	private String operatorName;

	public Integer getOperatorId() {
		return operatorId;
	}

	public void setOperatorId(Integer operatorId) {
		this.operatorId = operatorId;
	}

	public String getOperatorName() {
		return operatorName;
	}

	public void setOperatorName(String operatorName) {
		this.operatorName = operatorName;
	}

	@Override
	public String toString() {
		return "SearchOperatorModel [operatorId=" + operatorId + ", operatorName=" + operatorName
				+ "]";
	}
	
}
