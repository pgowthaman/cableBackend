package com.cable.to;

import java.util.Objects;

import org.springframework.stereotype.Component;

import com.cable.model.ComplaintModel;
import com.cable.model.ComplaintTypeModel;
import com.cable.model.OperatorModel;
import com.cable.model.UserModel;
import com.cable.utils.StringUtils;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@Component
@JsonInclude(Include.NON_NULL)
public class ComplaintTO {

	private String complaintId;
	
	private ComplaintTypeTO complaintTypeTO;
	
	private String complaintTypeId;
	
	private String complaintStatus;
	
	private UserTO userTO;
	
	private String userId;
	
	private String collectorComments;
	
	private String customerComments;
	
	private UserTO collectorTO;
	
	private String collectorId;
	
	private OperatorTO operatorTO;
	
	private String operatorId;

	public String getComplaintId() {
		return complaintId;
	}

	public void setComplaintId(String complaintId) {
		this.complaintId = complaintId;
	}

	public ComplaintTypeTO getComplaintTypeTO() {
		return complaintTypeTO;
	}

	public void setComplaintTypeTO(ComplaintTypeTO complaintTypeTO) {
		this.complaintTypeTO = complaintTypeTO;
	}

	public String getComplaintStatus() {
		return complaintStatus;
	}

	public void setComplaintStatus(String complaintStatus) {
		this.complaintStatus = complaintStatus;
	}

	public String getCollectorComments() {
		return collectorComments;
	}

	public void setCollectorComments(String collectorComments) {
		this.collectorComments = collectorComments;
	}

	public String getCustomerComments() {
		return customerComments;
	}

	public void setCustomerComments(String customerComments) {
		this.customerComments = customerComments;
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

	public UserTO getCollectorTO() {
		return collectorTO;
	}

	public void setCollectorTO(UserTO collectorTO) {
		this.collectorTO = collectorTO;
	}
	
	public String getComplaintTypeId() {
		return complaintTypeId;
	}

	public void setComplaintTypeId(String complaintTypeId) {
		this.complaintTypeId = complaintTypeId;
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

	@Override
	public String toString() {
		return "ComplaintTO [complaintId=" + complaintId + ", complaintTypeTO=" + complaintTypeTO + ", complaintTypeId="
				+ complaintTypeId + ", complaintStatus=" + complaintStatus + ", userTO=" + userTO + ", userId=" + userId
				+ ", collectorComments=" + collectorComments + ", customerComments=" + customerComments
				+ ", collectorTO=" + collectorTO + ", collectorId=" + collectorId + ", operatorTO=" + operatorTO
				+ ", operatorId=" + operatorId + "]";
	}
	
	public void convertModelToTO(ComplaintModel model) {
		if(Objects.nonNull(model.getComplaintId())) {
			this.complaintId = model.getComplaintId().toString();
		}
		
		if(Objects.nonNull(model.getComplaintTypeModel())) {
			ComplaintTypeTO complaintTypeTO = new ComplaintTypeTO();
			complaintTypeTO.convertModelToTO(model.getComplaintTypeModel());
			this.setComplaintTypeTO(complaintTypeTO);
		}
		this.complaintStatus = model.getComplaintStatus();
		
		if(Objects.nonNull(model.getUserModel())) {
			UserTO userTO = new UserTO();
			userTO.convertModelToTO(model.getUserModel());
			this.setUserTO(userTO);
		}
		
		this.collectorComments = model.getCollectorComments();
		this.customerComments = model.getCustomerComments();
		
		if(Objects.nonNull(model.getCollectorModel())) {
			UserTO collectorTO = new UserTO();
			collectorTO.convertModelToTO(model.getCollectorModel());
			this.setCollectorTO(collectorTO);
		}
		if(Objects.nonNull(model.getOperatorModel())) {
			OperatorTO operatorTO = new OperatorTO();
			operatorTO.convertModelToTO(model.getOperatorModel());
			this.setOperatorTO(operatorTO);
		}
	}
	
	public void convertTOToModel(ComplaintModel model) {
		if(StringUtils.isNotEmpty(this.getComplaintId())) {
			model.setComplaintId(Integer.valueOf(this.getComplaintId()));
		}
		if(Objects.nonNull(this.getComplaintTypeTO())) {
			 ComplaintTypeTO complaintTypeTO = this.getComplaintTypeTO();
			 ComplaintTypeModel complaintTypeModel = new ComplaintTypeModel();
			 complaintTypeTO.convertTOToModel(complaintTypeModel);
			 model.setComplaintTypeModel(complaintTypeModel);
		 }
		model.setComplaintStatus(this.complaintStatus);
		if(Objects.nonNull(this.getUserTO())) {
			 UserTO userTO = this.getUserTO();
			 UserModel userModel = new UserModel();
			 userTO.convertTOToModel(userModel);
			 model.setUserModel(userModel);
		 }
		model.setCollectorComments(this.collectorComments);
		model.setCustomerComments(this.customerComments);
		if(Objects.nonNull(this.getCollectorTO())) {
			 UserTO collectorTO = this.getCollectorTO();
			 UserModel collectorModel = new UserModel();
			 collectorTO.convertTOToModel(collectorModel);
			 model.setCollectorModel(collectorModel);
		 }
		 if(Objects.nonNull(this.getOperatorTO())) {
			 OperatorTO operatorTO = this.getOperatorTO();
			 OperatorModel operatorModel = new OperatorModel();
			 operatorTO.convertTOToModel(operatorModel);
			 model.setOperatorModel(operatorModel);
		 }
	}
}
