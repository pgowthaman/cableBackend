package com.cable.to;

import java.io.Serializable;
import java.util.Objects;

import org.springframework.stereotype.Component;

import com.cable.model.UserRoleModel;
import com.cable.utils.StringUtils;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@Component
@JsonInclude(Include.NON_NULL)
public class UserRoleTO implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String userRoleId;
	
	private String userRoleDesc;

	public String getUserRoleId() {
		return userRoleId;
	}

	public void setUserRoleId(String userRoleId) {
		this.userRoleId = userRoleId;
	}

	public String getUserRoleDesc() {
		return userRoleDesc;
	}

	public void setUserRoleDesc(String userRoleDesc) {
		this.userRoleDesc = userRoleDesc;
	}

	@Override
	public String toString() {
		return "UserRoleTO [userRoleId=" + userRoleId + ", userRoleDesc=" + userRoleDesc + "]";
	}

	public void convertModelToTO(UserRoleModel userRoleModel) {
		if(Objects.nonNull(userRoleModel.getUserRoleId())) {
			this.userRoleId = userRoleModel.getUserRoleId().toString();
		}
		this.userRoleDesc = userRoleModel.getUserRoleDesc();
	}

	public void convertTOToModel(UserRoleModel roleModel) {
		
		if(StringUtils.isNotEmpty(this.getUserRoleId())) {
			roleModel.setUserRoleId(Integer.valueOf(this.getUserRoleId()));
		}
		roleModel.setUserRoleDesc(this.userRoleDesc);
	}

}
