<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: User
  Date: 25.05.2021
  Time: 23:59
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Show details</title>
</head>
<body>

<h2>You are welcome</h2>
<br>

<%--Your name: ${param.employeeName}--%>

<%--Your name: ${nameAttribute}--%>

Your name: ${employee.name}
<br>
Your surname: ${employee.surname}
<br>
Your salary: ${employee.salary}
<br>
Your department: ${employee.department}
<br>
Your car: ${employee.carBrand}
<br>
Your language(s):
<ul>
    <c:forEach var="lang" items="${employee.languages}">
        <li> ${lang} </li>
    </c:forEach>

</ul>
<br>
Phone number: ${employee.phoneNumber}
<br>
Email: ${employee.email}
<br>



<br>
<br>
<a href="<c:url value="/askDetails"/>"> Back </a>

</body>
</html>
