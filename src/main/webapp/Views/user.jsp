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
		 <c:if test="${empty userModel}">
			<h2>User Details</h2>

			<form method="POST" class="form-inline" action="searchUser"
				modelAttribute="searchUserModel">
				<div class="form-group">
					<input type="text" class="form-control" id="userId"
						placeholder="User Id" name="userId" value="${searchUserModel.userId}">
				</div>
				<div class="form-group">
					<input type="text" class="form-control" id="username"
						placeholder="User name" name="username"
						value="${searchUserModel.username}">
				</div>
				<div class="form-group">
				<input type="text"
					class="form-control" id="phoneNumber" placeholder="Mobile Number"
					name="phoneNumber" value="${searchUserModel.phoneNumber}">
			</div>
			<div class="form-group">
				 <input type="text"
					class="form-control" id="firebaseId" placeholder="Firebase Id"
					name="firebaseId" value="${searchUserModel.firebaseId}">
			</div>
			<div class="form-group">
				 <input type="text"
					class="form-control" id="firebaseToken" placeholder="Firebase Token"
					name="firebaseToken" value="${searchUserModel.firebaseToken}">
			</div>
			<div class="form-group">
				 <input type="text"
					class="form-control" id="userRole" placeholder="User Role"
					name="userRole" value="${searchUserModel.userRole}">
			</div>
			<div class="form-group">
				<input type="text"
					class="form-control" id="operatorId" placeholder="Operator Id"
					name="operatorId" value="${searchUserModel.operatorId}">
			</div>
				<button type="submit" class="btn btn-default">Search</button>
				</form>
				<br>

				<c:if test="${empty userModelList}">
					<h5>No Records Found</h5>
				</c:if>
		</c:if> 
		<c:if test="${!empty userModelList}">

			<table class="table table-striped table-bordered table-sm"
				cellspacing="0" width="100%">
				<thead>
					<tr>
						<th class="th-sm">User Id</th>
						<th class="th-sm">User Name</th>
						<th class="th-sm">Phone Number</th>
						<th class="th-sm">User Role</th>
						<th class="th-sm">Firebase Id</th>
						<th class="th-sm">Firebase Token</th>
						<th class="th-sm">Operator Id</th>
						<th class="th-sm">Edit</th>
						<th class="th-sm">Delete</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${userModelList}" var="user">
						<tr>
							<td>${user.userId}</td>
							<td>${user.username}</td>
							<td>${user.phoneNumber}</td>
							<td>${user.userRole}</td>
							<td>${user.firebaseId}</td>
							<td>${user.firebaseToken}</td>
							<td>${user.operatorId}</td>
							<td><a href="<c:url value='editUser/${user.userId}' />">Edit</a></td>
							<td><a href="<c:url value='removeUser/${user.userId}' />">Delete</a></td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
			<nav aria-label="Page navigation example">
				<ul class="pagination justify-content-end">
					<li class="page-item disabled"><a class="page-link" href="#">Total
							Records : ${totalRecords}</a></li>
					<li class="page-item disabled"><a class="page-link" href="#">No
							of pages : ${totalPage}</a></li>
					<c:if test="${!empty previousPage}">
						<li class="page-item"><a class="page-link"
							href="setPageUser/${previousPage}">Previous</a></li>
						<li class="page-item"><a class="page-link"
							href="setPageUser/${previousPage}">${previousPage} </a></li>
					</c:if>
					<li class="page-item active"><a class="page-link"
						href="setPageUser/${currentPage}">${currentPage}</a></li>
					<c:if test="${!empty nextPage}">
						<li class="page-item"><a class="page-link"
							href="setPageUser/${nextPage}">${nextPage}</a></li>
						<li class="page-item"><a class="page-link"
							href="setPageUser/${nextPage}">Next</a></li>
					</c:if>
					<li><a href="userDownloadCSVFile">Download Csv</a></li>
					<li><a href="userDeleteAll">Delete All</a></li>
				</ul>
			</nav>
			</c:if>
			 <c:if test="${empty userModel}">
		<h2>Upload User Data</h2>
		<form class="form-inline" method="POST" action="userSaveCSVFile"
			modelAttribute="fileUploadModel" enctype="multipart/form-data">
			<div class="form-group">
				<div class="form-group">
				<input type="file" class="form-control"
					name="fileData" /> OR
			 <input type="text" class="form-control" id="filePath"
					name="filePath" placeHolder="File path"/>
			</div>
			</div>
			<button type="submit" class="btn btn-default">Upload CSV</button>
		</form> 
	</c:if>
		<h2>User Form</h2>
		<form method="POST" class="form-inline" action="userSave" modelAttribute="userModel">
			<div class="form-group">
				<input type="hidden" class="form-control" id="userId"
					placeholder="User Id" name="userId" value="${userModel.userId}">
			</div>
			<div class="form-group">
				<label for="username">User Name :</label> <input type="text"
					class="form-control" id="username" placeholder="User name"
					name="username" value="${userModel.username}">
			</div>
			<div class="form-group">
				<label for="phoneNumber">Mobile :</label> <input type="text"
					class="form-control" id="phoneNumber" placeholder="Mobile Number"
					name="phoneNumber" value="${userModel.phoneNumber}">
			</div>
			<div class="form-group">
				<label for="firebaseId">Firebase Id :</label> <input type="text"
					class="form-control" id="firebaseId" placeholder="Firebase Id"
					name="firebaseId" value="${userModel.firebaseId}">
			</div>
			<div class="form-group">
				<label for="firebaseToken">Firebase Token :</label> <input type="text"
					class="form-control" id="firebaseToken" placeholder="Firebase Token"
					name="firebaseToken" value="${userModel.firebaseToken}">
			</div>
			<div class="form-group">
				<label for="userRole">User Role :</label> <input type="text"
					class="form-control" id="userRole" placeholder="User Role"
					name="userRole" value="${userModel.userRole}">
			</div>
			<div class="form-group">
				<label for="operatorId">Operator Id :</label> <input type="text"
					class="form-control" id="operatorId" placeholder="Operator Id"
					name="operatorId" value="${userModel.operatorId}">
			</div>
			<button type="submit" class="btn btn-default">Save</button>
		</form>
		<br />


	</div>
	<br />
	<br />
	<br />
</body>
</html>