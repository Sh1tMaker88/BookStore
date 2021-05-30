<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%--
  Created by IntelliJ IDEA.
  User: User
  Date: 25.05.2021
  Time: 23:57
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <style>
        body { background-color: #eee; font: helvetica; }
        #container { width: 500px; background-color: #fff;
            margin: 30px auto; padding: 30px; border-radius: 5px; }
        .green { font-weight: bold; color: green; }
        .message { margin-bottom: 10px; }
        label {width:70px; display:inline-block;}
        input { display:inline-block; margin-right: 10px; }
        form {line-height: 160%; }
        .hide { display: none; }
        .error { color: red; font-size: 0.9em; font-weight: bold; }
    </style>
    <title>Ask details</title>
</head>
<body>

<h2>Enter your details</h2>
<br>
<br>
<div id="container">

    <c:if test="${not empty message}"><div class="message green">${message}</div></c:if>


    <form:form action="showDetails" modelAttribute="employee">

        <label for="nameInput">Name</label>
        <form:input path="name" id="nameInput"/>
        <form:errors path="name" cssClass="error"/>
        <br/><br/>
        <label for="surnameInput">Surname</label>
        <form:input path="surname" id="surnameInput"/>
        <form:errors path="surname" cssClass="error"/>
        <br/><br/>
        <label for="salaryInput">Salary</label>
        <form:input path="salary" id="salaryInput"/>
        <form:errors path="salary" cssClass="error"/>
        <br/><br/>
        Department
        <form:select path="department">
    <%--        <form:option value="Information Technology" label="IT"/>--%>
    <%--        <form:option value="Human Resources" label="HR"/>--%>
    <%--        <form:option value="Sales" label="Sales"/>--%>
            <form:option value="-" label="-SELECT-"/>
            <form:options items="${employee.departments}"/>
        </form:select>
        <br><br>
        Which car do you like? <br>
    <%--    <form:radiobutton path="carBrand" value="BMW"/>BMW |--%>
    <%--    <form:radiobutton path="carBrand" value="Audi"/>Audi |--%>
    <%--    <form:radiobutton path="carBrand" value="Mercedes-benz"/>Mercedes--%>
        <form:radiobuttons path="carBrand" items="${employee.carBrands}"/>
        <br><br>
        Foreign language(s):
    <%--    EN <form:checkbox path="languages" value="English"/>--%>
    <%--    DE <form:checkbox path="languages" value="Deutch"/>--%>
    <%--    FR <form:checkbox path="languages" value="Franch"/>--%>
        <form:checkboxes path="languages" items="${employee.languageList}"/>
        <br><br>
        <label for="phoneNumberInput">Phone number:</label>
        <form:input path="phoneNumber" id="phoneNumberInput"/>
        <form:errors path="phoneNumber" cssClass="error"/>
        <br><br>
        <label for="emailInput">Phone number:</label>
        <form:input path="email" id="emailInput"/>
        <form:errors path="email" cssClass="error"/>

        <br><br><br>
        <input type="submit" value="OK">

    </form:form>
</div>

<%--<form action="/showDetails" method="get">--%>
<%--    <label for="button">This is label</label>--%>
<%--    <input type="text" name="employeeName" placeholder="Write your code" id="button">--%>
<%--    <input type="submit">--%>
<%--</form>--%>


</body>
</html>