package com.cable.to;

import java.util.Objects;

import org.springframework.stereotype.Component;

import com.cable.model.AreaModel;
import com.cable.model.OperatorModel;
import com.cable.model.ProviderModel;
import com.cable.utils.StringUtils;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@Component
@JsonInclude(Include.NON_NULL)
public class ProviderTO {

	private String providerId;
	
	private String providerName;
	
	private String providerType;
	
	private OperatorTO operatorTO;
	
	private String operatorId;
	
	public String getProviderId() {
		return providerId;
	}

	public void setProviderId(String providerId) {
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

	public OperatorTO getOperatorTO() {
		return operatorTO;
	}

	public void setOperatorTO(OperatorTO operatorTO) {
		this.operatorTO = operatorTO;
	}

	@Override
	public String toString() {
		return "ProviderTO [providerId=" + providerId + ", providerName=" + providerName + ", providerType="
				+ providerType + ", operatorTO=" + operatorTO + ", operatorId=" + operatorId + "]";
	}
	
	public void convertModelToTO(ProviderModel model) {
		if(Objects.nonNull(model.getProviderId())) {
			this.providerId = model.getProviderId().toString();
		}
		this.providerName = model.getProviderName();
		this.providerType =  model.getProviderType();
	}
	
	public void convertTOToModel(ProviderModel model) {
		if(StringUtils.isNotEmpty(this.getProviderId())) {
			model.setProviderId(Integer.valueOf(this.getProviderId()));
		}
		
		model.setProviderName(this.providerName);
		model.setProviderType(this.providerType);
	}

	public String getOperatorId() {
		return operatorId;
	}

	public void setOperatorId(String operatorId) {
		this.operatorId = operatorId;
	}
	
}
