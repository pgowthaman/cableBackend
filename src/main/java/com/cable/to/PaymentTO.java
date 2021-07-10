package com.cable.to;

import java.util.Date;
import java.util.Objects;

import org.springframework.stereotype.Component;

import com.cable.model.AreaModel;
import com.cable.model.ConnectionModel;
import com.cable.model.OperatorModel;
import com.cable.model.PaymentModel;
import com.cable.model.SetupboxModel;
import com.cable.model.UserModel;
import com.cable.utils.ConvertionUtils;
import com.cable.utils.StringUtils;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@Component
@JsonInclude(Include.NON_NULL)
public class PaymentTO {

	private String paymentId;

	private UserTO userTO;
	
	private String userId;

	private String amount;

	private String paidDate;

	private UserTO collectorTO;
	
	private String collectorId;

	private String paymentMode;

	private OperatorTO operatorTO;
	
	private String operatorId;

	private AreaTO areaTO;
	
	private String areaId;

	private String comment;

	private ConnectionTO connectionTO;
	
	private String connectionId;

	public String getPaymentId() {
		return paymentId;
	}

	public void setPaymentId(String paymentId) {
		this.paymentId = paymentId;
	}

	public UserTO getUserTO() {
		return userTO;
	}

	public void setUserTO(UserTO userTO) {
		this.userTO = userTO;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getPaidDate() {
		return paidDate;
	}

	public void setPaidDate(String paidDate) {
		this.paidDate = paidDate;
	}

	public UserTO getCollectorTO() {
		return collectorTO;
	}

	public void setCollectorTO(UserTO collectorTO) {
		this.collectorTO = collectorTO;
	}

	public String getPaymentMode() {
		return paymentMode;
	}

	public void setPaymentMode(String paymentMode) {
		this.paymentMode = paymentMode;
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

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public ConnectionTO getConnectionTO() {
		return connectionTO;
	}

	public void setConnectionTO(ConnectionTO connectionTO) {
		this.connectionTO = connectionTO;
	}

	@Override
	public String toString() {
		return "PaymentTO [paymentId=" + paymentId + ", userTO=" + userTO + ", userId=" + userId + ", amount=" + amount
				+ ", paidDate=" + paidDate + ", collectorTO=" + collectorTO + ", collectorId=" + collectorId
				+ ", paymentMode=" + paymentMode + ", operatorTO=" + operatorTO + ", operatorId=" + operatorId
				+ ", areaTO=" + areaTO + ", areaId=" + areaId + ", comment=" + comment + ", connectionTO="
				+ connectionTO + ", connectionId=" + connectionId + "]";
	}

	public void convertModelToTO(PaymentModel model) {
		if(Objects.nonNull(model.getPaymentId())) {
			this.paymentId = model.getPaymentId().toString();
		}

		if(Objects.nonNull(this.getUserTO())) {
			UserTO userTO = this.getUserTO();
			UserModel userModel = new UserModel();
			userTO.convertTOToModel(userModel);
			model.setUserModel(userModel);
		}
		if(Objects.nonNull(model.getAmount())) {
			this.amount = model.getAmount().toString();
		}
		if(Objects.nonNull(model.getPaidDate())) {
			this.paidDate = ConvertionUtils.convertDateTOString(model.getPaidDate());
		}

		if(Objects.nonNull(model.getCollectorModel())) {
			UserTO collectorTO = new UserTO();
			collectorTO.convertModelToTO(model.getCollectorModel());
			this.setCollectorTO(collectorTO);
		}
		this.paymentMode =  model.getPaymentMode();
		if(Objects.nonNull(model.getOperatorModel())) {
			OperatorTO operatorTO = new OperatorTO();
			operatorTO.convertModelToTO(model.getOperatorModel());
			this.setOperatorTO(operatorTO);
		}
		if(Objects.nonNull(this.getAreaTO())) {
			AreaTO areaTO = this.getAreaTO();
			AreaModel areaModel = new AreaModel();
			areaTO.convertTOToModel(areaModel);
			model.setAreaModel(areaModel);
		}
		this.comment = model.getComment();

		if(Objects.nonNull(this.getConnectionTO())) {
			ConnectionTO connectionTO = this.getConnectionTO();
			ConnectionModel connectionModel = new ConnectionModel();
			connectionTO.convertTOToModel(connectionModel);
			model.setConnectionModel(connectionModel);
		}
	}

	public void convertTOToModel(PaymentModel model) {
		if(StringUtils.isNotEmpty(this.getPaymentId())) {
			model.setPaymentId(Integer.valueOf(this.getPaymentId()));
		}

		if(Objects.nonNull(this.getUserTO())) {
			UserTO userTO = this.getUserTO();
			UserModel userModel = new UserModel();
			userTO.convertTOToModel(userModel);
			model.setUserModel(userModel);
		}

		if(StringUtils.isNotEmpty(this.amount)) {
			model.setAmount(Long.valueOf(this.amount));
		}
		if(StringUtils.isNotEmpty(this.paidDate)) {
			model.setPaidDate(ConvertionUtils.convertStringTODate(this.paidDate));
		}
		
		if(Objects.nonNull(this.getCollectorTO())) {
			 UserTO collectorTO = this.getCollectorTO();
			 UserModel collectorModel = new UserModel();
			 collectorTO.convertTOToModel(collectorModel);
			 model.setCollectorModel(collectorModel);
		 }
		model.setPaymentMode(this.paymentMode);
		
		if(Objects.nonNull(this.getAreaTO())) {
			AreaTO areaTO = this.getAreaTO();
			AreaModel areaModel = new AreaModel();
			areaTO.convertTOToModel(areaModel);
			model.setAreaModel(areaModel);
		}
		if(Objects.nonNull(this.getOperatorTO())) {
			OperatorTO operatorTO = this.getOperatorTO();
			OperatorModel operatorModel = new OperatorModel();
			operatorTO.convertTOToModel(operatorModel);
			model.setOperatorModel(operatorModel);
		}
		model.setComment(this.comment);
		if(Objects.nonNull(this.getConnectionTO())) {
			ConnectionTO connectionTO = this.getConnectionTO();
			ConnectionModel connectionModel = new ConnectionModel();
			connectionTO.convertTOToModel(connectionModel);
			model.setConnectionModel(connectionModel);
		}
		
		
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getCollectorId() {
		return collectorId;
	}

	public void setCollectorId(String collectorId) {
		this.collectorId = collectorId;
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

	public String getConnectionId() {
		return connectionId;
	}

	public void setConnectionId(String connectionId) {
		this.connectionId = connectionId;
	}

}
