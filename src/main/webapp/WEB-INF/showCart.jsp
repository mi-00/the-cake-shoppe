<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Your Cart</title>
<link rel="stylesheet" href="css/foundation.css">
<link rel="stylesheet" href="css/my.css">
</head>
<body>

	<jsp:include page="header.html" />

	<c:choose>
		<c:when test="${cart == null or cart.isEmpty()}">
			<div class="row">
				<h3>Your Cart is empty.</h3>
				<a
					href="/ProductsServlet?action=all&page=1">Shop
					for Cakes</a>
			</div>
		</c:when>
		<c:when test="${setOfCakes == null or setOfCakes.isEmpty()}">
		    					setOfCakes is empty.
							</c:when>
		<c:otherwise>

			<div class="row">

				<div class="medium-2 small-12 columns">&nbsp;</div>
				<div class="medium-8 small-12 columns">

					<table class="unstriped darkpink">
						<thead class="th-pink">
							<tr>
								<th>Name</th>
								<th>Quantity</th>
								<th>Add/ Remove 1</th>
							</tr>
						</thead>
						<tbody class="cake-pink">
							<c:forEach items="${cart}" var="cart_entry">
								<c:forEach items="${setOfCakes}" var="cake">

									<c:if test="${cart_entry.key==cake.id}">
										<tr>
											<td>${cart_entry.key}.${cake.name}</td>
											<td>${cart_entry.value}</td>
											<td>
												<form action='ProductsServlet?action=addRemove'
													method='post'>
													<input type='hidden' name='id' value="${cake.id}">
													<input type='submit' name='submit' value='-'> <input
														type='submit' name='submit' value='+'>
												</form>
											</td>
										</tr>
									</c:if>

								</c:forEach>
							</c:forEach>
						</tbody>
					</table>
					<a
						href="/ProductsServlet?action=checkout"
						class="button hollow secondary expanded">Buy</a> <a
						href="/ProductsServlet?action=clearCart"
						class="alert hollow button expanded">Delete Cart</a>
				</div>
				<div class="medium-2 small-12 columns">&nbsp;</div>
			</div>

		</c:otherwise>
	</c:choose>
</body>
</html>
