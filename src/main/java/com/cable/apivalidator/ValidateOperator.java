package com.cable.apivalidator;

import javax.validation.Valid;

import org.springframework.stereotype.Service;

import com.cable.dao.OperatorDao;
import com.cable.to.OperatorTO;
import com.cable.utils.StringUtils;

@Service
public class ValidateOperator {
	
	private OperatorDao operatorDao;
	
	public ValidateOperator(OperatorDao operatorDao){
		this.operatorDao = operatorDao;
	}

	public void checkOperator(@Valid OperatorTO operatorTO) throws CableException {
		
		if(operatorTO==null) {
			throw new CableException("Operator Object cannot be empty");
		}else if(StringUtils.isEmpty(operatorTO.getOperatorId())) {
			throw new CableException("Operator ID cannot be empty");
		}else {
			OperatorTO operatorTONew = operatorDao.getByOperatorId(operatorTO.getOperatorId());
			if(operatorTONew==null) {
				throw new CableException("Given Operator Is Not Valid");
			}
		}
	}
	
	

}
