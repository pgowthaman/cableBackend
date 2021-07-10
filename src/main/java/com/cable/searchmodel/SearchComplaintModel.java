package com.cable.searchmodel;

public class SearchComplaintModel {

	private Integer complaintId;
	
	private Integer complaintTypeId;
	
	private String complaintStatus;
	
	private Integer userId;
	
	private String collectorComments;
	
	private String customerComments;
	
	private Integer collecterId;


	public Integer getComplaintId() {
		return complaintId;
	}

	public void setComplaintId(Integer complaintId) {
		this.complaintId = complaintId;
	}

	public Integer getComplaintTypeId() {
		return complaintTypeId;
	}

	public void setComplaintTypeId(Integer complaintTypeId) {
		this.complaintTypeId = complaintTypeId;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
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

	public String getComplaintStatus() {
		return complaintStatus;
	}

	public void setComplaintStatus(String complaintStatus) {
		this.complaintStatus = complaintStatus;
	}

	public Integer getCollecterId() {
		return collecterId;
	}

	public void setCollecterId(Integer collecterId) {
		this.collecterId = collecterId;
	}

	@Override
	public String toString() {
		return "SearchComplaintModel [complaintId=" + complaintId + ", complaintTypeId=" + complaintTypeId
				+ ", complaintStatus=" + complaintStatus + ", userId=" + userId + ", collectorComments="
				+ collectorComments + ", customerComments=" + customerComments + ", collecterId=" + collecterId + "]";
	}
	
	
	
}
