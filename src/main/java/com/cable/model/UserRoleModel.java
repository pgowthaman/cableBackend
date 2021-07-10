package com.cable.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.stereotype.Component;

@Entity	
@Table(name = "user_roles")
@Component
public class UserRoleModel implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "user_role_id",strategy = GenerationType.AUTO)
	@Column(name = "user_role_id")
	private Integer userRoleId;
	
	@Column(name = "user_role_desc")
	private String userRoleDesc;

	public Integer getUserRoleId() {
		return userRoleId;
	}

	public void setUserRoleId(Integer userRoleId) {
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
		return "UserRolesModel [userRoleId=" + userRoleId + ", userRoleDesc=" + userRoleDesc + "]";
	}

}
