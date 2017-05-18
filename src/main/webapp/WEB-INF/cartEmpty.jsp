<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Cart Empty</title>
<link rel="stylesheet" href="css/foundation.css">
<link rel="stylesheet" href="css/my.css">
</head>
<body>

	<jsp:include page="header.html" />
	
	<div class="row">
		<h3>Your cart is empty</h3>
		<a href="/ProductsServlet?action=all&page=1">Shop
			for Cakes</a>
	</div>
</body>
</html>
