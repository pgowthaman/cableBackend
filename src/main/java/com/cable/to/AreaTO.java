package com.cable.to;

import java.util.Objects;

import org.springframework.stereotype.Component;

import com.cable.model.AreaModel;
import com.cable.model.OperatorModel;
import com.cable.utils.StringUtils;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@Component
@JsonInclude(Include.NON_NULL)
public class AreaTO {
	
	private String areaId;
	
	private String areaName;
	
	private OperatorTO operatorTO;
	
	private String operatorId;

	public String getAreaId() {
		return areaId;
	}

	public void setAreaId(String areaId) {
		this.areaId = areaId;
	}

	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	public OperatorTO getOperatorTO() {
		return operatorTO;
	}

	public void setOperatorTO(OperatorTO operatorTO) {
		this.operatorTO = operatorTO;
	}

	@Override
	public String toString() {
		return "AreaTO [areaId=" + areaId + ", areaName=" + areaName + ", operatorTO=" + operatorTO + ", operatorId="
				+ operatorId + "]";
	}
	
	public void convertModelToTO(AreaModel model) {
		if(Objects.nonNull(model.getAreaId())) {
			this.areaId = model.getAreaId().toString();
		}
		this.areaName = model.getAreaName();
		 if(Objects.nonNull(model.getOperatorModel())) {
			OperatorTO operatorTO = new OperatorTO();
			operatorTO.convertModelToTO(model.getOperatorModel());
			this.setOperatorTO(operatorTO);
		 }
	}
	
	public void convertTOToModel(AreaModel model) {
		if(StringUtils.isNotEmpty(this.getAreaId())) {
			model.setAreaId(Integer.valueOf(this.getAreaId()));
		}
		
		model.setAreaName(this.areaName);
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
