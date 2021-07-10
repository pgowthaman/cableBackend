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
<script>
	/* function myFunction() {
	 var x = document.getElementById("mySelect").value;
	 $.ajax({  
	 type : 'GET',  
	 url : "setPageSize",  
	 data : {
	 pageSize : x
	 },success: function () {
	 refreshPage(x);
	 }

	 });
	 }
	 function refreshPage(x) {
	 alert("inside Refresh"+x);
	 document.getElementById("mySelect").value=x;
	 location.reload(true);
	 } */
</script>
</head>
<body>
	<%@ include file="/Views/header.jsp"%><br />

	<div class="container">
		<c:if test="${empty providerModel}">
			<h2>Provider Details</h2>

			<form method="POST" class="form-inline" action="searchProvider"
				modelAttribute="searchProviderModel">
				<div class="form-group">
					<input type="number" class="form-control" id="providerId"
						placeholder="Provider Id" name="providerId" value="${searchProviderModel.providerId}">
				</div>
				<div class="form-group">
				 <input type="text"
					class="form-control" id="providerName" placeholder="Provider Name"
					name="providerName" value="${searchProviderModel.providerName}">
			</div>
			<div class="form-group">
				 <input type="text"
					class="form-control" id="providerType" placeholder="Provider Type"
					name="providerType" value="${searchProviderModel.providerType}">
			</div>
				<button type="submit" class="btn btn-default">Search</button>
				</form>
				<br>

				<c:if test="${empty providerModelList}">
					<h5>No Records Found</h5>
				</c:if>
		</c:if>
		<c:if test="${!empty providerModelList}">

			<table class="table table-striped table-bordered table-sm"
				cellspacing="0" width="100%">
				<thead>
					<tr>
						<th class="th-sm">Provider Id</th>
						<th class="th-sm">Provider Name</th>
						<th class="th-sm">Provider Type</th>
						<th class="th-sm">Edit</th>
						<th class="th-sm">Delete</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${providerModelList}" var="provider">
						<tr>
							<td>${provider.providerId}</td>
							<td>${provider.providerName}</td>
							<td>${provider.providerType}</td>
							<td><a href="<c:url value='editProvider/${provider.providerId}' />">Edit</a></td>
							<td><a href="<c:url value='removeProvider/${provider.providerId}' />">Delete</a></td>
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
							href="setPageProvider/${previousPage}">Previous</a></li>
						<li class="page-item"><a class="page-link"
							href="setPageProvider/${previousPage}">${previousPage} </a></li>
					</c:if>
					<li class="page-item active"><a class="page-link"
						href="setPageProvider/${currentPage}">${currentPage}</a></li>
					<c:if test="${!empty nextPage}">
						<li class="page-item"><a class="page-link"
							href="setPageProvider/${nextPage}">${nextPage}</a></li>
						<li class="page-item"><a class="page-link"
							href="setPageProvider/${nextPage}">Next</a></li>
					</c:if>
					<li><a href="providerDownloadCSVFile">Download Csv</a></li>
					<li><a href="providerDeleteAll">Delete All</a></li>
				</ul>
			</nav>
		</c:if>
			<c:if test="${empty providerModel}">
		<h2>Upload Provider Data</h2>
		<form class="form-inline" method="POST" action="providerSaveCSVFile"
			modelAttribute="fileUploadModel" enctype="multipart/form-data">
			<div class="form-group">
				<input type="file" class="form-control"
					name="fileData" /> OR
			 <input type="text" class="form-control" id="filePath"
					name="filePath" placeHolder="File path"/>
			</div>
			<button type="submit" class="btn btn-default">Upload CSV</button>
		</form>
		</c:if>
		<h2>Provider Form</h2>
		<form method="POST" class="form-inline" action="providerSave" modelAttribute="providerModel">
			<div class="form-group">
				<input type="hidden" class="form-control" id="providerId"
					placeholder="Provider Id" name="providerId" value="${providerModel.providerId}">
			</div>
			<div class="form-group">
				<label for="providerName">Provider Name:</label> <input type="text"
					class="form-control" id="providerName" placeholder="Provider Name"
					name="providerName" value="${providerModel.providerName}">
			</div>
			<div class="form-group">
				<label for="providerType">Provider Type:</label> <input type="text"
					class="form-control" id="providerType" placeholder="Provider Type"
					name="providerType" value="${providerModel.providerType}">
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