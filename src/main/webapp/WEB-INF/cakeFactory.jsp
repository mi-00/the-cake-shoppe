<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>***Welcome to Our Lovely Cake Factory***</title>
<link rel="stylesheet" href="css/foundation.css">
<link rel="stylesheet" href="css/my.css">
</head>
<body>

<jsp:include page="header.html"/>

	<form action="./ProductsServlet?action=add" method="post"
		enctype='multipart/form-data'>

		<fieldset>
			<h3 style="text-align: center">Add Your Awesome Cake Recipe &lt;3</h3>

			<div class="row">
				<div class="medium-2 small-12 columns">
					<label for="right-label" class="text-right middle">Cake
						Name:</label>
				</div>
				<div class="medium-9 small-12 columns">
					<input type="text" name="name" id="right-label">
				</div>
				<div class="medium-1 small-12 columns"></div>
			</div>

			<div class="row">
				<div class="medium-2 small-12 columns">
					<label for="right-label" class="text-right middle">Ingredients:</label>
				</div>
				<div class="medium-9 small-12 columns">
					<textarea name="ingredients" rows="4" cols="15"></textarea>
				</div>
				<div class="medium-1 small-12 columns"></div>
			</div>

			<div class="row">
				<div class="medium-2 small-12 columns">
					<label for="right-label" class="text-right middle">Method:</label>
				</div>
				<div class="medium-9 small-12 columns">
					<textarea name="method" rows="4" cols="15"></textarea>
				</div>
				<div class="medium-1 small-12 columns"></div>
			</div>

			<div class="row">
				<div class="medium-2 small-12 columns">
					<label for="right-label" class="text-right">Cake
						image:</label>
				</div>
				<div class="medium-9 small-12 columns">
					<input type="file" name="uploadedFile" id="right-label">
				</div>
				<div class="medium-1 small-12 columns"></div>
			</div>

			<div class="row">
				<div class="medium-2 small-12 columns">
					<label for="right-label" class="text-right">
						Price:</label>
				</div>
				<div class="medium-9 small-12 columns">
					<input type="text" name="price">
				</div>
				<div class="medium-1 small-12 columns"></div>
			</div>

			<div class="row">
				<div class="small-2 columns">&nbsp;</div>
				<div class="small-9 columns text-justify">
					<input type="submit" class="hollow button expanded" value="Save">
				</div>
				<div class="small-1 columns"></div>
			</div>
		</fieldset>

	</form>

</body>
</html>