<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.epam.jwd.subscription.entity.Role" %>
<%@include file="header.jsp" %>
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
            <td>${account.role}</td>
            <td>
                <c:if test="${account.role eq Role.USER}">
                    <form name="delete_account" action="${pageContext.request.contextPath}
                    /controller?command=delete_account" method="post">
                        <input type="hidden" name="accId" value="${account.id}"/>
                        <c:if test="${not empty requestScope.errorFindAccountMessage}">
                            <b>${requestScope.errorFindAccountMessage}</b>
                            <br>
                        </c:if>
                        <input type="submit" value="Delete"/>
                    </form>
                </c:if>
            </td>
        </tr>
    </c:forEach>
</table>
</body>
</html>
