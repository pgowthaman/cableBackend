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
		<c:if test="${empty areaModel}">
			<h2>Area Details</h2>

			<form method="POST" class="form-inline" action="searchArea"
				modelAttribute="searchAreaModel">
				<div class="form-group">
					<input type="number" class="form-control" id="areaId"
						placeholder="Area Id" name="areaId" value="${searchAreaModel.areaId}">
				</div>
				<div class="form-group">
					<input type="text" class="form-control" id="areaName"
						placeholder="Area name" name="areaName"
						value="${searchAreaModel.areaName}">
				</div>
				<button type="submit" class="btn btn-default">Search</button>
				</form>
				<br>

				<c:if test="${empty areaModelList}">
					<h5>No Records Found</h5>
				</c:if>
		</c:if>
		<c:if test="${!empty areaModelList}">

			<table class="table table-striped table-bordered table-sm"
				cellspacing="0" width="100%">
				<thead>
					<tr>
						<th class="th-sm">Area Id</th>
						<th class="th-sm">Area Name</th>
						<th class="th-sm">Edit</th>
						<th class="th-sm">Delete</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${areaModelList}" var="area">
						<tr>
							<td>${area.areaId}</td>
							<td>${area.areaName}</td>
							<td><a href="<c:url value='editArea/${area.areaId}' />">Edit</a></td>
							<td><a href="<c:url value='removeArea/${area.areaId}' />">Delete</a></td>
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
							href="setPageArea/${previousPage}">Previous</a></li>
						<li class="page-item"><a class="page-link"
							href="setPageArea/${previousPage}">${previousPage} </a></li>
					</c:if>
					<li class="page-item active"><a class="page-link"
						href="setPageArea/${currentPage}">${currentPage}</a></li>
					<c:if test="${!empty nextPage}">
						<li class="page-item"><a class="page-link"
							href="setPageArea/${nextPage}">${nextPage}</a></li>
						<li class="page-item"><a class="page-link"
							href="setPageArea/${nextPage}">Next</a></li>
					</c:if>
					<li><a href="areaDownloadCSVFile">Download Csv</a></li>
					<li><a href="areaDeleteAll">Delete All</a></li>
				</ul>
			</nav>
		</c:if>
			<c:if test="${empty areaModel}">
		<h2>Upload Area Data</h2>
		<form class="form-inline" method="POST" action="areaSaveCSVFile"
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
		<h2>Area Form</h2>
		<form method="POST" class="form-inline" action="areaSave" modelAttribute="areaModel">
			<div class="form-group">
				<input type="hidden" class="form-control" id="areaId"
					placeholder="Area Id" name="areaId" value="${areaModel.areaId}">
			</div>
			<div class="form-group">
				<label for="areaName">Area Name:</label> <input type="text"
					class="form-control" id="areaName" placeholder="Area name"
					name="areaName" value="${areaModel.areaName}">
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