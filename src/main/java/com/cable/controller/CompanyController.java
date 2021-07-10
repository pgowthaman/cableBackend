package com.cable.controller;

import javax.annotation.security.RolesAllowed;
import javax.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


/**
 * 
 * @author iamgo
 *
 */
@RestController
@RequestMapping(value="/Api")
public class CompanyController {

	@RolesAllowed("ADMIN")
	@RequestMapping(value = "/testAPI/Admin", method = RequestMethod.GET)
	public String testAPIAdmin() {
		return "Showing for Admin";
	}
	
	@RolesAllowed({"CUSTOMER","ADMIN"})
	@RequestMapping(value = "/testAPI/Customer", method = RequestMethod.GET)
	public String testAPICustomer() {
		return "Showing for Customer";
	}
	
	@RolesAllowed("OPERATOR")
	@RequestMapping(value = "/testAPI/Operator", method = RequestMethod.GET)
	public String testAPIOperator() {
		return "Showing for Operator";
	}
	
	@RolesAllowed("COLLECTOR")
	@RequestMapping(value = "/testAPI/Collector", method = RequestMethod.GET)
	public String testAPICollector() {
		return "Showing for Operator";
	}
	
	
	@RequestMapping(value = "/showUser", method = RequestMethod.GET)
	public String lookUpLoggedInUser(HttpSession session) {
		
		return "User Looged in as "+session.getAttribute("OathKey");
	}
	
	
	
	
	/*
	 * @RequestMapping(value = "/getCompanyList", method = RequestMethod.POST)
	 * public ResponseEntity<?> getCompanyList() { List<CompanyModel> companyList =
	 * companyService.getCompanyList(); return new
	 * ResponseEntity<List<CompanyModel>>(companyList,HttpStatus.OK); }
	 * 
	 * @RequestMapping(value = "/getCategoryList", method = RequestMethod.POST)
	 * public ResponseEntity<?> getCategoryList() { List<CompanyModel> companyList =
	 * companyService.getCompanyList(); return new
	 * ResponseEntity<List<CompanyModel>>(companyList,HttpStatus.OK); }
	 */
	
	
}
