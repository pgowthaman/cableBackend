<!DOCTYPE html>
<html lang="en">
<head>
<title>Bootstrap Example</title>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
</head>
<body>
	<%
	Cookie cookie = null;
	Cookie[] cookies = null;
	String value = "";
	cookies = request.getCookies();
	
	if( cookies != null ) {
      	 for (int i = 0; i < cookies.length; i++) {
           cookie = cookies[i];
           if("Authorization".equals(cookie.getName())){
        	   value = cookie.getValue();
        	   out.print("Name : " + cookie.getName( ) + ",  ");
               out.print("Value: " + cookie.getValue( )+" <br/>");
           }
        }
     }
	
   // Add both the cookies in the response header.
	%>
	<nav class="navbar navbar-inverse">
		<div class="container-fluid">
			<div class="navbar-header">
				<a class="navbar-brand" href="#">Cable</a>
			</div>
			<ul class="nav navbar-nav">
				<li><a href="home">Home</a></li>
				<li><a href="about">About</a></li>
				<li><a href="operator">Operator</a></li>
				<li><a href="area">Area</a></li>
				<li><a href="user">User</a></li>
				<li><a href="complaint">Complaint</a></li>
				<li><a href="complaintType">Complaint Type</a></li>
				<li><a href="connection">Connection</a></li>
				<li><a href="payment">Payment</a></li>
				<li><a href="provider">Provider</a></li>
				<li><a href="setupbox">Setupbox</a></li>
				<li><a href="loginPage">Login</a></li>
			</ul>
		</div>
	</nav>

	<div class="container"></div>

</body>
</html>
