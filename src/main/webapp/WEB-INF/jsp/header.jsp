<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="jwds" uri="subscription.jwd.epam.com" %>
<%@ page import="com.epam.jwd.subscription.entity.Role" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
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
<fmt:message bundle="${loc}" key="label.link.home_page" var="home_pageLink" />
<html>
<head>
    <title></title>
</head>
<body>
<form name="lang" action="${pageContext.request.contextPath}/controller?command=change_language" method="post"> |
    <a href="${pageContext.request.contextPath}/controller?command=main_page">${home_pageLink}</a> |
    Language: <label>
    <select name="lang" onchange="submit()">
        <option>
            <c:choose>
                <c:when test="${sessionScope.lang eq 'en_US'}">
                    <p>English</p>
                </c:when>
                <c:when test="${sessionScope.lang eq 'ru_RU'}">
                    <p>Russian</p>
                </c:when>
                <c:otherwise>
                </c:otherwise>
            </c:choose>
        </option>
        <option>English</option>
        <option>Russian</option>
    </select></label> |
    <c:choose>
        <c:when test="${not empty sessionScope.account}">
            <a href="${pageContext.request.contextPath}/controller?command=logout">${logoutLink}</a> |
        </c:when>
        <c:otherwise>
            <a href="${pageContext.request.contextPath}/controller?command=show_login">${loginLink}</a> |
            <a href="${pageContext.request.contextPath}/controller?command=show_signup">${signupLink}</a> |
        </c:otherwise>
    </c:choose>
</form>
</body>
</html>
