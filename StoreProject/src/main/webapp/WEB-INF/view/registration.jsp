<%--
  Created by IntelliJ IDEA.
  User: User
  Date: 09.06.2021
  Time: 9:38
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Registration page</title>
</head>
<body>
<h2>Registration</h2>

    <form:form method="POST" modelAttribute="userForm">
        <div>
            <label for="nameInput">Enter username</label>
            <form:input path="username" id="nameInput"/>
            <form:errors path="username"/>
            <br><br>
            <label for="passwordInput">Enter password</label>
            <form:input path="password" id="passwordInput"/>
            <form:errors path="password"/>
            <br><br>
            <button type="submit">Confirm</button>
        </div>
    </form:form>
<br><br>
<a href="/">Main page</a><br>
<a href="/login">Login page</a>

</body>
</html>
