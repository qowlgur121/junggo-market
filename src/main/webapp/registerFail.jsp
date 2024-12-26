<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<html>
<head>
<title>회원가입 실패</title>
<link rel="stylesheet" href="css/style.css">
</head>
<body>
	<h1>회원가입 실패</h1>
	<p>회원가입에 실패했습니다. 다시 시도해주세요.</p>
	<%
	if (request.getParameter("message") != null) {
	%>
	<c:choose>
		<c:when test="${request.getParameter('message') == 'duplicate'}">
			<p>중복된 아이디입니다.</p>
		</c:when>
		<c:when test="${request.getParameter('message') == 'adminUsername'}">
			<p>사용할 수 없는 아이디입니다.</p>
		</c:when>
	</c:choose>
	<%
	}
	%>
	<a href="registerForm.jsp">회원가입 페이지로 이동</a>
</body>
</html>