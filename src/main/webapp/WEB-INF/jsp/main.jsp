<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="com.epam.jwd.subscription.entity.Role" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<h1>Cool Subscription</h1>
<c:if test="${not empty sessionScope.account}">
    <p>Hello, ${sessionScope.account.login}</p>
</c:if>
<a href="${pageContext.request.contextPath}/controller?command=show_editions">editions page</a>
<br>
<c:if test="${not empty sessionScope.account && sessionScope.account.role eq Role.ADMIN}">
<a href="${pageContext.request.contextPath}/controller?command=show_users">users page</a>
<br>
<a href="${pageContext.request.contextPath}/controller?command=show_accounts">accounts page</a>
<br>
</c:if>
<c:choose>
    <c:when test="${not empty sessionScope.account}">
        <a href="${pageContext.request.contextPath}/controller?command=logout">logout</a>
    </c:when>
    <c:otherwise>
        <a href="${pageContext.request.contextPath}/controller?command=show_login">login</a>
    </c:otherwise>
</c:choose>
</body>
</html>
