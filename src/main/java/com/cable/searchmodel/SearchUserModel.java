package com.cable.searchmodel;

public class SearchUserModel {
	
	private Integer userId;
	
	private String phoneNumber;
	
	private String firebaseId;
	
	private String firebaseToken;
	
	private String userRole;
	
	private String username;
	
	private Integer operatorId;

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
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

	public String getUserRole() {
		return userRole;
	}

	public void setUserRole(String userRole) {
		this.userRole = userRole;
	}

	public String getUserName() {
		return username;
	}

	public void setUserName(String userName) {
		this.username = userName;
	}

	public Integer getOperatorId() {
		return operatorId;
	}

	public void setOperatorId(Integer operatorId) {
		this.operatorId = operatorId;
	}

	@Override
	public String toString() {
		return "UserModel [userId=" + userId + ", phoneNumber=" + phoneNumber + ", firebaseId=" + firebaseId
				+ ", firebaseToken=" + firebaseToken + ", userRole=" + userRole + ", userName=" + username
				+ ", operatorId=" + operatorId + "]";
	}
	
}
