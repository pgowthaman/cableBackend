package com.cable.searchmodel;

public class SearchComplaintTypeModel {
	
	private Integer complaintTypeId;
	
	private String complaintTypeDesc;

	public String getComplaintTypeDesc() {
		return complaintTypeDesc;
	}

	public void setComplaintTypeDesc(String complaintTypeDesc) {
		this.complaintTypeDesc = complaintTypeDesc;
	}

	public Integer getComplaintTypeId() {
		return complaintTypeId;
	}

	public void setComplaintTypeId(Integer complaintTypeId) {
		this.complaintTypeId = complaintTypeId;
	}

	@Override
	public String toString() {
		return "SearchComplaintTypeModel [complaintTypeId=" + complaintTypeId + ", complaintTypeDesc="
				+ complaintTypeDesc + "]";
	}
	

}
