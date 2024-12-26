<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<title>상품 목록</title>
<link rel="stylesheet" href="css/style.css">
</head>
<body>
	<h1>상품 목록</h1>
	<table border="1">
		<thead>
			<tr>
				<th>대표 이미지</th>
				<th>제목</th>
				<th>가격</th>
				<th>조회수</th>
				<th>주소</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="product" items="${productList}">
				<tr>
					<td><img src="${product.main_image}" width="150" /></td>
					<td><a href="productDetail?productId=${product.product_id}">${product.title}</a><br>
						판매자: ${product.username}</td>
					<td>${product.price}</td>
					<td>${product.view_count}</td>
					<td>${product.address}</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<br>
	<%-- 페이지 링크를 생성하는 부분 --%>
	<c:forEach var="i" begin="1" end="${totalPages}">
		<c:choose>
			<c:when test="${currentPage == i}">
               ${i}
           </c:when>
			<c:otherwise>
				<a href="productList?page=${i}">${i}</a>
			</c:otherwise>
		</c:choose>
	</c:forEach>
	<br>
	<a href="index.jsp">메인 페이지로 이동</a>
</body>