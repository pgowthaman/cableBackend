package com.cable.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity	
@Table(name = "customer")
@Component
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class UserModel implements UserDetails, Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "user_id",strategy = GenerationType.AUTO)
	@Column(name = "user_id")
	private Integer userId;
	
	@Column(name = "phone_number")
	private Long phoneNumber;
	
	@Column(name = "firebase_id")
	private String firebaseId;
	
	@Column(name = "firebase_token")
	private String firebaseToken;
	
	@Column(name = "user_name")
	private String username;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_role_id")
	private UserRoleModel userRoleModel;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "operator_id")
	private OperatorModel operatorModel;
	
	@Transient
	private String password;
	

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Long getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(Long phoneNumber) {
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

	@Override
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public OperatorModel getOperatorModel() {
		return operatorModel;
	}

	public void setOperatorModel(OperatorModel operatorModel) {
		this.operatorModel = operatorModel;
	}

	@Override
	public String toString() {
		return "UserModel [userId=" + userId + ", phoneNumber=" + phoneNumber + ", firebaseId=" + firebaseId
				+ ", firebaseToken=" + firebaseToken + ", username=" + username + ", userRoleModel=" + userRoleModel
				+ ", operatorModel=" + operatorModel + ", password=" + password + "]";
	}

	public UserRoleModel getUserRoleModel() {
		return userRoleModel;
	}

	public void setUserRoleModel(UserRoleModel userRoleModel) {
		this.userRoleModel = userRoleModel;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		List<GrantedAuthority> list = new ArrayList<GrantedAuthority>();
		if(Objects.nonNull(userRoleModel) && Objects.nonNull(userRoleModel.getUserRoleDesc())) {
			list.add(new SimpleGrantedAuthority("ROLE_" + userRoleModel.getUserRoleDesc()));
		}
		return list;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
	

	public void setPassword(String password) {
		this.password = password;
	}
	
}
