package com.cable.searchmodel;

import java.util.Date;

public class SearchConnectionModel {

	private Integer connectionId;
	
	private Integer userId;
	
	private Integer operatorId;
	
	private Integer areaId;
	
	private Integer setupboxId;
	
	private String connectionStatus;
	
	private Integer connectionNumber;
	
	private Date connectionDate;
	
	private String conDate;
	
	private Integer dueAmount;

	public Integer getConnectionId() {
		return connectionId;
	}

	public void setConnectionId(Integer connectionId) {
		this.connectionId = connectionId;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getOperatorId() {
		return operatorId;
	}

	public void setOperatorId(Integer operatorId) {
		this.operatorId = operatorId;
	}

	public Integer getAreaId() {
		return areaId;
	}

	public void setAreaId(Integer areaId) {
		this.areaId = areaId;
	}

	public Integer getSetupboxId() {
		return setupboxId;
	}

	public void setSetupboxId(Integer setupboxId) {
		this.setupboxId = setupboxId;
	}

	public String getConnectionStatus() {
		return connectionStatus;
	}

	public void setConnectionStatus(String connectionStatus) {
		this.connectionStatus = connectionStatus;
	}

	public Integer getConnectionNumber() {
		return connectionNumber;
	}

	public void setConnectionNumber(Integer connectionNumber) {
		this.connectionNumber = connectionNumber;
	}

	public Date getConnectionDate() {
		return connectionDate;
	}

	public void setConnectionDate(Date connectionDate) {
		this.connectionDate = connectionDate;
	}

	public Integer getDueAmount() {
		return dueAmount;
	}

	public void setDueAmount(Integer dueAmount) {
		this.dueAmount = dueAmount;
	}

	public String getConDate() {
		return conDate;
	}

	public void setConDate(String conDate) {
		this.conDate = conDate;
	}

	@Override
	public String toString() {
		return "SearchConnectionModel [connectionId=" + connectionId + ", userId=" + userId + ", operatorId="
				+ operatorId + ", areaId=" + areaId + ", setupboxId=" + setupboxId + ", connectionStatus="
				+ connectionStatus + ", connectionNumber=" + connectionNumber + ", connectionDate=" + connectionDate
				+ ", conDate=" + conDate + ", dueAmount=" + dueAmount + "]";
	}
	
	
}
