package com.cable.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.stereotype.Component;

@Entity	
@Table(name = "setupbox")
@Component
public class SetupboxModel {

	@Id
	@GeneratedValue(generator = "setupbox_id",strategy = GenerationType.AUTO)
	@Column(name = "setupbox_id")
	private Integer setupboxId;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "operator_id")
	private OperatorModel operatorModel;
	
	@Column(name = "connection_status")
	private String connectionStatus;
	
	@Column(name = "setupbox_status")
	private String setupboxStatus;
	
	@Column(name = "setupbox_type")
	private String setupboxType;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "provider_id")
	private ProviderModel providerModel;

	public Integer getSetupboxId() {
		return setupboxId;
	}

	public void setSetupboxId(Integer setupboxId) {
		this.setupboxId = setupboxId;
	}

	public OperatorModel getOperatorModel() {
		return operatorModel;
	}

	public void setOperatorModel(OperatorModel operatorModel) {
		this.operatorModel = operatorModel;
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

	public ProviderModel getProviderModel() {
		return providerModel;
	}

	public void setProviderModel(ProviderModel providerModel) {
		this.providerModel = providerModel;
	}

	@Override
	public String toString() {
		return "SetupboxModel [setupboxId=" + setupboxId + ", operatorModel=" + operatorModel + ", connectionStatus="
				+ connectionStatus + ", setupboxStatus=" + setupboxStatus + ", setupboxType=" + setupboxType
				+ ", providerModel=" + providerModel + "]";
	}
	
	
	
}
