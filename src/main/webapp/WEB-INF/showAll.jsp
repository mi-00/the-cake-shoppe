<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width" />
<title>On Sale in Your Shop Today</title>
<link rel="stylesheet" href="css/foundation.css">
<link rel="stylesheet" href="css/my.css">
<script type="text/javascript" src="js/my.js"></script>
</head>
<body>

	<jsp:include page="header.html" />

	<h3 class="text-center">Our Cakes and Their Recipes:</h3>

	<hr>
	<c:choose>
		<c:when test="${listOfCakes == null or listOfCakes.isEmpty()}">
			<div class="row">The List is empty.</div>
		</c:when>
		<c:otherwise>
			<c:forEach var="cake" items="${listOfCakes}">

				<div class="row">
					<div class="medium-6 small-12 columns">
						<img
							src="/DBImageServerServlet?cakeId=${cake.id}">
					</div>
					<div class="medium-6 small-12 columns">

						<p class="text-right cake-name">${cake.name}</p>
						<p>
							<span class="cake-ingredients">Ingredients:</span>
							${cake.ingredients}
						</p>
						<p>
							<span class="cake-price">Price:</span> ${cake.price}
						</p>
						<p>
							<span class="cake-method">Directions:</span> $ { cake .method}
						</p>
						<p>
							<a href="ProductsServlet?action=show&id=${cake.id}"
								class="button secondary">Edit</a>&nbsp; <a
								href="ProductsServlet?action=buy&id=${cake.id}"
								class="button darkpink">Buy</a>&nbsp; <a
								href="ProductsServlet?action=delete&id=${cake.id}"
								class="alert button" onclick='return confirm("Are you sure?")'>Delete</a>
						</p>
					</div>
				</div>
				<hr>

			</c:forEach>
		</c:otherwise>
	</c:choose>

	<c:set var="prev" scope="request" value="${requestScope.prev}" />
	<c:set var="next" scope="request" value="${requestScope.next}" />
	<div class="row">
		<ul class="pagination text-center" role="navigation"
			aria-label="Pagination">

			<c:choose>
				<c:when test="${prev eq 'disabled'}">
					<li class="pagination-previous ${prev}">Previous</li>
				</c:when>
				<c:otherwise>
					<li class="pagination-previous ${requestScope.prev}"><a
						href="ProductsServlet?action=all&page=${param.page-1}"
						aria-label="Previous page">Previous</a></li>
				</c:otherwise>
			</c:choose>

			<li class="current pagination-pink"><span class="show-for-sr">You're on
					page</span>${param.page}</li>


			<c:choose>
				<c:when test="${next eq 'disabled'}">
					<li class="pagination-next ${next}">Next</li>
				</c:when>
				<c:otherwise>
					<li><a href="ProductsServlet?action=all&page=${param.page+1}"
						aria-label="Page ${param.page+1}">${param.page+1}</a></li>
					<li class="ellipsis"></li>
					<li class="pagination-next"><a
						href="ProductsServlet?action=all&page=${param.page+1}"
						aria-label="Next page">Next <span class="show-for-sr">page</span></a></li>
				</c:otherwise>
			</c:choose>
		</ul>
	</div>

	<!-- BasePathImgServerServlet -->

</body>
</html>
