<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@page isELIgnored="false"%>
<%@ page session="false"%>
<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
<style type="text/css">
input.form-control {
	width: auto;
}
</style>
</head>
<body>
	<%@ include file="/Views/header.jsp"%><br />

	<div class="container">
			<h2>Register User Details</h2>

			<form method="POST" class="form-inline" action="register"
				modelAttribute="userModel">
				<div class="form-group">
					<input type="text" class="form-control" id="username"
						placeholder="User name" name="username"
						value="">
				</div>
				<div class="form-group">
				<input type="text"
					class="form-control" id="phoneNumber" placeholder="Mobile Number"
					name="phoneNumber" value="">
			</div>
			<div class="form-group">
				 <input type="text"
					class="form-control" id="firebaseId" placeholder="Firebase Id"
					name="firebaseId" value="">
			</div>
			<div class="form-group">
				 <input type="text"
					class="form-control" id="firebaseToken" placeholder="Firebase Token"
					name="firebaseToken" value="">
			</div>
			<div class="form-group">
				 <input type="text"
					class="form-control" id="userRole" placeholder="User Role"
					name="userRole" value="">
			</div>
			<div class="form-group">
				<input type="text"
					class="form-control" id="operatorId" placeholder="Operator Id"
					name="operatorId" value="">
			</div>
				<button type="submit" class="btn btn-default">Register</button>
				</form>
				<br>


	</div>
	<br />
	<br />
	<br />
</body>
</html>