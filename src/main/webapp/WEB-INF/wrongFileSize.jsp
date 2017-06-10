<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width" />
<title>Error Page - Wrong File Size or Type</title>
<link rel="stylesheet" href="css/foundation.css">
<link rel="stylesheet" href="css/my.css">
</head>
<body>
	<jsp:include page="header.html" />
	<div class="row">
		<h3>File Size exceeded accepted limit or wasn't of type .jpeg</h3>
		<p>Make sure the file you want to upload is not larger than 1Mb and its type is jpeg.</p>
	</div>
</body>
</html>
