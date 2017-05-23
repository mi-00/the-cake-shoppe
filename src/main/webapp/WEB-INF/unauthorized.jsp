<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Unauthorized Access Forbidden</title>
<link rel="stylesheet" href="css/foundation.css">
<link rel="stylesheet" href="css/my.css">
</head>
<body>
	<jsp:include page="header.html" />
	<div class="row">
		<div class="medium-5 small-12 columns">
			<img
				src="/OtherImagesServerServlet?imageId=2">
		</div>
		<div class="medium-7 small-12 columns">
			<h3>Unauthorized Access Prohibited</h3>
			<p>You must have rights in order to access this page.</p>
			<p>Please contact the site administrator.</p>
			<p><a href="InvalidateSessionServlet" class="button darkpink">Retry</a></p>
		</div>
	</div>
</body>
</html>
