<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<html>
<head>
<title>회원가입</title>
<link rel="stylesheet" href="css/style.css">
</head>
<body>
	<h1>회원가입</h1>
	<form action="register" method="post">
		<label>아이디:</label><input type="text" name="username" required><br>
		<label>비밀번호:</label><input type="password" name="password" required><br>
		<label>이메일:</label><input type="email" name="email" required><br>
		<label>전화번호:</label><input type="text" name="phone" required><br>
		<input type="submit" value="가입하기">
	</form>
</body>
</html>