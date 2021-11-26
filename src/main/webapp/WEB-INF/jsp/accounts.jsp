<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.epam.jwd.subscription.entity.Role" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<h3>Accounts</h3>
<table>
    <tr>
        <th>Id</th>
        <th>Login</th>
        <th>Password</th>
        <th>Role</th>
    </tr>
    <c:forEach var="account" items="${requestScope.accounts}">
        <tr>
            <td>${account.id}</td>
            <td>${account.login}</td>
            <td>${account.password}</td>
            <c:if test="${account.role eq Role.USER}">
                <td>${account.role}</td>
            </c:if>
        </tr>
    </c:forEach>
</table>
</body>
</html>
