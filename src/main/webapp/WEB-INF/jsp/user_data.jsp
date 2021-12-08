<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="header.jsp" %>
<html>
<head>
    <title>User data</title>
</head>
<body>
<h3>Please enter your details:</h3>
<form name="user_data-form" action="${pageContext.request.contextPath}/controller?command=user_data" method="post">
    <label for="first_name-input">First Name:</label>
    <input id="first_name-input" type="text" name="first_name" value="${requestScope.user.firstName}"/>
    <br>
    <label for="last_name-input">Last Name:</label>
    <input id="last_name-input" type="text" name="last_name" value="${requestScope.user.lastName}"/>
    <br>
    <label for="age-input">Age:</label>
    <input id="age-input" type="number" name="age" value="${requestScope.user.age}"/>
    <br>
    <label for="email-input">Email:</label>
    <input id="email-input" type="text" name="email" value="${requestScope.user.email}"/>
    <br/>
    <c:if test="${not empty requestScope.errorUserDataMessage}">
        <b style="color: red">${requestScope.errorUserDataMessage}</b>
        <br>
    </c:if>
    <input type="submit" value="Submit"/>
</form>
</body>
</html>
