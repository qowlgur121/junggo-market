<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<title>상품 상세 정보</title>
<link rel="stylesheet" href="css/style.css">
</head>
<body>
	<h1>상품 상세 정보</h1>
	<table border="1">
		<tr>
			<th>대표 이미지</th>
			<td><img src="${product.main_image}" width="400" /></td>
		</tr>
		<tr>
			<th>제목</th>
			<td>${product.title}</td>
		</tr>
		<tr>
			<th>설명</th>
			<td>${product.description}</td>
		</tr>
		<tr>
			<th>가격</th>
			<td>${product.price}</td>
		</tr>
		<tr>
			<th>주소</th>
			<td>${product.address}</td>
		</tr>
		<tr>
			<th>판매자</th>
			<td>${product.username}</td>
		</tr>
		<tr>
			<th>조회수</th>
			<td>${product.view_count}</td>
		</tr>
	</table>
	<br>
	<%-- 상품 작성자가 아닌 경우에만 구매 요청 폼 및 신고 버튼을 보여주도록 변경 --%>
	<c:if test="${sessionScope.userId != product.user_id}">
		<h1>구매 의사 요청</h1>
		<form action="purchaseRequest" method="post">
			<input type="hidden" name="productId" value="${product.product_id}">
			<label>원하는 가격:</label><input type="number" name="priceOffer" required><br>
			<label>연락처:</label><input type="text" name="phone" required><br>
			<input type="submit" value="요청하기">
		</form>
		<h1>신고</h1>
		<form action="report" method="post">
			<input type="hidden" name="productId" value="${product.product_id}">
			<label>신고 이유:</label>
			<textarea name="reason" rows="5" cols="30" required></textarea>
			<br> <input type="submit" value="신고하기">
		</form>
	</c:if>
	<br>
	<a href="productList">상품 목록으로 이동</a><br>
	<c:if test="${sessionScope.userId == product.user_id}">
		<a href="productUpdate?productId=${product.product_id}">수정</a><br>
		<a href="productDelete?productId=${product.product_id}">삭제</a><br>
	</c:if>
</body>
</html>