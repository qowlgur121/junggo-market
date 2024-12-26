<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<html>
<head>
<title>상품 수정</title>
<link rel="stylesheet" href="css/style.css">
</head>
<body>
	<h1>상품 수정</h1>
	<form action="productUpdate" method="post"
		enctype="multipart/form-data">
		<input type="hidden" name="productId" value="${product.product_id}">
		<label>제목:</label><input type="text" name="title"
			value="${product.title}" required><br> <label>설명:</label>
		<textarea name="description" rows="5" cols="30">${product.description}</textarea>
		<br> <label>가격:</label><input type="number" name="price"
			value="${product.price}" required><br> <label>주소:</label><input
			type="text" name="address" value="${product.address}" required><br>
		<label>대표 이미지:</label><input type="file" name="main_image"><br>
		<label>이미지 1:</label><input type="file" name="image1"><br>
		<label>이미지 2:</label><input type="file" name="image2"><br>
		<label>이미지 3:</label><input type="file" name="image3"><br>
		<input type="submit" value="수정하기">
	</form>
</body>
</html>