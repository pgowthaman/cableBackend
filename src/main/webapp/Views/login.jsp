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
			<h2>Login</h2>

			<form method="POST" class="form-inline" action="login" modelAttribute="userModel">
			
				<div class="form-group">
				<input type="text"
					class="form-control" id="phoneNumber" placeholder="Mobile Number"
					name="phoneNumber" >
			</div>
			<div class="form-group">
				 <input type="password"
					class="form-control" id="firebaseId" placeholder="Password"
					name="firebaseId">
			</div>
		
				<button type="submit" class="btn btn-default">login</button>
				</form>
				<br>

	</div>
	<br />
	<br />
	<br />
</body>
</html>