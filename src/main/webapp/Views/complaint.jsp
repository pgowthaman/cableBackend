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
		<c:if test="${empty complaintModel}">
			<h2>Complaint Details</h2>

			<form method="POST" class="form-inline" action="searchComplaint"
				modelAttribute="searchComplaintModel">
				<div class="form-group">
				<input type="number" class="form-control" id="complaintId"
					placeholder="Complaint Id" name="complaintId" value="${searchComplaintModel.complaintId}">
			</div>
			<div class="form-group">
				 <input type="number"
					class="form-control" id="complaintTypeId" placeholder="Complaint Type Id"
					name="complaintTypeId" value="${searchComplaintModel.complaintTypeId}">
			</div>
			<div class="form-group">
				 <input type="text"
					class="form-control" id="complaintStatus" placeholder="Complaint Status"
					name="complaintStatus" value="${searchComplaintModel.complaintStatus}">
			</div>
			<div class="form-group">
				 <input type="number"
					class="form-control" id="userId" placeholder="User Id"
					name="userId" value="${searchComplaintModel.userId}">
			</div>
			<div class="form-group">
				<input type="text"
					class="form-control" id="collectorComments" placeholder="Collector Comments"
					name="collectorComments" value="${searchComplaintModel.collectorComments}">
			</div>
			<div class="form-group">
				 <input type="text"
					class="form-control" id="customerComments" placeholder="Customer Comments"
					name="customerComments" value="${searchComplaintModel.customerComments}">
			</div>
			<div class="form-group">
				<input type="text"
					class="form-control" id="collecterId" placeholder="Collecter Id"
					name="collecterId" value="${searchComplaintModel.collecterId}">
			</div>
				<button type="submit" class="btn btn-default">Search</button>
				</form>
				<br>

				<c:if test="${empty complaintModelList}">
					<h5>No Records Found</h5>
				</c:if>
		</c:if>
		<c:if test="${!empty complaintModelList}">

			<table class="table table-striped table-bordered table-sm"
				cellspacing="0" width="100%">
				<thead>
					<tr>
						<th class="th-sm">Complaint Id</th>
						<th class="th-sm">Complaint Type Id</th>
						<th class="th-sm">Complaint Status</th>
						<th class="th-sm">User Id</th>
						<th class="th-sm">Collector Comments</th>
						<th class="th-sm">Customer Comments</th>
						<th class="th-sm">Collector Id</th>
						<th class="th-sm">Edit</th>
						<th class="th-sm">Delete</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${complaintModelList}" var="complaint">
						<tr>
							<td>${complaint.complaintId}</td>
							<td>${complaint.complaintTypeId}</td>
							<td>${complaint.complaintStatus}</td>
							<td>${complaint.userId}</td>
							<td>${complaint.collectorComments}</td>
							<td>${complaint.customerComments}</td>
							<td>${complaint.collecterId}</td>
							<td><a href="<c:url value='editComplaint/${complaint.complaintId}' />">Edit</a></td>
							<td><a href="<c:url value='removeComplaint/${complaint.complaintId}' />">Delete</a></td>
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
							href="setPageComplaint/${previousPage}">Previous</a></li>
						<li class="page-item"><a class="page-link"
							href="setPageComplaint/${previousPage}">${previousPage} </a></li>
					</c:if>
					<li class="page-item active"><a class="page-link"
						href="setPageComplaint/${currentPage}">${currentPage}</a></li>
					<c:if test="${!empty nextPage}">
						<li class="page-item"><a class="page-link"
							href="setPageComplaint/${nextPage}">${nextPage}</a></li>
						<li class="page-item"><a class="page-link"
							href="setPageComplaint/${nextPage}">Next</a></li>
					</c:if>
					<li><a href="complaintDownloadCSVFile">Download Csv</a></li>
					<li><a href="complaintDeleteAll">Delete All</a></li>
				</ul>
			</nav>
		</c:if>
			<c:if test="${empty complaintModel}">
		<h2>Upload Complaint Data</h2>
		<form class="form-inline" method="POST" action="complaintSaveCSVFile"
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
		<h2>Complaint Form</h2>
		<form method="POST" class="form-inline" action="complaintSave" modelAttribute="complaintModel">
			<div class="form-group">
				<input type="hidden" class="form-control" id="complaintId"
					placeholder="Complaint Id" name="complaintId" value="${complaintModel.complaintId}">
			</div>
			<div class="form-group">
				<label for="complaintTypeId">Complaint type Id:</label> <input type="number"
					class="form-control" id="complaintTypeId" placeholder="Complaint Type Id"
					name="complaintTypeId" value="${complaintModel.complaintTypeId}">
			</div>
			<div class="form-group">
				<label for="complaintStatus">Complaint Status:</label> <input type="text"
					class="form-control" id="complaintStatus" placeholder="Complaint Status"
					name="complaintStatus" value="${complaintModel.complaintStatus}">
			</div>
			<div class="form-group">
				<label for="userId">User Id:</label> <input type="number"
					class="form-control" id="userId" placeholder="User Id"
					name="userId" value="${complaintModel.userId}">
			</div>
			<div class="form-group">
				<label for="collectorComments">Collector Comments:</label> <input type="text"
					class="form-control" id="collectorComments" placeholder="Collector Comments"
					name="collectorComments" value="${complaintModel.collectorComments}">
			</div>
			<div class="form-group">
				<label for="customerComments">Customer Comments:</label> <input type="text"
					class="form-control" id="customerComments" placeholder="Customer Comments"
					name="customerComments" value="${complaintModel.customerComments}">
			</div>
			<div class="form-group">
				<label for="collecterId">Collector Id:</label> <input type="text"
					class="form-control" id="collecterId" placeholder="Collecter Id"
					name="collecterId" value="${complaintModel.collecterId}">
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