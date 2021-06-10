<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: User
  Date: 09.06.2021
  Time: 22:16
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Admin Page</title>
</head>
<body>
    <h1>Hello ${pageContext.request.userPrincipal.name}</h1>
    <div>
        <table>
            <thead>
            <th>Username</th>
            <th>Password</th>
            <th>Roles</th>
            </thead>

            <c:forEach items="${allUsers}" var="user">
                <tr>
                    <td>${user.id}</td>
                    <td>${user.username}</td>
                    <td>${user.password}</td>
                    <td>
                        <c:forEach items="${user.roles}" var="role">${role.name()}</c:forEach>
                    </td>
                    <td>
                        <form action="${pageContext.request.contextPath}/admin" method="post">
                            <input type="hidden" name="userId" value="${user.id}"/>
                            <input type="hidden" name="action" value="delete"/>
                            <button type="submit">Delete</button>
                        </form>
                    </td>
                </tr>
            </c:forEach>
        </table>
        <a href="/">Main page</a>
    </div>

</body>
</html>
