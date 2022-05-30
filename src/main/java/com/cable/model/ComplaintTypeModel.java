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
@Table(name="complaint_type")
@Component
public class ComplaintTypeModel {
	
	@Id
	@GeneratedValue(generator = "complaint_type_id",strategy = GenerationType.AUTO)
	@Column(name = "complaint_type_id")
	private Integer complaintTypeId;
	
	@Column(name="complaint_type_desc")
	private String complaintTypeDesc;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "operator_id")
	private OperatorModel operatorModel;

	public Integer getComplaintTypeId() {
		return complaintTypeId;
	}

	public void setComplaintTypeId(Integer complaintTypeId) {
		this.complaintTypeId = complaintTypeId;
	}

	public String getComplaintTypeDesc() {
		return complaintTypeDesc;
	}

	public void setComplaintTypeDesc(String complaintTypeDesc) {
		this.complaintTypeDesc = complaintTypeDesc;
	}

	public OperatorModel getOperatorModel() {
		return operatorModel;
	}

	public void setOperatorModel(OperatorModel operatorModel) {
		this.operatorModel = operatorModel;
	}

	@Override
	public String toString() {
		return "ComplaintTypeModel [complaintTypeId=" + complaintTypeId + ", complaintTypeDesc=" + complaintTypeDesc
				+ ", operatorModel=" + operatorModel + "]";
	}
	

}
