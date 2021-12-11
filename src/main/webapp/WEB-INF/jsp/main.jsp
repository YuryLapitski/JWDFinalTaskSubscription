<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="jwds" uri="subscription.jwd.epam.com" %>
<%@ page import="com.epam.jwd.subscription.entity.Role" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="header.jsp" %>
<fmt:setLocale value="${sessionScope.lang}" />
<fmt:setBundle basename="locale.main" var="loc" />
<fmt:message bundle="${loc}" key="label.title" var="pageTitle" />
<fmt:message bundle="${loc}" key="label.subscription" var="subscriptionMessage" />
<fmt:message bundle="${loc}" key="label.welcome" var="welcomeMessage" />
<fmt:message bundle="${loc}" key="label.link.editions" var="editionsLink" />
<fmt:message bundle="${loc}" key="label.link.users" var="usersLink" />
<fmt:message bundle="${loc}" key="label.link.accounts" var="accountsLink" />
<fmt:message bundle="${loc}" key="label.link.login" var="loginLink" />
<fmt:message bundle="${loc}" key="label.link.logout" var="logoutLink" />
<fmt:message bundle="${loc}" key="label.link.signup" var="signupLink" />
<fmt:message bundle="${loc}" key="label.link.user_data" var="user_dataLink" />
<fmt:message bundle="${loc}" key="label.link.shopping_card" var="shopping_cardLink" />
<fmt:message bundle="${loc}" key="label.link.archive" var="archiveLink" />
<html>
<head>
    <title>${pageTitle}</title>
</head>
<body>
<h1>${subscriptionMessage}</h1>
<jwds:welcomeAccount text="${welcomeMessage}"/>
<a href="${pageContext.request.contextPath}/controller?command=show_editions">${editionsLink}</a>
<br>
<c:if test="${not empty sessionScope.account && sessionScope.account.role eq Role.ADMIN}">
    <a href="${pageContext.request.contextPath}/controller?command=show_users">${usersLink}</a>
    <br>
    <a href="${pageContext.request.contextPath}/controller?command=show_accounts">${accountsLink}</a>
    <br>
    <a href="${pageContext.request.contextPath}/controller?command=show_archive">${archiveLink}</a>
    <br>
</c:if>
<c:if test="${not empty sessionScope.account && sessionScope.account.role eq Role.USER}">
    <a href="${pageContext.request.contextPath}/controller?command=show_user_data">${user_dataLink}</a>
    <br>
    <a href="${pageContext.request.contextPath}/controller?command=show_shopping_card">${shopping_cardLink}</a>
    <br>
</c:if>
<c:choose>
    <c:when test="${not empty sessionScope.account}">
        <a href="${pageContext.request.contextPath}/controller?command=logout">${logoutLink}</a>
    </c:when>
    <c:otherwise>
        <a href="${pageContext.request.contextPath}/controller?command=show_login">${loginLink}</a>
        <br>
        <a href="${pageContext.request.contextPath}/controller?command=show_signup">${signupLink}</a>
    </c:otherwise>
</c:choose>
</body>
