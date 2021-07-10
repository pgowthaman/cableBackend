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
		<c:if test="${empty paymentModel}">
			<h2>Payment Details</h2>

			<form method="POST" class="form-inline" action="searchPayment"
				modelAttribute="searchPaymentModel">
				<div class="form-group">
					<input type="number" class="form-control" id="paymentId"
						placeholder="Payment Id" name="paymentId" value="${searchPaymentModel.paymentId}">
				</div>
			<div class="form-group">
				 <input type="number"
					class="form-control" id="userId" placeholder="User Id"
					name="userId" value="${searchPaymentModel.userId}">
			</div>
			<div class="form-group">
				 <input type="number"
					class="form-control" id="amount" placeholder="Amount"
					name="amount" value="${searchPaymentModel.amount}">
			</div>
			<div class="form-group">
				 <input type="date"
					class="form-control" id="strPaidDate" placeholder="paid Date"
					name="strPaidDate" value="${searchPaymentModel.strPaidDate}">
			</div>
			<div class="form-group">
				 <input type="number"
					class="form-control" id="collectorId" placeholder="Collector Id"
					name="collectorId" value="${searchPaymentModel.collectorId}">
			</div>
			<div class="form-group">
				<input type="text"
					class="form-control" id="paymentMode" placeholder="Payment Mode"
					name="paymentMode" value="${searchPaymentModel.paymentMode}">
			</div>
			<div class="form-group">
				 <input type="number"
					class="form-control" id="operatorId" placeholder="Operator Id"
					name="operatorId" value="${searchPaymentModel.operatorId}">
			</div>
			<div class="form-group">
				 <input type="number"
					class="form-control" id="areaId" placeholder="Area Id"
					name="areaId" value="${searchPaymentModel.areaId}">
			</div>
			<div class="form-group">
				 <input type="text"
					class="form-control" id="comment" placeholder="comment"
					name="comment" value="${searchPaymentModel.comment}">
			</div>
			<div class="form-group">
				<input type="number"
					class="form-control" id="connectionId" placeholder="Connection Id"
					name="connectionId" value="${searchPaymentModel.connectionId}">
			</div>
				<button type="submit" class="btn btn-default">Search</button>
				</form>
				<br>

				<c:if test="${empty paymentModelList}">
					<h5>No Records Found</h5>
				</c:if>
		</c:if>
		<c:if test="${!empty paymentModelList}">

			<table class="table table-striped table-bordered table-sm"
				cellspacing="0" width="100%">
				<thead>
					<tr>
						<th class="th-sm">Payment Id</th>
						<th class="th-sm">User Id</th>
						<th class="th-sm">Amount</th>
						<th class="th-sm">Paid Date</th>
						<th class="th-sm">Collector Id</th>
						<th class="th-sm">Payment Mode</th>
						<th class="th-sm">Operator Id</th>
						<th class="th-sm">Area Id</th>
						<th class="th-sm">Comment</th>
						<th class="th-sm">Connection Id</th>
						<th class="th-sm">Edit</th>
						<th class="th-sm">Delete</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${paymentModelList}" var="payment">
						<tr>
							<td>${payment.paymentId}</td>
							<td>${payment.userId}</td>
							<td>${payment.amount}</td>
							<td>${payment.paidDate}</td>
							<td>${payment.collectorId}</td>
							<td>${payment.paymentMode}</td>
							<td>${payment.operatorId}</td>
							<td>${payment.areaId}</td>
							<td>${payment.comment}</td>
							<td>${payment.connectionId}</td>
							<td><a href="<c:url value='editPayment/${payment.paymentId}' />">Edit</a></td>
							<td><a href="<c:url value='removePayment/${payment.paymentId}' />">Delete</a></td>
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
							href="setPagePayment/${previousPage}">Previous</a></li>
						<li class="page-item"><a class="page-link"
							href="setPagePayment/${previousPage}">${previousPage} </a></li>
					</c:if>
					<li class="page-item active"><a class="page-link"
						href="setPagePayment/${currentPage}">${currentPage}</a></li>
					<c:if test="${!empty nextPage}">
						<li class="page-item"><a class="page-link"
							href="setPagePayment/${nextPage}">${nextPage}</a></li>
						<li class="page-item"><a class="page-link"
							href="setPagePayment/${nextPage}">Next</a></li>
					</c:if>
					<li><a href="paymentDownloadCSVFile">Download Csv</a></li>
					<li><a href="paymentDeleteAll">Delete All</a></li>
				</ul>
			</nav>
		</c:if>
			<c:if test="${empty paymentModel}">
		<h2>Upload Payment Data</h2>
		<form class="form-inline" method="POST" action="paymentSaveCSVFile"
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
		<h2>Payment Form</h2>
		<form method="POST" class="form-inline" action="paymentSave" modelAttribute="paymentModel">
			<div class="form-group">
				<input type="hidden" class="form-control" id="paymentId"
					placeholder="Payment Id" name="paymentId" value="${paymentModel.paymentId}">
			</div>
			<div class="form-group">
				<label for="userId">User Id:</label> <input type="number"
					class="form-control" id="userId" placeholder="User Id"
					name="userId" value="${paymentModel.userId}">
			</div>
			<div class="form-group">
				<label for="amount">Amount:</label> <input type="number"
					class="form-control" id="amount" placeholder="Amount"
					name="amount" value="${paymentModel.amount}">
			</div>
			<div class="form-group">
				<label for="strPaidDate">Paid Date:</label> <input type="date"
					class="form-control" id="strPaidDate" placeholder="paid Date"
					name="strPaidDate" value="${paymentModel.strPaidDate}">
			</div>
			<div class="form-group">
				<label for="collectorId">Collector Id:</label> <input type="number"
					class="form-control" id="collectorId" placeholder="Collector Id"
					name="collectorId" value="${paymentModel.collectorId}">
			</div>
			<div class="form-group">
				<label for="paymentMode">Payment Mode:</label> <input type="text"
					class="form-control" id="paymentMode" placeholder="Payment Mode"
					name="paymentMode" value="${paymentModel.paymentMode}">
			</div>
			<div class="form-group">
				<label for="operatorId">Operator Id:</label> <input type="number"
					class="form-control" id="operatorId" placeholder="Operator Id"
					name="operatorId" value="${paymentModel.operatorId}">
			</div>
			<div class="form-group">
				<label for="areaId">Area Id:</label> <input type="number"
					class="form-control" id="areaId" placeholder="Area Id"
					name="areaId" value="${paymentModel.areaId}">
			</div>
			<div class="form-group">
				<label for="comment">Comment:</label> <input type="text"
					class="form-control" id="comment" placeholder="comment"
					name="comment" value="${paymentModel.comment}">
			</div>
			<div class="form-group">
				<label for="connectionId">Connection Id:</label> <input type="number"
					class="form-control" id="connectionId" placeholder="Connection Id"
					name="connectionId" value="${paymentModel.connectionId}">
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