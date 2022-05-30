package com.cable.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Component;

@Entity
@Table(name="connection")
@Component
public class ConnectionModel {

	@Id
	@GeneratedValue(generator = "connection_id",strategy = GenerationType.AUTO)
	@Column(name="connection_id")
	private Integer connectionId;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private UserModel userModel;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "operator_id")
	private OperatorModel operatorModel;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "area_id")
	private AreaModel areaModel;
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "setupbox_id")
	private SetupboxModel setupboxModel;
	
	@Column(name = "connection_status")
	private String connectionStatus;
	
	@Column(name = "connection_number")
	private Integer connectionNumber;
	
	@Column(name = "connectiod_date")
	private Date connectionDate;
	
	@Column(name = "due_amount")
	private Integer dueAmount;

	public Integer getConnectionId() {
		return connectionId;
	}

	public void setConnectionId(Integer connectionId) {
		this.connectionId = connectionId;
	}

	public UserModel getUserModel() {
		return userModel;
	}

	public void setUserModel(UserModel userModel) {
		this.userModel = userModel;
	}

	public OperatorModel getOperatorModel() {
		return operatorModel;
	}

	public void setOperatorModel(OperatorModel operatorModel) {
		this.operatorModel = operatorModel;
	}

	public AreaModel getAreaModel() {
		return areaModel;
	}

	public void setAreaModel(AreaModel areaModel) {
		this.areaModel = areaModel;
	}

	public SetupboxModel getSetupboxModel() {
		return setupboxModel;
	}

	public void setSetupboxModel(SetupboxModel setupboxModel) {
		this.setupboxModel = setupboxModel;
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

	@Override
	public String toString() {
		return "ConnectionModel [connectionId=" + connectionId + ", userModel=" + userModel + ", operatorModel="
				+ operatorModel + ", areaModel=" + areaModel + ", setupboxModel=" + setupboxModel
				+ ", connectionStatus=" + connectionStatus + ", connectionNumber=" + connectionNumber
				+ ", connectionDate=" + connectionDate + ", dueAmount=" + dueAmount + "]";
	}

	
}
