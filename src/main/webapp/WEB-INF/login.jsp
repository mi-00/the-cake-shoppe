<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width" />
<title>Login</title>
<link rel="stylesheet" href="css/foundation.css">
<link rel="stylesheet" href="css/my.css">
</head>
<body>
	<jsp:include page="header.html" />
	<form action="j_security_check" method="post">		
			
			<fieldset>
			<h3 style="text-align: center">Please login to access the requested page</h3>

			<div class="row">
				<div class="medium-2 small-12 columns">
					<label for="right-label" class="text-right middle">
						Username:</label>
				</div>
				<div class="medium-9 small-12 columns">
					<input type="text" name="j_username" id="right-label">
				</div>
				<div class="medium-1 small-12 columns"></div>
			</div>

			<div class="row">
				<div class="medium-2 small-12 columns">
					<label for="right-label" class="text-right middle">Password:</label>
				</div>
				<div class="medium-9 small-12 columns">
					<input type="password" name="j_password"></textarea>
				</div>
				<div class="medium-1 small-12 columns"></div>
			</div>

			<div class="row">
				<div class="small-2 columns">&nbsp;</div>
				<div class="small-9 columns text-justify">
					<input type="submit" class="hollow button expanded" value="Login">
				</div>
				<div class="small-1 columns"></div>
			</div>
		</fieldset>
	</form>
	
	
</body>
</html>
