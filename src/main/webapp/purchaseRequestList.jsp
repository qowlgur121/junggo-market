<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<title>구매 요청 목록</title>
<link rel="stylesheet" href="css/style.css">
</head>
<body>
	<h1>구매 요청 목록</h1>
	<c:if test="${not empty purchaseRequestList}">
		<table border="1">
			<thead>
				<tr>
					<th>상품 제목</th>
					<th>구매 요청자</th>
					<th>제안 가격</th>
					<th>연락처</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="purchaseRequest" items="${purchaseRequestList}">
					<tr>
						<td>${purchaseRequest.productTitle}</td>
						<td>${purchaseRequest.username}</td>
						<td>${purchaseRequest.price_offer}</td>
						<td>${purchaseRequest.phone}</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</c:if>
	<c:if test="${empty purchaseRequestList}">
		<p>구매 요청이 없습니다.</p>
	</c:if>
	<br>
	<a href="index.jsp">메인 페이지로 이동</a>
</body>
</html>