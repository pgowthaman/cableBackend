package com.cable.to;

import java.util.Objects;

import org.springframework.stereotype.Component;

import com.cable.model.AreaModel;
import com.cable.model.OperatorModel;
import com.cable.model.ProviderModel;
import com.cable.model.SetupboxModel;
import com.cable.utils.StringUtils;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@Component
@JsonInclude(Include.NON_NULL)
public class SetupboxTO {

	private String setupboxId;

	private OperatorTO operatorTO;
	
	private String operatorId;

	private String connectionStatus;

	private String setupboxStatus;

	private String setupboxType;

	private ProviderTO providerTO;
	
	private String providerId;

	public String getSetupboxId() {
		return setupboxId;
	}

	public void setSetupboxId(String setupboxId) {
		this.setupboxId = setupboxId;
	}

	public OperatorTO getOperatorTO() {
		return operatorTO;
	}

	public void setOperatorTO(OperatorTO operatorTO) {
		this.operatorTO = operatorTO;
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

	public ProviderTO getProviderTO() {
		return providerTO;
	}

	public void setProviderTO(ProviderTO providerTO) {
		this.providerTO = providerTO;
	}

	@Override
	public String toString() {
		return "SetupboxTO [setupboxId=" + setupboxId + ", operatorTO=" + operatorTO + ", operatorId=" + operatorId
				+ ", connectionStatus=" + connectionStatus + ", setupboxStatus=" + setupboxStatus + ", setupboxType="
				+ setupboxType + ", providerTO=" + providerTO + ", providerId=" + providerId + "]";
	}

	public void convertModelToTO(SetupboxModel model) {
		if(Objects.nonNull(model.getSetupboxId())) {
			this.setupboxId = model.getSetupboxId().toString();
		}
		if(Objects.nonNull(model.getOperatorModel())) {
			OperatorTO operatorTO = new OperatorTO();
			operatorTO.convertModelToTO(model.getOperatorModel());
			this.setOperatorTO(operatorTO);
		}
		this.connectionStatus = model.getConnectionStatus();
		this.setupboxStatus = model.getSetupboxStatus();
		this.setupboxType = model.getSetupboxType();
		if(Objects.nonNull(model.getProviderModel())) {
			ProviderTO providerTO = new ProviderTO();
			providerTO.convertModelToTO(model.getProviderModel());
			this.setProviderTO(providerTO);
		}
	}

	public void convertTOToModel(SetupboxModel model) {
		if(StringUtils.isNotEmpty(this.getSetupboxId())) {
			model.setSetupboxId(Integer.valueOf(this.getSetupboxId()));
		}
		if(Objects.nonNull(this.getOperatorTO())) {
			OperatorTO operatorTO = this.getOperatorTO();
			OperatorModel operatorModel = new OperatorModel();
			operatorTO.convertTOToModel(operatorModel);
			model.setOperatorModel(operatorModel);
		}
		model.setConnectionStatus(this.connectionStatus);
		model.setSetupboxStatus(this.setupboxStatus);
		model.setSetupboxType(this.setupboxType);
		if(Objects.nonNull(this.getProviderTO())) {
			ProviderTO providerTO = this.getProviderTO();
			ProviderModel providerModel = new ProviderModel();
			providerTO.convertTOToModel(providerModel);
			model.setProviderModel(providerModel);
		}
	}

	public String getOperatorId() {
		return operatorId;
	}

	public void setOperatorId(String operatorId) {
		this.operatorId = operatorId;
	}

	public String getProviderId() {
		return providerId;
	}

	public void setProviderId(String providerId) {
		this.providerId = providerId;
	}

}
