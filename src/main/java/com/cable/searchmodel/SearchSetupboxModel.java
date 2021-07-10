package com.cable.searchmodel;

public class SearchSetupboxModel {

	private Integer setupboxId;
	
	private Integer operatorId;
	
	private String connectionStatus;
	
	private String setupboxStatus;
	
	private String setupboxType;

	private Integer provderTypeId;

	public Integer getSetupboxId() {
		return setupboxId;
	}

	public void setSetupboxId(Integer setupboxId) {
		this.setupboxId = setupboxId;
	}

	public Integer getOperatorId() {
		return operatorId;
	}

	public void setOperatorId(Integer operatorId) {
		this.operatorId = operatorId;
	}

	public String getConnectionStatus() {
		return connectionStatus;
	}

	public void setConnectionStatus(String connectionStatus) {
		this.connectionStatus = connectionStatus;
	}

	public String getSetupboxStatus() {
		return setupboxStatus;
	}

	public void setSetupboxStatus(String setupboxStatus) {
		this.setupboxStatus = setupboxStatus;
	}

	public String getSetupboxType() {
		return setupboxType;
	}

	public void setSetupboxType(String setupboxType) {
		this.setupboxType = setupboxType;
	}

	public Integer getProvderTypeId() {
		return provderTypeId;
	}

	public void setProvderTypeId(Integer provderTypeId) {
		this.provderTypeId = provderTypeId;
	}

	@Override
	public String toString() {
		return "SearchSetupboxModel [setupboxId=" + setupboxId + ", operatorId=" + operatorId + ", connectionStatus="
				+ connectionStatus + ", setupboxStatus=" + setupboxStatus + ", setupboxType=" + setupboxType
				+ ", provderTypeId=" + provderTypeId + "]";
	}
	
}
