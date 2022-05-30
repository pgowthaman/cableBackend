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
@Table(name = "area")
@Component
public class AreaModel{

	@Id
	@GeneratedValue(generator = "area_id",strategy = GenerationType.AUTO)
	@Column(name = "area_id")
	private Integer areaId;
	
	@Column(name ="area_name")
	private String areaName;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "operator_id")
	private OperatorModel operatorModel;

	public Integer getAreaId() {
		return areaId;
	}

	public void setAreaId(Integer areaId) {
		this.areaId = areaId;
	}

	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	public OperatorModel getOperatorModel() {
		return operatorModel;
	}

	public void setOperatorModel(OperatorModel operatorModel) {
		this.operatorModel = operatorModel;
	}

	@Override
	public String toString() {
		return "AreaModel [areaId=" + areaId + ", areaName=" + areaName + ", operatorModel=" + operatorModel + "]";
	}
	
	
}
