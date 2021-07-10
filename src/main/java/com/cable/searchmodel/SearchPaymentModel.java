package com.cable.searchmodel;

import java.util.Date;

public class SearchPaymentModel {
	
	private Integer paymentId;
	
	private Integer userId;
	
	private Double amount;
	
	private Date paidDate;
	
	private Integer collectorId;
	
	private String paymentMode;
	
	private Integer operatorId;
	
	private Integer areaId;
	
	private String comment;
	
	private Integer connectionId;
	
	private String strPaidDate;

	public Integer getPaymentId() {
		return paymentId;
	}

	public void setPaymentId(Integer paymentId) {
		this.paymentId = paymentId;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public Date getPaidDate() {
		return paidDate;
	}

	public void setPaidDate(Date paidDate) {
		this.paidDate = paidDate;
	}

	public Integer getCollectorId() {
		return collectorId;
	}

	public void setCollectorId(Integer collectorId) {
		this.collectorId = collectorId;
	}

	public String getPaymentMode() {
		return paymentMode;
	}

	public void setPaymentMode(String paymentMode) {
		this.paymentMode = paymentMode;
	}

	public Integer getOperatorId() {
		return operatorId;
	}

	public void setOperatorId(Integer operatorId) {
		this.operatorId = operatorId;
	}

	public Integer getAreaId() {
		return areaId;
	}

	public void setAreaId(Integer areaId) {
		this.areaId = areaId;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public Integer getConnectionId() {
		return connectionId;
	}

	public void setConnectionId(Integer connectionId) {
		this.connectionId = connectionId;
	}

	public String getStrPaidDate() {
		return strPaidDate;
	}

	public void setStrPaidDate(String strPaidDate) {
		this.strPaidDate = strPaidDate;
	}

	@Override
	public String toString() {
		return "SearchPaymentModel [paymentId=" + paymentId + ", userId=" + userId + ", amount=" + amount
				+ ", paidDate=" + paidDate + ", collectorId=" + collectorId + ", paymentMode=" + paymentMode
				+ ", operatorId=" + operatorId + ", areaId=" + areaId + ", comment=" + comment + ", connectionId="
				+ connectionId + ", strPaidDate=" + strPaidDate + "]";
	}
	
	
}
