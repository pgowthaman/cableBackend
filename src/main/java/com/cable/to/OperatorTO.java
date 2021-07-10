package com.cable.to;

import java.io.Serializable;
import java.util.Objects;

import org.springframework.stereotype.Component;

import com.cable.model.OperatorModel;
import com.cable.utils.StringUtils;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@Component
@JsonInclude(Include.NON_NULL)
public class OperatorTO  implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String operatorId;
	
	private String operatorName;

	public String getOperatorId() {
		return operatorId;
	}

	public void setOperatorId(String operatorId) {
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
	
	public void convertModelToTO(OperatorModel operatorModel) {
		if(Objects.nonNull(operatorModel.getOperatorId())) {
			this.operatorId = operatorModel.getOperatorId().toString();
		}
		this.operatorName = operatorModel.getOperatorName();
	}

	public void convertTOToModel(OperatorModel operatorModel) {
		
		if(StringUtils.isNotEmpty(this.getOperatorId())) {
			operatorModel.setOperatorId(Integer.valueOf(this.getOperatorId()));
		}
		operatorModel.setOperatorName(this.getOperatorName());
	}


}
