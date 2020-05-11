<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Login</title>
</head>
<body>

<h2>${success }</h2>
<h2>Login</h2>
<h3>${bad }</h3>

<form:form method="POST" action="validateUser" modelAttribute="userbean">
<form:label path="email">Email</form:label>
<form:input path="email"/><br>
<form:label path="password">Password</form:label>
<form:password path="password"/><br>
<input type="submit" value="Login">
</form:form>
</body>
</html>