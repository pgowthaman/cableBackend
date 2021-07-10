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
		<c:if test="${empty connectionModel}">
			<h2>Connection Details</h2>

			<form method="POST" class="form-inline" action="searchConnection"
				modelAttribute="searchConnectionModel">
				<div class="form-group">
					<input type="number" class="form-control" id="connectionId"
						placeholder="Connection Id" name="connectionId" value="${searchConnectionModel.connectionId}">
				</div>
				<div class="form-group">
				 <input type="number"
					class="form-control" id="userId" placeholder="User Id"
					name="userId" value="${searchConnectionModel.userId}">
			</div>
			<div class="form-group">
				 <input type="number"
					class="form-control" id="operatorId" placeholder="Operator Id"
					name="operatorId" value="${searchConnectionModel.operatorId}">
			</div>
			<div class="form-group">
				 <input type="number"
					class="form-control" id="areaId" placeholder="Area Id"
					name="areaId" value="${searchConnectionModel.areaId}">
			</div>
			<div class="form-group">
				 <input type="number"
					class="form-control" id="setupboxId" placeholder="Setupbox Id"
					name="setupboxId" value="${searchConnectionModel.setupboxId}">
			</div>
			<div class="form-group">
				 <input type="text"
					class="form-control" id="connectionStatus" placeholder="Connection Status"
					name="connectionStatus" value="${searchConnectionModel.connectionStatus}">
			</div>
			<div class="form-group">
				 <input type="number"
					class="form-control" id="connectionNumber" placeholder="Connection Number"
					name="connectionNumber" value="${searchConnectionModel.connectionNumber}">
			</div>
			<div class="form-group">
			<input type="date"
					class="form-control" id="conDate" placeholder="Connection Date"
					name="conDate" value="${searchConnectionModel.conDate}">
			</div>
			<div class="form-group">
				 <input type="number"
					class="form-control" id="dueAmount" placeholder="Due Amount"
					name="dueAmount" value="${searchConnectionModel.dueAmount}">
			</div>
				<button type="submit" class="btn btn-default">Search</button>
				</form>
				<br>

				<c:if test="${empty connectionModelList}">
					<h5>No Records Found</h5>
				</c:if>
		</c:if>
		<c:if test="${!empty connectionModelList}">

			<table class="table table-striped table-bordered table-sm"
				cellspacing="0" width="100%">
				<thead>
					<tr>
						<th class="th-sm">Connection Id</th>
						<th class="th-sm">User Id</th>
						<th class="th-sm">Operator Id</th>
						<th class="th-sm">Area Id</th>
						<th class="th-sm">Setupbox Id</th>
						<th class="th-sm">Connection Status</th>
						<th class="th-sm">Connection Number</th>
						<th class="th-sm">Connection Date</th>
						<th class="th-sm">Due Amount</th>
						<th class="th-sm">Edit</th>
						<th class="th-sm">Delete</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${connectionModelList}" var="connection">
						<tr>
							<td>${connection.connectionId}</td>
							<td>${connection.userId}</td>
							<td>${connection.operatorId}</td>
							<td>${connection.areaId}</td>
							<td>${connection.setupboxId}</td>
							<td>${connection.connectionStatus}</td>
							<td>${connection.connectionNumber}</td>
							<td>${connection.connectionDate}</td>
							<td>${connection.dueAmount}</td>
							<td><a href="<c:url value='editConnection/${connection.connectionId}' />">Edit</a></td>
							<td><a href="<c:url value='removeConnection/${connection.connectionId}' />">Delete</a></td>
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
							href="setPageConnection/${previousPage}">Previous</a></li>
						<li class="page-item"><a class="page-link"
							href="setPageConnection/${previousPage}">${previousPage} </a></li>
					</c:if>
					<li class="page-item active"><a class="page-link"
						href="setPageConnection/${currentPage}">${currentPage}</a></li>
					<c:if test="${!empty nextPage}">
						<li class="page-item"><a class="page-link"
							href="setPageConnection/${nextPage}">${nextPage}</a></li>
						<li class="page-item"><a class="page-link"
							href="setPageConnection/${nextPage}">Next</a></li>
					</c:if>
					<li><a href="connectionDownloadCSVFile">Download Csv</a></li>
					<li><a href="connectionDeleteAll">Delete All</a></li>
				</ul>
			</nav>
		</c:if>
			<c:if test="${empty connectionModel}">
		<h2>Upload Connection Data</h2>
		<form class="form-inline" method="POST" action="connectionSaveCSVFile"
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
		<h2>Connection Form</h2>
		<form method="POST" class="form-inline" action="connectionSave" modelAttribute="connectionModel">
			<div class="form-group">
				<input type="hidden" class="form-control" id="connectionId"
					placeholder="Connection Id" name="connectionId" value="${connectionModel.connectionId}">
			</div>
			<div class="form-group">
				<label for="userId">User Id:</label> <input type="number"
					class="form-control" id="userId" placeholder="User Id"
					name="userId" value="${connectionModel.userId}">
			</div>
			<div class="form-group">
				<label for="operatorId">Operator Id:</label> <input type="number"
					class="form-control" id="operatorId" placeholder="Operator Id"
					name="operatorId" value="${connectionModel.operatorId}">
			</div>
			<div class="form-group">
				<label for="areaId">Area Id:</label> <input type="number"
					class="form-control" id="areaId" placeholder="Area Id"
					name="areaId" value="${connectionModel.areaId}">
			</div>
			<div class="form-group">
				<label for="setupboxId">Setupbox Id:</label> <input type="number"
					class="form-control" id="setupboxId" placeholder="Setupbox Id"
					name="setupboxId" value="${connectionModel.setupboxId}">
			</div>
			<div class="form-group">
				<label for="connectionStatus">Connection Status:</label> <input type="text"
					class="form-control" id="connectionStatus" placeholder="Connection Status"
					name="connectionStatus" value="${connectionModel.connectionStatus}">
			</div>
			<div class="form-group">
				<label for="connectionNumber">Connection Number:</label> <input type="number"
					class="form-control" id="connectionNumber" placeholder="Connection Number"
					name="connectionNumber" value="${connectionModel.connectionNumber}">
			</div>
			 <div class="form-group">
				<label for="conDate">Connection Date:</label> <input type="date"
					class="form-control" id="conDate" placeholder="Connection Date"
					name="conDate" value="${connectionModel.conDate}">
			</div> 
			<div class="form-group">
				<label for="dueAmount">Due Amount:</label> <input type="number"
					class="form-control" id="dueAmount" placeholder="Due Amount"
					name="dueAmount" value="${connectionModel.dueAmount}">
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