
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<html>
<head>
<title>상품 등록</title>
<link rel="stylesheet" href="css/style.css">
</head>
<body>
	<h1>상품 등록</h1>
	<form action="productRegister" method="post"
		enctype="multipart/form-data">
		<label>제목:</label><input type="text" name="title" required><br>
		<label>설명:</label>
		<textarea name="description" rows="5" cols="30"></textarea>
		<br> <label>가격:</label><input type="number" name="price" required><br>
		<label>주소:</label><input type="text" name="address" required><br>
		<label>대표 이미지:</label><input type="file" name="main_image" required><br>
		<input type="submit" value="등록하기">
	</form>
</body>
</html>