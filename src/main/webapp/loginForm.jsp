<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<html>
<head>
<title>로그인</title>
<link rel="stylesheet" href="css/style.css">
</head>
<body>
	<h1>로그인</h1>
	<form action="login" method="post">
		<label>아이디:</label><input type="text" name="username" required><br>
		<label>비밀번호:</label><input type="password" name="password" required><br>
		<input type="submit" value="로그인">
	</form>
</body>
</html>