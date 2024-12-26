<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<html>
<head>
<title>상품 검색</title>
<link rel="stylesheet" href="css/style.css">
</head>
<body>
	<h1>상품 검색</h1>
	<form action="productSearch" method="get">
		<label>검색어:</label><input type="text" name="keyword" required><br>
		<label>주소:</label><input type="text" name="address"><br>
		<input type="submit" value="검색">
	</form>
</body>
</html>