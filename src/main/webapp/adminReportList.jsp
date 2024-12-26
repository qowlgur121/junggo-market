<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<title>신고 목록</title>
<link rel="stylesheet" href="css/style.css">
</head>
<body>
	<h1>신고 목록</h1>
	<c:if test="${not empty reportList}">
		<table border="1">
			<thead>
				<tr>
					<th>신고된 상품</th>
					<th>신고자</th>
					<th>신고 이유</th>
					<th>삭제</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="report" items="${reportList}">
					<tr>
						<td><a href="productDetail?productId=${report.productId}">${report.productTitle}</a></td>
						<td>${report.username}</td>
						<td>${report.reason}</td>
						<td><a
							href="adminProductDelete?productId=${report.productId}">삭제</a></td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</c:if>
	<c:if test="${empty reportList}">
		<p>신고된 상품이 없습니다.</p>
	</c:if>
	<br>
	<a href="index.jsp">메인 페이지로 이동</a>
</body>
</html>