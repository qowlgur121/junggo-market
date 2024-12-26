<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<title>중고마켓</title>
<link rel="stylesheet" href="css/style.css">
</head>
<body>
	<h1>중고마켓</h1>
	<%-- 세션에 username이 없으면 로그인 링크를 보여준다 --%>
	<%
	if (session.getAttribute("username") == null) {
	%>
	<a href="loginForm.jsp">로그인</a>
	<br>
	<a href="registerForm.jsp">회원가입</a>
	<%} else {%>
	<%-- 세션에 username이 있으면 사용자이름과 로그아웃 링크, 상품등록 링크를 보여준다 --%>
	<p>
		안녕하세요,
		<%=session.getAttribute("username")%>님!
	</p>
	<a href="logout">로그아웃</a>
	<br>
	<a href="productForm.jsp">상품 등록</a>
	<br>
	<a href="productList">상품 목록</a>
	<br>
	<a href="productSearchForm.jsp">상품 검색</a>
	<br>
	<a href="purchaseRequestList">구매 요청 확인</a>
	<br>
	<%-- 세션에 userRole이 admin이면 관리자 링크를 표시한다. --%>
	<c:if test="${sessionScope.userRole == 'admin'}">
		<a href="adminUserList">회원 관리</a>
		<br>
		<a href="adminReportList">신고 관리</a>
	</c:if>
	<%}%>
</body>

</html>