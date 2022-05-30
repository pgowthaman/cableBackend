package com.cable.model;

import java.util.Date;

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
@Table(name="payment")
@Component
public class PaymentModel {
	
	@Id
	@GeneratedValue(generator = "payment_id",strategy = GenerationType.AUTO)
	@Column(name = "payment_id")
	private Integer paymentId;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private UserModel userModel;
	
	@Column(name = "amount")
	private Long amount;
	
	@Column(name= "paid_date")
	private Date paidDate;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "collector_id")
	private UserModel collectorModel;
	
	@Column(name= "payment_mode")
	private String paymentMode;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "operator_id")
	private OperatorModel operatorModel;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "area_id")
	private AreaModel areaModel;
	
	@Column(name="comment")
	private String comment;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="connection_id")
	private ConnectionModel connectionModel;

	public Integer getPaymentId() {
		return paymentId;
	}

	public void setPaymentId(Integer paymentId) {
		this.paymentId = paymentId;
	}

	public UserModel getUserModel() {
		return userModel;
	}

	public void setUserModel(UserModel userModel) {
		this.userModel = userModel;
	}

	public Long getAmount() {
		return amount;
	}

	public void setAmount(Long amount) {
		this.amount = amount;
	}

	public Date getPaidDate() {
		return paidDate;
	}

	public void setPaidDate(Date paidDate) {
		this.paidDate = paidDate;
	}

	public UserModel getCollectorModel() {
		return collectorModel;
	}

	public void setCollectorModel(UserModel collectorModel) {
		this.collectorModel = collectorModel;
	}

	public String getPaymentMode() {
		return paymentMode;
	}

	public void setPaymentMode(String paymentMode) {
		this.paymentMode = paymentMode;
	}

	public OperatorModel getOperatorModel() {
		return operatorModel;
	}

	public void setOperatorModel(OperatorModel operatorModel) {
		this.operatorModel = operatorModel;
	}

	public AreaModel getAreaModel() {
		return areaModel;
	}

	public void setAreaModel(AreaModel areaModel) {
		this.areaModel = areaModel;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public ConnectionModel getConnectionModel() {
		return connectionModel;
	}

	public void setConnectionModel(ConnectionModel connectionModel) {
		this.connectionModel = connectionModel;
	}

	@Override
	public String toString() {
		return "PaymentModel [paymentId=" + paymentId + ", userModel=" + userModel + ", amount=" + amount
				+ ", paidDate=" + paidDate + ", collectorModel=" + collectorModel + ", paymentMode=" + paymentMode
				+ ", operatorModel=" + operatorModel + ", areaModel=" + areaModel + ", comment=" + comment
				+ ", connectionModel=" + connectionModel + "]";
	}
	
	
	
}
