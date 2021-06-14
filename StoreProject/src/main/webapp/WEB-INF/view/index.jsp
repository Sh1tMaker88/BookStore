<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%--
  Created by IntelliJ IDEA.
  User: User
  Date: 06.06.2021
  Time: 18:09
  To change this template use File | Settings | File Templates.
--%>

<html>
<head>
    <title>Start page</title>
</head>
<body>
    --Hello ${pageContext.request.userPrincipal.name}-- role admin: ${pageContext.request.isUserInRole('ADMIN')} |
    role_admin: ${pageContext.request.isUserInRole('ROLE_ADMIN')} |
    user: ${pageContext.request.isUserInRole('USER')} |
    role_user: ${pageContext.request.isUserInRole('ROLE_USER')}
    <div>
        <security:authorize access="!isAuthenticated()">
            <h3><a href="/login">Enter</a></h3>
            <h3><a href="/registration">Registration</a></h3>
        </security:authorize>

        <security:authorize access="isAuthenticated()">
            <h3><a href="/logout">Exit</a> </h3>
        </security:authorize>

        <br><br>
        <h4><a href="/books/">>>Books<<</a> </h4>
        <h4><a href="/orders/">>>Orders<<</a> </h4>
        <h4><a href="/requests/">>>Requests<<</a> </h4>

        <h6><a href="/admin">Admin page</a></h6>
    </div>
<%--<security:authentication property="username"/>--%>
</body>
</html>
