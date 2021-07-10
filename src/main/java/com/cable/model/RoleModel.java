package com.cable.model;

import org.springframework.security.core.GrantedAuthority;

public class RoleModel implements GrantedAuthority{
	
	public static final String SUPER_ADMIN = "SUPER_ADMIN";
	public static final String ADMIN = "ADMIN";
	public static final String OPEARTOR = "OPEARTOR";
	public static final String COLLECTOR = "COLLECTOR";
	public static final String CUSTOMER = "CUSTOMER";

	private String authority;

	public void setAuthority(String authority) {
		this.authority = authority;
	}

	@Override
	public String getAuthority() {
		// TODO Auto-generated method stub
		return authority;
	}
}
