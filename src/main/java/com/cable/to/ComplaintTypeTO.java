package com.cable.to;

import java.util.Objects;

import org.springframework.stereotype.Component;

import com.cable.model.ComplaintTypeModel;
import com.cable.model.OperatorModel;
import com.cable.utils.StringUtils;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@Component
@JsonInclude(Include.NON_NULL)
public class ComplaintTypeTO {
	
	private String complaintTypeId;
	
	private String complaintTypeDesc;
	
	private OperatorTO operatorTO;
	
	private String operatorId;

	public String getComplaintTypeId() {
		return complaintTypeId;
	}

	public void setComplaintTypeId(String complaintTypeId) {
		this.complaintTypeId = complaintTypeId;
	}

	public String getComplaintTypeDesc() {
		return complaintTypeDesc;
	}

	public void setComplaintTypeDesc(String complaintTypeDesc) {
		this.complaintTypeDesc = complaintTypeDesc;
	}

	public OperatorTO getOperatorTO() {
		return operatorTO;
	}

	public void setOperatorTO(OperatorTO operatorTO) {
		this.operatorTO = operatorTO;
	}

	@Override
	public String toString() {
		return "ComplaintTypeTO [complaintTypeId=" + complaintTypeId + ", complaintTypeDesc=" + complaintTypeDesc
				+ ", operatorTO=" + operatorTO + ", operatorId=" + operatorId + "]";
	}
	
	public void convertModelToTO(ComplaintTypeModel model) {
		if(Objects.nonNull(model.getComplaintTypeId())) {
			this.complaintTypeId = model.getComplaintTypeId().toString();
		}
		this.complaintTypeDesc = model.getComplaintTypeDesc();
		 if(Objects.nonNull(model.getOperatorModel())) {
				OperatorTO operatorTO = new OperatorTO();
				operatorTO.convertModelToTO(model.getOperatorModel());
				this.setOperatorTO(operatorTO);
			 }
	}
	
	public void convertTOToModel(ComplaintTypeModel model) {
		if(StringUtils.isNotEmpty(this.getComplaintTypeId())) {
			model.setComplaintTypeId(Integer.valueOf(this.getComplaintTypeId()));
		}
		
		model.setComplaintTypeDesc(this.complaintTypeDesc);
		if(Objects.nonNull(this.getOperatorTO())) {
			 OperatorTO operatorTO = this.getOperatorTO();
			 OperatorModel operatorModel = new OperatorModel();
			 operatorTO.convertTOToModel(operatorModel);
			 model.setOperatorModel(operatorModel);
		 }
	}

	public String getOperatorId() {
		return operatorId;
	}

	public void setOperatorId(String operatorId) {
		this.operatorId = operatorId;
	}
	

}
