<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width" />
<title>Modify Your Recipe</title>
<link rel="stylesheet" href="css/foundation.css">
<link rel="stylesheet" href="css/my.css">
</head>
<body>

<jsp:include page="header.html"/>

	<div class="row">
		<div class="small-2 columns">
			<h4 style="color: #702f5a; text-align: center;">
				<c:if test="${lastID>0}">${lastID}:""</c:if>
				<b>${cake.id}. ${cake.name}</b>
			</h4>
		</div>
		<div class="small-10 columns">
			<img
				src="/DBImageServerServlet?cakeId=${cake.id}">
		</div>
	</div>

	<div class="row">
		<div class="small-2 columns">
			<p class="text-right middle">
				<b>Ingredients:</b>
			</p>
		</div>
		<div class="small-9 columns">
			<p>${cake.ingredients}</p>
		</div>
		<div class="small-1 columns"></div>
	</div>

	<div class="row">
		<div class="small-2 columns">
			<p class="text-right middle">
				<b>Directions:</b>
			</p>
		</div>
		<div class="small-9 columns">
			<p>${cake.method}</p>
		</div>
		<div class="small-1 columns"></div>
	</div>

	<form action="/ProductsServlet?action=update" method="post">

		<fieldset>
			<div class="row">
				<div class="small-2 columns">
					<p class="text-right middle">
						<b>Price:</b>
					</p>
				</div>
				<div class="small-9 columns">
					<input type="text" name="price" value="${cake.price}"
						placeholder="${cake.price}">
						<input type="hidden" name="id" value="${cake.id}">
						<input type="submit"
						class="hollow button" value="Change Price">
				</div>
				<div class="small-1 columns"></div>
			</div>
		</fieldset>

	</form>

</body>
</html>
