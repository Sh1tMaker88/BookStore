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
<br>

<%--Your name: ${param.employeeName}--%>

Your name: ${nameAttribute}




<br>
<br>
<a href="<c:url value="/askDetails"/>"> Back </a>

</body>
</html>
