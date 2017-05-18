<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Login Error</title>
<link rel="stylesheet" href="css/foundation.css">
<link rel="stylesheet" href="css/my.css">
</head>
<body>
	<jsp:include page="header.html" />
	<div class="row">
		<h3>Please insert your credentials</h3>
	</div>
	
	<div class="row">
		<p><i>You need to be logged in to access the requested page.</i></p>
		<p><a href="ProductsServlet?action=login" class="button darkpink">Retry</a></p>
	</div>
	
</body>
</html>