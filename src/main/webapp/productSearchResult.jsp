<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<title>상품 검색 결과</title>
<link rel="stylesheet" href="css/style.css">
</head>
<body>
	<h1>상품 검색 결과</h1>
	<c:if test="${not empty productList}">
		<table border="1">
			<thead>
				<tr>
					<th>대표 이미지</th>
					<th>제목</th>
					<th>가격</th>
					<th>주소</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="product" items="${productList}">
					<tr>
						<td><img src="${product.main_image}" width="100" /></td>
						<td><a href="productDetail?productId=${product.product_id}">${product.title}</a></td>
						<td>${product.price}</td>
						<td>${product.address}</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</c:if>
	<c:if test="${empty productList}">
		<p>검색 결과가 없습니다.</p>
	</c:if>
	<br>
	<a href="index.jsp">메인 페이지로 이동</a>
</body>
</html>