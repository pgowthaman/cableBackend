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
		<c:if test="${empty setupboxModel}">
			<h2>Setupbox Details</h2>

			<form method="POST" class="form-inline" action="searchSetupbox"
				modelAttribute="searchSetupboxModel">
				<div class="form-group">
					<input type="number" class="form-control" id="setupboxId"
						placeholder="Setupbox Id" name="setupboxId" value="${searchSetupboxModel.setupboxId}">
				</div>
				<div class="form-group">
				<input type="number"
					class="form-control" id="operatorId" placeholder="Operator Id"
					name="operatorId" value="${searchSetupboxModel.operatorId}">
			</div>
			<div class="form-group">
				 <input type="text"
					class="form-control" id="connectionStatus" placeholder="Connection Status"
					name="connectionStatus" value="${searchSetupboxModel.connectionStatus}">
			</div>
			<div class="form-group">
				 <input type="text"
					class="form-control" id="setupboxStatus" placeholder="Setupbox Status"
					name="setupboxStatus" value="${searchSetupboxModel.setupboxStatus}">
			</div>
			<div class="form-group">
				 <input type="text"
					class="form-control" id="setupboxType" placeholder="Setupbox Type"
					name="setupboxType" value="${searchSetupboxModel.setupboxType}">
			</div>
			<div class="form-group">
				 <input type="number"
					class="form-control" id="provderTypeId" placeholder="Provider Type Id"
					name="provderTypeId" value="${searchSetupboxModel.provderTypeId}">
			</div>
				<button type="submit" class="btn btn-default">Search</button>
				</form>
				<br>

				<c:if test="${empty setupboxModelList}">
					<h5>No Records Found</h5>
				</c:if>
		</c:if>
		<c:if test="${!empty setupboxModelList}">

			<table class="table table-striped table-bordered table-sm"
				cellspacing="0" width="100%">
				<thead>
					<tr>
						<th class="th-sm">Setupbox Id</th>
						<th class="th-sm">Operator Id</th>
						<th class="th-sm">Connection Status</th>
						<th class="th-sm">Setupbox Status</th>
						<th class="th-sm">Setupbox Type</th>
						<th class="th-sm">Provider Type Id</th>
						<th class="th-sm">Edit</th>
						<th class="th-sm">Delete</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${setupboxModelList}" var="setupbox">
						<tr>
							<td>${setupbox.setupboxId}</td>
							<td>${setupbox.operatorId}</td>
							<td>${setupbox.connectionStatus}</td>
							<td>${setupbox.setupboxStatus}</td>
							<td>${setupbox.setupboxType}</td>
							<td>${setupbox.provderTypeId}</td>
							<td><a href="<c:url value='editSetupbox/${setupbox.setupboxId}' />">Edit</a></td>
							<td><a href="<c:url value='removeSetupbox/${setupbox.setupboxId}' />">Delete</a></td>
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
							href="setPageSetupbox/${previousPage}">Previous</a></li>
						<li class="page-item"><a class="page-link"
							href="setPageSetupbox/${previousPage}">${previousPage} </a></li>
					</c:if>
					<li class="page-item active"><a class="page-link"
						href="setPageSetupbox/${currentPage}">${currentPage}</a></li>
					<c:if test="${!empty nextPage}">
						<li class="page-item"><a class="page-link"
							href="setPageSetupbox/${nextPage}">${nextPage}</a></li>
						<li class="page-item"><a class="page-link"
							href="setPageSetupbox/${nextPage}">Next</a></li>
					</c:if>
					<li><a href="setupboxDownloadCSVFile">Download Csv</a></li>
					<li><a href="setupboxDeleteAll">Delete All</a></li>
				</ul>
			</nav>
		</c:if>
			<c:if test="${empty setupboxModel}">
		<h2>Upload Setupbox Data</h2>
		<form class="form-inline" method="POST" action="setupboxSaveCSVFile"
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
		<h2>Setupbox Form</h2>
		<form method="POST" class="form-inline" action="setupboxSave" modelAttribute="setupboxModel">
			<div class="form-group">
				<input type="hidden" class="form-control" id="setupboxId"
					placeholder="Setupbox Id" name="setupboxId" value="${setupboxModel.setupboxId}">
			</div>
			<div class="form-group">
				<label for="operatorId">Operator Id:</label> <input type="number"
					class="form-control" id="operatorId" placeholder="Operator Id"
					name="operatorId" value="${setupboxModel.operatorId}">
			</div>
			<div class="form-group">
				<label for="connectionStatus">Connection Status:</label> <input type="text"
					class="form-control" id="connectionStatus" placeholder="Connection Status"
					name="connectionStatus" value="${setupboxModel.connectionStatus}">
			</div>
			<div class="form-group">
				<label for="setupboxStatus">Setupbox Status:</label> <input type="text"
					class="form-control" id="setupboxStatus" placeholder="Setupbox Status"
					name="setupboxStatus" value="${setupboxModel.setupboxStatus}">
			</div>
			<div class="form-group">
				<label for="setupboxType">Setupbox Type:</label> <input type="text"
					class="form-control" id="setupboxType" placeholder="Setupbox Type"
					name="setupboxType" value="${setupboxModel.setupboxType}">
			</div>
			<div class="form-group">
				<label for="provderTypeId">Provider Type Id:</label> <input type="number"
					class="form-control" id="provderTypeId" placeholder="Provider Type Id"
					name="provderTypeId" value="${setupboxModel.provderTypeId}">
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