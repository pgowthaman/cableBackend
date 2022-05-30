package com.cable.to;

import java.io.Serializable;
import java.util.List;

import org.springframework.stereotype.Component;

import com.cable.model.AreaModel;
import com.cable.model.ComplaintModel;
import com.cable.model.ComplaintTypeModel;
import com.cable.model.ConnectionModel;
import com.cable.model.OperatorModel;
import com.cable.model.PaymentModel;
import com.cable.model.ProviderModel;
import com.cable.model.SetupboxModel;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@Component
//@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@JsonInclude(Include.NON_NULL)
public class MainModel implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String pageNumber;
	
	private String pageSize;
	
	private String totalRecords;
	
	private String totalNumberOfpages;
	
	private String previousPage;
	
	private String nextPage;

	private String currentPage;
	
	private String message ;
	
	private boolean response = true;
	
	private List<AreaTO> areaModelList;
	
	private AreaTO searchAreaModel;
	
	private List<ComplaintTO> complaintModelList;
	
	private ComplaintTO searchComplaintModel;
	
	private List<ComplaintTypeTO> complaintTypeModelList;
	
	private ComplaintTypeTO searchComplaintTypeModel;
	
	private List<ConnectionTO> connectionModelList;
	
	private ConnectionTO searchConnectionModel;
	
	private List<OperatorTO> operatorModelList;
	
	private OperatorTO searchOperatorModel;
	
	private List<PaymentTO> paymentModelList;
	
	private PaymentTO searchPaymentModel;
	
	private List<ProviderTO> providerModelList;
	
	private ProviderTO searchProviderModel;
	
	private List<SetupboxTO> setupboxModelList;
	
	private SetupboxTO searchSetupboxModel;
	
	private List<UserTO> userModelList;
	
	private UserTO searchUserModel;
	
	private List<UserRoleTO> userRoleModelList;
	
	private UserRoleTO searchUserRoleModel;

	public String getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(String pageNumber) {
		this.pageNumber = pageNumber;
	}

	public String getPageSize() {
		return pageSize;
	}

	public void setPageSize(String pageSize) {
		this.pageSize = pageSize;
	}

	public String getTotalRecords() {
		return totalRecords;
	}

	public void setTotalRecords(String totalRecords) {
		this.totalRecords = totalRecords;
	}

	public String getTotalNumberOfpages() {
		return totalNumberOfpages;
	}

	public void setTotalNumberOfpages(String totalNumberOfpages) {
		this.totalNumberOfpages = totalNumberOfpages;
	}

	public String getPreviousPage() {
		return previousPage;
	}

	public void setPreviousPage(String previousPage) {
		this.previousPage = previousPage;
	}

	public String getNextPage() {
		return nextPage;
	}

	public void setNextPage(String nextPage) {
		this.nextPage = nextPage;
	}

	public String getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(String currentPage) {
		this.currentPage = currentPage;
	}

	public List<AreaTO> getAreaModelList() {
		return areaModelList;
	}

	public void setAreaModelList(List<AreaTO> areaModelList) {
		this.areaModelList = areaModelList;
	}

	public AreaTO getSearchAreaModel() {
		return searchAreaModel;
	}

	public void setSearchAreaModel(AreaTO searchAreaModel) {
		this.searchAreaModel = searchAreaModel;
	}

	public List<ComplaintTO> getComplaintModelList() {
		return complaintModelList;
	}

	public void setComplaintModelList(List<ComplaintTO> complaintModelList) {
		this.complaintModelList = complaintModelList;
	}

	public ComplaintTO getSearchComplaintModel() {
		return searchComplaintModel;
	}

	public void setSearchComplaintModel(ComplaintTO searchComplaintModel) {
		this.searchComplaintModel = searchComplaintModel;
	}

	public List<ComplaintTypeTO> getComplaintTypeModelList() {
		return complaintTypeModelList;
	}

	public void setComplaintTypeModelList(List<ComplaintTypeTO> complaintTypeModelList) {
		this.complaintTypeModelList = complaintTypeModelList;
	}

	public ComplaintTypeTO getSearchComplaintTypeModel() {
		return searchComplaintTypeModel;
	}

	public void setSearchComplaintTypeModel(ComplaintTypeTO searchComplaintTypeModel) {
		this.searchComplaintTypeModel = searchComplaintTypeModel;
	}

	public List<ConnectionTO> getConnectionModelList() {
		return connectionModelList;
	}

	public void setConnectionModelList(List<ConnectionTO> connectionModelList) {
		this.connectionModelList = connectionModelList;
	}

	public ConnectionTO getSearchConnectionModel() {
		return searchConnectionModel;
	}

	public void setSearchConnectionModel(ConnectionTO searchConnectionModel) {
		this.searchConnectionModel = searchConnectionModel;
	}

	public List<OperatorTO> getOperatorModelList() {
		return operatorModelList;
	}

	public void setOperatorModelList(List<OperatorTO> operatorModelList) {
		this.operatorModelList = operatorModelList;
	}

	public OperatorTO getSearchOperatorModel() {
		return searchOperatorModel;
	}

	public void setSearchOperatorModel(OperatorTO searchOperatorModel) {
		this.searchOperatorModel = searchOperatorModel;
	}

	public List<PaymentTO> getPaymentModelList() {
		return paymentModelList;
	}

	public void setPaymentModelList(List<PaymentTO> paymentModelList) {
		this.paymentModelList = paymentModelList;
	}

	public PaymentTO getSearchPaymentModel() {
		return searchPaymentModel;
	}

	public void setSearchPaymentModel(PaymentTO searchPaymentModel) {
		this.searchPaymentModel = searchPaymentModel;
	}

	public List<ProviderTO> getProviderModelList() {
		return providerModelList;
	}

	public void setProviderModelList(List<ProviderTO> providerModelList) {
		this.providerModelList = providerModelList;
	}

	public ProviderTO getSearchProviderModel() {
		return searchProviderModel;
	}

	public void setSearchProviderModel(ProviderTO searchProviderModel) {
		this.searchProviderModel = searchProviderModel;
	}

	public List<SetupboxTO> getSetupboxModelList() {
		return setupboxModelList;
	}

	public void setSetupboxModelList(List<SetupboxTO> setupboxModelList) {
		this.setupboxModelList = setupboxModelList;
	}

	public SetupboxTO getSearchSetupboxModel() {
		return searchSetupboxModel;
	}

	public void setSearchSetupboxModel(SetupboxTO searchSetupboxModel) {
		this.searchSetupboxModel = searchSetupboxModel;
	}

	public List<UserTO> getUserModelList() {
		return userModelList;
	}

	public void setUserModelList(List<UserTO> userModelList) {
		this.userModelList = userModelList;
	}

	public UserTO getSearchUserModel() {
		return searchUserModel;
	}

	public void setSearchUserModel(UserTO searchUserModel) {
		this.searchUserModel = searchUserModel;
	}

	public List<UserRoleTO> getUserRoleModelList() {
		return userRoleModelList;
	}

	public void setUserRoleModelList(List<UserRoleTO> userRoleModelList) {
		this.userRoleModelList = userRoleModelList;
	}

	public UserRoleTO getSearchUserRoleModel() {
		return searchUserRoleModel;
	}

	public void setSearchUserRoleModel(UserRoleTO searchUserRoleModel) {
		this.searchUserRoleModel = searchUserRoleModel;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public boolean isResponse() {
		return response;
	}

	public void setResponse(boolean response) {
		this.response = response;
	}

	@Override
	public String toString() {
		return "MainModel [pageNumber=" + pageNumber + ", pageSize=" + pageSize + ", totalRecords=" + totalRecords
				+ ", totalNumberOfpages=" + totalNumberOfpages + ", previousPage=" + previousPage + ", nextPage="
				+ nextPage + ", currentPage=" + currentPage + ", message=" + message + ", response=" + response
				+ ", areaModelList=" + areaModelList + ", searchAreaModel=" + searchAreaModel + ", complaintModelList="
				+ complaintModelList + ", searchComplaintModel=" + searchComplaintModel + ", complaintTypeModelList="
				+ complaintTypeModelList + ", searchComplaintTypeModel=" + searchComplaintTypeModel
				+ ", connectionModelList=" + connectionModelList + ", searchConnectionModel=" + searchConnectionModel
				+ ", operatorModelList=" + operatorModelList + ", searchOperatorModel=" + searchOperatorModel
				+ ", paymentModelList=" + paymentModelList + ", searchPaymentModel=" + searchPaymentModel
				+ ", providerModelList=" + providerModelList + ", searchProviderModel=" + searchProviderModel
				+ ", setupboxModelList=" + setupboxModelList + ", searchSetupboxModel=" + searchSetupboxModel
				+ ", userModelList=" + userModelList + ", searchUserModel=" + searchUserModel + ", userRoleModelList="
				+ userRoleModelList + ", searchUserRoleModel=" + searchUserRoleModel + "]";
	}
	
	
}
