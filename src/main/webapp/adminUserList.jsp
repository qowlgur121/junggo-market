<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<title>회원 목록</title>
<link rel="stylesheet" href="css/style.css">
</head>
<body>
	<h1>회원 목록</h1>
	<table border="1">
		<thead>
			<tr>
				<th>사용자</th>
				<th>이메일</th>
				<th>전화번호</th>
				<th>역할</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="user" items="${userList}">
				<tr>
					<td>${user.username}</td>
					<td>${user.email}</td>
					<td>${user.phone}</td>
					<td>${user.role}</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<br>
	<a href="index.jsp">메인 페이지로 이동</a>
</body>
</html>