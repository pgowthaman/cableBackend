package com.cable.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.stereotype.Component;

@Entity	
@Table(name = "operator")
@Component
public class OperatorModel {

	@Id
	@GeneratedValue(generator = "operator_id" ,strategy = GenerationType.AUTO)
	@Column(name = "operator_id")
	private Integer operatorId;
	
	@Column(name= "operator_name")
	private String operatorName;

	public Integer getOperatorId() {
		return operatorId;
	}

	public void setOperatorId(Integer operatorId) {
		this.operatorId = operatorId;
	}

	public String getOperatorName() {
		return operatorName;
	}

	public void setOperatorName(String operatorName) {
		this.operatorName = operatorName;
	}

	@Override
	public String toString() {
		return "OperatorModel [operatorId=" + operatorId + ", operatorName=" + operatorName
				+ "]";
	}
	
}
