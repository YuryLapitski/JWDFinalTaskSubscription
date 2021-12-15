<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="header.jsp" %>
<fmt:setLocale value="${sessionScope.lang}" />
<fmt:setBundle basename="locale.main" var="loc" />
<fmt:message bundle="${loc}" key="label.title.users" var="titleUsers" />
<fmt:message bundle="${loc}" key="label.usersMessage" var="usersMessage" />
<fmt:message bundle="${loc}" key="label.field.userId" var="userId" />
<fmt:message bundle="${loc}" key="label.field.userName" var="userName" />
<fmt:message bundle="${loc}" key="label.field.userAge" var="userAge" />
<fmt:message bundle="${loc}" key="label.field.userEmail" var="userEmail" />
<fmt:message bundle="${loc}" key="label.field.userAccId" var="userAccId" />
<html>
<head>
    <title>${titleUsers}</title>
</head>
<body>
<h3>${usersMessage}</h3>
<table>
    <tr>
        <th>${userId}</th>
        <th>${userName}</th>
        <th>${userAge}</th>
        <th>${userEmail}</th>
        <th>${userAccId}</th>
    </tr>
    <c:forEach var="user" items="${requestScope.users}">
        <tr>
            <td>${user.id}</td>
            <td>${user.firstName} ${user.lastName}</td>
            <td>${user.age}</td>
            <td>${user.email}</td>
            <td>${user.accId}</td>
        </tr>
    </c:forEach>
</table>
</body>
</html>