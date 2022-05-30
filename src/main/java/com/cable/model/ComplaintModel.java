package com.cable.model;

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

import org.springframework.stereotype.Component;

@Entity
@Table(name="complaint")
@Component
public class ComplaintModel {

	@Id
	@GeneratedValue(generator = "complaint_id",strategy = GenerationType.AUTO)
	@Column(name = "complaint_id")
	private Integer complaintId;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "complaint_type_id")
	private ComplaintTypeModel complaintTypeModel;
	
	@Column(name = "complaint_status")
	private String complaintStatus;
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private UserModel userModel;
	
	@Column(name = "collector_comments")
	private String collectorComments;
	
	@Column(name= "customer_comments")
	private String customerComments;
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "collector_id")
	private UserModel collectorModel;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "operator_id")
	private OperatorModel operatorModel;

	public Integer getComplaintId() {
		return complaintId;
	}

	public void setComplaintId(Integer complaintId) {
		this.complaintId = complaintId;
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

	public ComplaintTypeModel getComplaintTypeModel() {
		return complaintTypeModel;
	}

	public void setComplaintTypeModel(ComplaintTypeModel complaintTypeModel) {
		this.complaintTypeModel = complaintTypeModel;
	}

	public UserModel getUserModel() {
		return userModel;
	}

	public void setUserModel(UserModel userModel) {
		this.userModel = userModel;
	}

	public UserModel getCollectorModel() {
		return collectorModel;
	}

	public void setCollectorModel(UserModel collectorModel) {
		this.collectorModel = collectorModel;
	}

	public OperatorModel getOperatorModel() {
		return operatorModel;
	}

	public void setOperatorModel(OperatorModel operatorModel) {
		this.operatorModel = operatorModel;
	}

	@Override
	public String toString() {
		return "ComplaintModel [complaintId=" + complaintId + ", complaintTypeModel=" + complaintTypeModel
				+ ", complaintStatus=" + complaintStatus + ", userModel=" + userModel + ", collectorComments="
				+ collectorComments + ", customerComments=" + customerComments + ", collectorModel=" + collectorModel
				+ ", operatorModel=" + operatorModel + "]";
	}
	
}
