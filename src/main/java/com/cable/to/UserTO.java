package com.cable.to;

import java.io.Serializable;
import java.util.Objects;

import org.springframework.stereotype.Component;

import com.cable.model.OperatorModel;
import com.cable.model.UserModel;
import com.cable.model.UserRoleModel;
import com.cable.utils.StringUtils;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@Component
public class UserTO implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String userId;
	
	private String phoneNumber;
	
	private String firebaseId;
	
	private String firebaseToken;
	
	private String userRoleId;
	
	private String username;
	
	private String operatorId;
		
	private UserRoleTO userRoleTO;
	
	private OperatorTO operatorTO;
	
	@JsonIgnore
	private String password;
	

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getFirebaseId() {
		return firebaseId;
	}

	public void setFirebaseId(String firebaseId) {
		this.firebaseId = firebaseId;
	}

	public String getFirebaseToken() {
		return firebaseToken;
	}

	public void setFirebaseToken(String firebaseToken) {
		this.firebaseToken = firebaseToken;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Override
	public String toString() {
		return "UserTO [userId=" + userId + ", phoneNumber=" + phoneNumber + ", firebaseId=" + firebaseId
				+ ", firebaseToken=" + firebaseToken + ", userRoleId=" + userRoleId + ", username=" + username
				+ ", operatorId=" + operatorId + ", userRoleTO=" + userRoleTO + ", operatorTO=" + operatorTO
				+ ", password=" + password + "]";
	}

	public String getUserRoleId() {
		return userRoleId;
	}

	public void setUserRoleId(String userRoleId) {
		this.userRoleId = userRoleId;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public UserRoleTO getUserRoleTO() {
		return userRoleTO;
	}

	public void setUserRoleTO(UserRoleTO userRoleTO) {
		this.userRoleTO = userRoleTO;
	}

	public String getPassword() {
		return password;
	}
	
	public OperatorTO getOperatorTO() {
		return operatorTO;
	}

	public void setOperatorTO(OperatorTO operatorTO) {
		this.operatorTO = operatorTO;
	}

	public String getOperatorId() {
		return operatorId;
	}

	public void setOperatorId(String operatorId) {
		this.operatorId = operatorId;
	}

	public void convertModelToTO(UserModel model) {
		if(Objects.nonNull(model.getUserId())) {
			this.userId = model.getUserId().toString();
		}
		if(Objects.nonNull(model.getPhoneNumber())) {
			this.phoneNumber =  model.getPhoneNumber().toString();
		}
		this.firebaseId = model.getFirebaseId();
		this.firebaseToken = model.getFirebaseToken();
		 this.username = model.getUsername();
		 if(Objects.nonNull(model.getOperatorModel())) {
			OperatorTO operatorTO = new OperatorTO();
			operatorTO.convertModelToTO(model.getOperatorModel());
			this.setOperatorTO(operatorTO);
		 }
		this.password = model.getPassword();
		if(Objects.nonNull(model.getUserRoleModel())) {
			UserRoleTO roleTO = new UserRoleTO();
			roleTO.convertModelToTO(model.getUserRoleModel());
			this.setUserRoleTO(roleTO);
		}
	}
	
	public void convertTOToModel(UserModel model) {
		if(StringUtils.isNotEmpty(this.getUserId())) {
			model.setUserId(Integer.valueOf(this.getUserId()));
		}
		if(StringUtils.isNotEmpty(this.getPhoneNumber())) {
			model.setPhoneNumber(Long.valueOf(this.getPhoneNumber()));
		}
		model.setFirebaseId(this.firebaseId);
		model.setFirebaseToken(this.firebaseToken);
		model.setUsername(this.username);
		 if(Objects.nonNull(this.getOperatorTO())) {
			 OperatorTO operatorTO = this.getOperatorTO();
			 OperatorModel operatorModel = new OperatorModel();
			 operatorTO.convertTOToModel(operatorModel);
			 model.setOperatorModel(operatorModel);
		 }
		model.setPassword(this.password);
		if(Objects.nonNull(this.getUserRoleTO())) {
			UserRoleTO roleTO = this.userRoleTO;
			UserRoleModel roleModel =  new UserRoleModel();
			roleTO.convertTOToModel(roleModel);
			model.setUserRoleModel(roleModel);
		}
	}
	
}
