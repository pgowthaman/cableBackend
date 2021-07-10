package com.cable.searchmodel;

public class SearchProviderModel {

	private Integer providerId;
	
	private String providerName;
	
	private String providerType;

	public Integer getProviderId() {
		return providerId;
	}

	public void setProviderId(Integer providerId) {
		this.providerId = providerId;
	}

	public String getProviderName() {
		return providerName;
	}

	public void setProviderName(String providerName) {
		this.providerName = providerName;
	}

	public String getProviderType() {
		return providerType;
	}

	public void setProviderType(String providerType) {
		this.providerType = providerType;
	}

	@Override
	public String toString() {
		return "SearchProviderModel [providerId=" + providerId + ", providerName=" + providerName + ", providerType="
				+ providerType + "]";
	}
	
}
