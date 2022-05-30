package com.cable.to;

import java.util.Objects;

import org.springframework.stereotype.Component;

import com.cable.model.AreaModel;
import com.cable.model.ConnectionModel;
import com.cable.model.OperatorModel;
import com.cable.model.SetupboxModel;
import com.cable.model.UserModel;
import com.cable.utils.ConvertionUtils;
import com.cable.utils.StringUtils;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@Component
@JsonInclude(Include.NON_NULL)
public class ConnectionTO {

	private String connectionId;

	private UserTO userTO;
	
	private String userId;

	private OperatorTO operatorTO;
	
	private String operatorId;

	private AreaTO areaTO;
	
	private String areaId;

	private SetupboxTO setupboxTO;
	
	private String setupboxId;

	private String connectionStatus;

	private String connectionNumber;

	private String connectionDate;

	private String dueAmount;

	public String getConnectionId() {
		return connectionId;
	}

	public void setConnectionId(String connectionId) {
		this.connectionId = connectionId;
	}

	public UserTO getUserTO() {
		return userTO;
	}

	public void setUserTO(UserTO userTO) {
		this.userTO = userTO;
	}

	public OperatorTO getOperatorTO() {
		return operatorTO;
	}

	public void setOperatorTO(OperatorTO operatorTO) {
		this.operatorTO = operatorTO;
	}

	public AreaTO getAreaTO() {
		return areaTO;
	}

	public void setAreaTO(AreaTO areaTO) {
		this.areaTO = areaTO;
	}

	public SetupboxTO getSetupboxTO() {
		return setupboxTO;
	}

	public void setSetupboxTO(SetupboxTO setupboxTO) {
		this.setupboxTO = setupboxTO;
	}

	public String getConnectionStatus() {
		return connectionStatus;
	}

	public void setConnectionStatus(String connectionStatus) {
		this.connectionStatus = connectionStatus;
	}

	public String getConnectionNumber() {
		return connectionNumber;
	}

	public void setConnectionNumber(String connectionNumber) {
		this.connectionNumber = connectionNumber;
	}

	public String getConnectionDate() {
		return connectionDate;
	}

	public void setConnectionDate(String connectionDate) {
		this.connectionDate = connectionDate;
	}

	public String getDueAmount() {
		return dueAmount;
	}

	public void setDueAmount(String dueAmount) {
		this.dueAmount = dueAmount;
	}

	@Override
	public String toString() {
		return "ConnectionTO [connectionId=" + connectionId + ", userTO=" + userTO + ", userId=" + userId
				+ ", operatorTO=" + operatorTO + ", operatorId=" + operatorId + ", areaTO=" + areaTO + ", areaId="
				+ areaId + ", setupboxTO=" + setupboxTO + ", setupboxId=" + setupboxId + ", connectionStatus="
				+ connectionStatus + ", connectionNumber=" + connectionNumber + ", connectionDate=" + connectionDate
				+ ", dueAmount=" + dueAmount + "]";
	}

	public void convertModelToTO(ConnectionModel model) {
		if(Objects.nonNull(model.getConnectionId())) {
			this.connectionId = model.getConnectionId().toString();
		}
		if(Objects.nonNull(model.getUserModel())) {
			UserTO userTO = new UserTO();
			userTO.convertModelToTO(model.getUserModel());
			this.setUserTO(userTO);
		}
		if(Objects.nonNull(model.getOperatorModel())) {
			OperatorTO operatorTO = new OperatorTO();
			operatorTO.convertModelToTO(model.getOperatorModel());
			this.setOperatorTO(operatorTO);
		}
		if(Objects.nonNull(model.getAreaModel())) {
			AreaTO areaTO = new AreaTO();
			areaTO.convertModelToTO(model.getAreaModel());
			this.setAreaTO(areaTO);
		}
		if(Objects.nonNull(model.getSetupboxModel())) {
			SetupboxTO setupboxTO = new SetupboxTO();
			setupboxTO.convertModelToTO(model.getSetupboxModel());
			this.setSetupboxTO(setupboxTO);
		}
		this.connectionStatus = model.getConnectionStatus();
		if(Objects.nonNull(model.getConnectionNumber())) {
			this.connectionNumber = model.getConnectionNumber().toString();
		}
		if(Objects.nonNull(model.getConnectionDate())) {
			this.connectionDate = ConvertionUtils.convertDateTOString(model.getConnectionDate());
		}
		if(Objects.nonNull(model.getDueAmount())) {
			this.dueAmount = model.getDueAmount().toString();
		}
	}

	public void convertTOToModel(ConnectionModel model) {
		if(StringUtils.isNotEmpty(this.getConnectionId())) {
			model.setConnectionId(Integer.valueOf(this.getConnectionId()));
		}

		if(Objects.nonNull(this.getUserTO())) {
			UserTO userTO = this.getUserTO();
			UserModel userModel = new UserModel();
			userTO.convertTOToModel(userModel);
			model.setUserModel(userModel);
		}

		if(Objects.nonNull(this.getOperatorTO())) {
			OperatorTO operatorTO = this.getOperatorTO();
			OperatorModel operatorModel = new OperatorModel();
			operatorTO.convertTOToModel(operatorModel);
			model.setOperatorModel(operatorModel);
		}

		if(Objects.nonNull(this.getAreaTO())) {
			AreaTO areaTO = this.getAreaTO();
			AreaModel areaModel = new AreaModel();
			areaTO.convertTOToModel(areaModel);
			model.setAreaModel(areaModel);
		}

		if(Objects.nonNull(this.getSetupboxTO())) {
			SetupboxTO setupboxTO = this.getSetupboxTO();
			SetupboxModel setupboxModel = new SetupboxModel();
			setupboxTO.convertTOToModel(setupboxModel);
			model.setSetupboxModel(setupboxModel);
		}
		model.setConnectionStatus(this.connectionStatus);
		if(StringUtils.isNotEmpty(this.connectionNumber)) {
			model.setConnectionNumber(Integer.valueOf(this.connectionNumber));
		}
		if(StringUtils.isNotEmpty(this.connectionDate)) {
			model.setConnectionDate(ConvertionUtils.convertStringTODate(this.connectionDate));
		}
		if(StringUtils.isNotEmpty(this.dueAmount)) {
			model.setDueAmount(Integer.valueOf(this.dueAmount));
		}
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getOperatorId() {
		return operatorId;
	}

	public void setOperatorId(String operatorId) {
		this.operatorId = operatorId;
	}

	public String getAreaId() {
		return areaId;
	}

	public void setAreaId(String areaId) {
		this.areaId = areaId;
	}

	public String getSetupboxId() {
		return setupboxId;
	}

	public void setSetupboxId(String setupboxId) {
		this.setupboxId = setupboxId;
	}


}
