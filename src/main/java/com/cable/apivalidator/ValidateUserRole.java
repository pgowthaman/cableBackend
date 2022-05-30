package com.cable.apivalidator;

import javax.validation.Valid;

import org.springframework.stereotype.Service;

import com.cable.dao.UserRoleDao;
import com.cable.to.UserRoleTO;
import com.cable.utils.StringUtils;

@Service
public class ValidateUserRole {
	
	private UserRoleDao userRoleDao;
	
	public ValidateUserRole(UserRoleDao userRoleDao){
		this.userRoleDao = userRoleDao;
	}

	public void checkUserRole(@Valid UserRoleTO userRoleTO) throws CableException {
		
		if(userRoleTO==null) {
			throw new CableException("User Role Object cannot be empty");
		}else if(StringUtils.isEmpty(userRoleTO.getUserRoleId())) {
			throw new CableException("User Role ID cannot be empty");
		}else {
			UserRoleTO userRoleTOnew = userRoleDao.getByUserRoleId(userRoleTO.getUserRoleId());
			if(userRoleTOnew==null) {
				throw new CableException("Given User Role Is Not Valid");
			}
		}
	}
	
	

}
