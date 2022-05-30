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
@Table(name="provider")
@Component
public class ProviderModel {

	@Id
	@GeneratedValue(generator = "provider_id",strategy = GenerationType.AUTO)
	@Column(name = "provider_id")
	private Integer providerId;
	
	@Column(name="provider_name")
	private String providerName;
	
	@Column(name="provider_type")
	private String providerType;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "operator_id")
	private OperatorModel operatorModel;

	public Integer getProviderId() {
		return providerId;
	}

	public void setProviderId(Integer providerId) {
		this.providerId = providerId;
	}

	public String getProviderName() {
		return providerName;
	}

	public void setProviderName(String providerName) {
		this.providerName = providerName;
	}

	public String getProviderType() {
		return providerType;
	}

	public void setProviderType(String providerType) {
		this.providerType = providerType;
	}

	public OperatorModel getOperatorModel() {
		return operatorModel;
	}

	public void setOperatorModel(OperatorModel operatorModel) {
		this.operatorModel = operatorModel;
	}

	@Override
	public String toString() {
		return "ProviderModel [providerId=" + providerId + ", providerName=" + providerName + ", providerType="
				+ providerType + ", operatorModel=" + operatorModel + "]";
	}
	
}
